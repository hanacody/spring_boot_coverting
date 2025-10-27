package hello.core.member;

/**
 * 회원 저장소 인터페이스
 * 회원 데이터 저장 및 조회 기능을 정의하는 계약
 */
public interface MemberRepository {
    
    /**
     * 회원 저장
     * @param member 저장할 회원 정보
     */
    void save(Member member);
    
    /**
     * 회원 ID로 조회
     * @param memberId 조회할 회원 ID
     * @return 조회된 회원 정보, 없으면 null
     */
    Member findById(Long memberId);
}
