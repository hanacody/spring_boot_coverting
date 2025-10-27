package hello.core.member;

/**
 * 회원 서비스 인터페이스
 * 회원 관련 비즈니스 로직을 정의하는 계약
 */
public interface MemberService {
    
    /**
     * 회원 가입
     * @param member 가입할 회원 정보
     */
    void join(Member member);
    
    /**
     * 회원 조회
     * @param memberId 조회할 회원 ID
     * @return 조회된 회원 정보
     */
    Member findMember(Long memberId);
}
