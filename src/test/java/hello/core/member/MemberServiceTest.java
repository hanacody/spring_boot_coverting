package hello.core.member;

import hello.core.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * MemberService 단위 테스트
 * 회원 서비스의 비즈니스 로직을 테스트
 * 
 * 테스트 시나리오:
 * 1. 회원 가입 후 정상적으로 저장되는지 확인
 * 2. 가입한 회원을 ID로 조회할 수 있는지 확인
 * 3. 존재하지 않는 회원 조회 시 null 반환 확인
 * 4. 회원 정보의 모든 필드가 올바르게 저장되고 조회되는지 확인
 * 5. 테스트 간 데이터 격리 보장 확인
 */
class MemberServiceTest {

    MemberService memberService;

    /**
     * 각 테스트 실행 전에 실행되는 메서드
     * AppConfig를 통해 의존성 주입 및 테스트 간 격리 보장
     */
    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    /**
     * 회원 가입 테스트
     * 회원이 정상적으로 가입되는지 확인
     */
    @Test
    void join() {
        // Given: 테스트 데이터 준비
        Member member = new Member(1L, "memberA", Grade.VIP);

        // When: 회원 가입 실행
        memberService.join(member);

        // Then: 결과 검증
        Member findMember = memberService.findMember(1L);
        assertThat(member).isEqualTo(findMember);
    }

    /**
     * 회원 조회 테스트
     * 가입한 회원을 정상적으로 조회하는지 확인
     */
    @Test
    void findMember() {
        // Given: 회원 가입
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // When: 회원 조회
        Member findMember = memberService.findMember(1L);

        // Then: 조회된 회원 정보 검증
        assertThat(findMember.getId()).isEqualTo(1L);
        assertThat(findMember.getName()).isEqualTo("memberA");
        assertThat(findMember.getGrade()).isEqualTo(Grade.VIP);
    }

    /**
     * 존재하지 않는 회원 조회 테스트
     * 존재하지 않는 ID로 조회할 때 null이 반환되는지 확인
     */
    @Test
    void findMemberNotFound() {
        // When: 존재하지 않는 회원 조회
        Member findMember = memberService.findMember(999L);

        // Then: null 반환 확인
        assertThat(findMember).isNull();
    }
}
