package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

/**
 * 주문 관리 시스템 테스트 애플리케이션
 * AppConfig를 사용하여 의존성 주입을 관리
 */
public class OrderApp {

    public static void main(String[] args) {
        // AppConfig를 통한 의존성 주입
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        // 테스트용 회원 생성 및 가입
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // 주문 생성 및 결과 출력
        Order order = orderService.createOrder(1L, "itemA", 10000);
        
        System.out.println("주문 결과:");
        System.out.println("회원: " + member.getName() + " (" + member.getGrade() + ")");
        System.out.println("상품: " + order.getItemName());
        System.out.println("상품 가격: " + order.getItemPrice() + "원");
        System.out.println("할인 금액: " + order.getDiscountPrice() + "원");
        System.out.println("최종 결제 금액: " + order.calculatePrice() + "원");
        System.out.println("=====================================");
        
        // BASIC 회원으로 테스트
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
