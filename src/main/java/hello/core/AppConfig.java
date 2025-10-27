package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * 애플리케이션 전체 동작 방식을 설정하는 설정 클래스(정책을 수정한다면 여기서 수정한다)
 * 
 * 역할과 책임:
 * 1. 애플리케이션의 모든 객체를 생성하고 관리
 * 2. 의존성 주입을 통한 객체 간 관계 설정
 * 3. 구현체 선택 및 정책 변경을 한 곳에서 관리
 * 4. 객체의 생성과 사용을 분리하여 관심사 분리
 */
public class AppConfig {

    /**
     * 회원 저장소 구현체를 생성하여 반환
     * 현재는 메모리 저장소를 사용하지만, 필요시 다른 저장소로 쉽게 변경 가능
     * 
     * @return MemberRepository 구현체
     */
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    /**
     * 할인 정책 구현체를 생성하여 반환
     * 정액할인과 정률할인 중 선택 가능
     * 
     * @return DiscountPolicy 구현체
     */
    public DiscountPolicy discountPolicy() {
        // 정액할인 정책 사용 (필요시 RateDiscountPolicy로 변경 가능)
        return new FixDiscountPolicy();
        // return new RateDiscountPolicy(); // 정률할인 정책 사용 시
    }

    /**
     * 회원 서비스 구현체를 생성하여 반환
     * 생성자를 통한 의존성 주입으로 MemberRepository를 주입
     * 
     * @return MemberService 구현체
     */
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * 주문 서비스 구현체를 생성하여 반환
     * 생성자를 통한 의존성 주입으로 MemberRepository와 DiscountPolicy를 주입
     * 
     * @return OrderService 구현체
     */
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
