package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링 기반 애플리케이션 설정 클래스
 * 필요한 빈을 등록하고 의존성을 주입한다.
 */
@Configuration
public class AppConfig {

    /**
     * 회원 저장소 빈 정의
     * 향후 다른 저장소로 교체 용이
     */
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    /**
     * 할인 정책 빈 정의
     * 정책 변경 시 이 메서드만 수정
     */
    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
        // return new RateDiscountPolicy();
    }

    /**
     * 회원 서비스 빈 정의
     * 생성자 주입에 의해 memberRepository()가 주입됨
     */
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * 주문 서비스 빈 정의
     * 생성자 주입에 의해 memberRepository(), discountPolicy()가 주입됨
     */
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
