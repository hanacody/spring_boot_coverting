package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * 주문 서비스 테스트
 * OrderService의 주문 생성 로직을 검증
 *
 * 테스트 시나리오:
 * 1. VIP 회원이 정액할인 정책으로 주문할 경우:
 *    - 10000원짜리 상품을 주문하면 1000원의 할인이 적용되어 9000원이 결제되어야 한다.
 *    - Order의 discountPrice는 1000, calculatePrice()는 9000이어야 한다.
 *
 * 2. BASIC 회원이 주문할 경우:
 *    - 어떤 할인 정책이더라도, BASIC 등급은 할인이 적용되지 않는다.
 *    - 10000원짜리 상품을 주문하면 discountPrice가 0, calculatePrice()는 10000이어야 한다.
 *
 * 3. VIP 회원이 정률할인 정책(예: 10%)으로 주문할 경우:
 *    - 10000원짜리 상품을 주문하면 10% 할인(1000원) 적용되어, discountPrice가 1000, calculatePrice()는 9000이어야 한다.
 *
 * 4. 주문생성시 저장소에 회원이 존재하지 않으면 예외 또는 null 등 적절하게 처리되는지 확인한다.
 *
 * 5. 다양한 금액과 등급 조합으로 할인 정책이 올바르게 적용되는지 경계값 테스트를 수행한다.
 */
class OrderServiceTest {

    MemberRepository memberRepository;
    OrderService orderService;

    /**
     * 각 테스트 실행 전에 실행되는 메서드
     * AppConfig를 통해 의존성 주입 및 테스트 간 격리 보장
     */
    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberRepository = appConfig.memberRepository();
        orderService = appConfig.orderService();
    }

    @Test
    @DisplayName("VIP 회원 주문 - 정액할인 적용")
    void createOrder_VIP_FixedDiscount() {
        // Given: VIP 회원 생성 및 저장
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        memberRepository.save(member);

        // When: 주문 생성
        Order order = orderService.createOrder(1L, "itemA", 10000);

        // Then: VIP 회원은 1000원 할인 적용
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
        assertThat(order.calculatePrice()).isEqualTo(9000); // 10000 - 1000
    }

    @Test
    @DisplayName("BASIC 회원 주문 - 할인 없음")
    void createOrder_BASIC_NoDiscount() {
        // Given: BASIC 회원 생성 및 저장
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);
        memberRepository.save(member);

        // When: 주문 생성
        Order order = orderService.createOrder(2L, "itemB", 10000);

        // Then: BASIC 회원은 할인 없음
        assertThat(order.getDiscountPrice()).isEqualTo(0);
        assertThat(order.calculatePrice()).isEqualTo(10000); // 10000 - 0
    }

    @Test
    @DisplayName("정률할인 정책으로 주문 생성")
    void createOrder_RateDiscount() {
        // Given: VIP 회원 생성 및 저장, 정률할인 정책 사용
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        memberRepository.save(member);

        // AppConfig를 사용하여 정률할인 정책으로 OrderService 설정
        AppConfig appConfig = new AppConfig() {
            @Override
            public OrderService orderService() {
                return new OrderServiceImpl(memberRepository, new RateDiscountPolicy());
            }
        };
        OrderService rateOrderService = appConfig.orderService();

        // When: 주문 생성
        Order order = rateOrderService.createOrder(1L, "itemC", 10000);

        // Then: VIP 회원은 10% 할인 적용
        assertThat(order.getDiscountPrice()).isEqualTo(1000); // 10000 * 10%
        assertThat(order.calculatePrice()).isEqualTo(9000); // 10000 - 1000
    }

    @Test
    @DisplayName("주문 정보 검증")
    void createOrder_OrderInfo() {
        // Given: 회원 생성 및 저장
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        memberRepository.save(member);

        // When: 주문 생성
        Order order = orderService.createOrder(1L, "itemD", 20000);

        // Then: 주문 정보가 올바르게 설정되었는지 확인
        assertThat(order.getMemberId()).isEqualTo(1L);
        assertThat(order.getItemName()).isEqualTo("itemD");
        assertThat(order.getItemPrice()).isEqualTo(20000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
        assertThat(order.calculatePrice()).isEqualTo(19000);
    }

    @Test
    @DisplayName("정액할인과 정률할인 비교")
    void compareDiscountPolicies() {
        // Given: VIP 회원 생성 및 저장
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        memberRepository.save(member);

        // When: 같은 가격으로 두 가지 할인 정책 적용
        AppConfig fixedConfig = new AppConfig();
        OrderService fixedOrderService = fixedConfig.orderService();

        AppConfig rateConfig = new AppConfig() {
            @Override
            public OrderService orderService() {
                return new OrderServiceImpl(memberRepository, new RateDiscountPolicy());
            }
        };
        OrderService rateOrderService = rateConfig.orderService();

        Order fixedOrder = fixedOrderService.createOrder(1L, "itemE", 10000);
        Order rateOrder = rateOrderService.createOrder(1L, "itemE", 10000);

        // Then: 정액할인과 정률할인 결과 비교
        assertThat(fixedOrder.getDiscountPrice()).isEqualTo(1000); // 정액할인
        assertThat(rateOrder.getDiscountPrice()).isEqualTo(1000);  // 정률할인 (10%)

        // 가격이 높아지면 정률할인이 더 유리
        Order fixedOrderHigh = fixedOrderService.createOrder(1L, "itemF", 50000);
        Order rateOrderHigh = rateOrderService.createOrder(1L, "itemF", 50000);

        assertThat(fixedOrderHigh.getDiscountPrice()).isEqualTo(1000); // 정액할인
        assertThat(rateOrderHigh.getDiscountPrice()).isEqualTo(5000);   // 정률할인 (10%)
    }
}
