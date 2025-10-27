package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

/**
 * 주문 서비스 구현체
 * 주문 관련 비즈니스 로직을 처리하는 클래스
 */
public class OrderServiceImpl implements OrderService {

    // 의존성 주입을 위한 인터페이스 참조들
    /**
     * private final 키워드 설명
     * - private: 해당 필드는 OrderServiceImpl 클래스 내부에서만 접근할 수 있도록 제한합니다.
     * - final: 해당 필드는 한 번 값이 할당되면 변경할 수 없음을 의미합니다(불변).
     *   즉, 생성자를 통해 값이 초기화된 후에는 이후 값이 바뀌지 않습니다.
     * 주 목적:
     * - 의존성 주입을 통해 주입된 의존 객체(memberRepository, discountPolicy)가
     *   외부 또는 내부적으로 변경되는 것을 방지하여 불변성을 보장하고,
     *   안전하고 신뢰성 있는 코드 구조를 만들 수 있습니다.
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * 생성자를 통한 의존성 주입
     * @param memberRepository 회원 저장소 구현체
     * @param discountPolicy 할인 정책 구현체
     */
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     * 주문 생성 처리
     * 1. 회원 정보 조회
     * 2. 할인 정책 적용
     * 3. 주문 객체 생성 및 반환
     * 
     * @param memberId 주문한 회원 ID
     * @param itemName 주문 상품명
     * @param itemPrice 상품 가격
     * @return 생성된 주문 정보
     */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 1. 회원 정보 조회
        Member member = memberRepository.findById(memberId);
        
        // 2. 할인 정책 적용하여 할인 금액 계산
        int discountPrice = discountPolicy.discount(member, itemPrice);
        
        // 3. 주문 객체 생성 및 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
