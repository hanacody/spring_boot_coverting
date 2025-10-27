package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * AppConfig 설정 클래스 테스트
 * 애플리케이션 설정이 올바르게 동작하는지 검증
 * 
 * 테스트 시나리오:
 * 1. AppConfig를 통한 객체 생성이 올바르게 동작하는지 확인
 * 2. 의존성 주입이 정상적으로 이루어지는지 확인
 * 3. 생성된 객체들이 서로 올바르게 연동되는지 확인
 * 4. 할인 정책 변경 시 동작이 올바르게 반영되는지 확인
 * 5. 전체 애플리케이션 플로우가 정상 동작하는지 확인
 */
class AppConfigTest {

    AppConfig appConfig;

    /**
     * 각 테스트 실행 전에 실행되는 메서드
     * 테스트마다 새로운 AppConfig 객체를 생성하여 테스트 간 격리 보장
     */
    @BeforeEach
    void beforeEach() {
        appConfig = new AppConfig();
    }

    @Test
    @DisplayName("AppConfig를 통한 객체 생성 테스트")
    void createObjects() {
        // When: AppConfig를 통해 각 서비스 객체 생성
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        // Then: 객체들이 정상적으로 생성되었는지 확인
        assertThat(memberService).isNotNull();
        assertThat(orderService).isNotNull();
    }

    @Test
    @DisplayName("의존성 주입이 올바르게 동작하는지 테스트")
    void dependencyInjection() {
        // Given: 테스트용 회원 생성
        Member member = new Member(1L, "memberA", Grade.VIP);

        // When: AppConfig를 통해 서비스 객체 생성 및 사용
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        memberService.join(member);
        Order order = orderService.createOrder(1L, "itemA", 10000);

        // Then: 의존성 주입이 올바르게 동작하여 정상적인 결과 확인
        assertThat(order.getMemberId()).isEqualTo(1L);
        assertThat(order.getItemName()).isEqualTo("itemA");
        assertThat(order.getItemPrice()).isEqualTo(10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000); // VIP 회원 정액할인
        assertThat(order.calculatePrice()).isEqualTo(9000);
    }

    @Test
    @DisplayName("할인 정책 변경 테스트")
    void changeDiscountPolicy() {
        // Given: VIP 회원 생성
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        MemberService memberService = appConfig.memberService();
        memberService.join(member);

        // When: 정액할인 정책으로 주문 생성
        OrderService orderService = appConfig.orderService();
        Order fixedOrder = orderService.createOrder(1L, "itemA", 10000);

        // Then: 정액할인 적용 확인
        assertThat(fixedOrder.getDiscountPrice()).isEqualTo(1000);

        // 할인 정책을 정률할인으로 변경하려면 AppConfig의 discountPolicy() 메서드를 수정해야 함
        // 현재는 정액할인으로 설정되어 있음
    }

    @Test
    @DisplayName("전체 애플리케이션 플로우 테스트")
    void applicationFlow() {
        // Given: 회원 가입
        Member vipMember = new Member(1L, "memberVIP", Grade.VIP);
        Member basicMember = new Member(2L, "memberBASIC", Grade.BASIC);

        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        memberService.join(vipMember);
        memberService.join(basicMember);

        // When: 각 회원이 주문 생성
        Order vipOrder = orderService.createOrder(1L, "itemVIP", 10000);
        Order basicOrder = orderService.createOrder(2L, "itemBASIC", 10000);

        // Then: 전체 플로우가 정상 동작하는지 확인
        // VIP 회원: 할인 적용
        assertThat(vipOrder.getDiscountPrice()).isEqualTo(1000);
        assertThat(vipOrder.calculatePrice()).isEqualTo(9000);

        // BASIC 회원: 할인 없음
        assertThat(basicOrder.getDiscountPrice()).isEqualTo(0);
        assertThat(basicOrder.calculatePrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("객체 재사용성 테스트")
    void objectReusability() {
        // When: 같은 메서드를 여러 번 호출
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        // Then: 매번 새로운 객체가 생성되는지 확인 (싱글톤이 아님)
        assertThat(memberService1).isNotSameAs(memberService2);
        assertThat(memberService1).isNotEqualTo(memberService2);
    }
}
