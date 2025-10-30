package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 주문 관리 시스템 테스트 애플리케이션 (Spring 기반)
 * 스프링 컨테이너(ApplicationContext)를 통해 의존성 주입을 관리
 */
public class OrderApp {

    public static void main(String[] args) {
        // 스프링 컨테이너를 통한 의존성 주입
        @SuppressWarnings("resource")
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        // 테스트용 회원 생성 및 가입(VIP)
        Member vipMember = new Member(1L, "memberA", Grade.VIP);
        memberService.join(vipMember);

        // 주문 생성 및 결과 출력 (VIP)
        Order vipOrder = orderService.createOrder(1L, "itemA", 10000);
        System.out.println("주문 결과:");
        System.out.println("회원: " + vipMember.getName() + " (" + vipMember.getGrade() + ")");
        System.out.println("상품: " + vipOrder.getItemName());
        System.out.println("상품 가격: " + vipOrder.getItemPrice() + "원");
        System.out.println("할인 금액: " + vipOrder.getDiscountPrice() + "원");
        System.out.println("최종 결제 금액: " + vipOrder.calculatePrice() + "원");
        System.out.println("=====================================");

        // BASIC 회원 테스트
        Member basicMember = new Member(2L, "memberB", Grade.BASIC);
        memberService.join(basicMember);

        Order basicOrder = orderService.createOrder(2L, "itemC", 10000);

        System.out.println("BASIC 회원 주문:");
        System.out.println("회원: " + basicMember.getName() + " (" + basicMember.getGrade() + ")");
        System.out.println("상품: " + basicOrder.getItemName());
        System.out.println("상품 가격: " + basicOrder.getItemPrice() + "원");
        System.out.println("할인 금액: " + basicOrder.getDiscountPrice() + "원");
        System.out.println("최종 결제 금액: " + basicOrder.calculatePrice() + "원");
    }
}
