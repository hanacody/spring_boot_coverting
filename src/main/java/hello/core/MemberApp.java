package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;

/**
 * 회원 관리 시스템 테스트 애플리케이션
 * AppConfig를 사용하여 의존성 주입을 관리
 */
public class MemberApp {

    public static void main(String[] args) {
        // AppConfig를 통한 의존성 주입
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();

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
