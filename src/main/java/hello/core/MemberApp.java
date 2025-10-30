package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 회원 관리 시스템 테스트 애플리케이션 (Spring 기반)
 * 스프링 컨테이너(ApplicationContext)를 통해 의존성 주입을 관리
 */
public class MemberApp {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        // 테스트용 회원 생성
        Member member = new Member(1L, "memberA", Grade.VIP);
        
        // 회원 가입
        memberService.join(member);

        // 회원 조회
        Member findMember = memberService.findMember(1L);

        // 결과 출력
        System.out.println("새로운 회원: " + member.getName());
        System.out.println("조회된 회원: " + findMember.getName());
        System.out.println("회원 등급: " + findMember.getGrade());
    }
}
