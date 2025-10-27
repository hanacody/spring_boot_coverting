package hello.core.member;

/**
 * 회원 서비스 구현체
 * 회원 관련 비즈니스 로직을 처리하는 클래스
 */
public class MemberServiceImpl implements MemberService {

    // 의존성 주입을 위한 MemberRepository 인터페이스 참조
    private final MemberRepository memberRepository;

    /**
     * 생성자를 통한 의존성 주입
     * @param memberRepository 회원 저장소 구현체
     */
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입 처리
     * @param member 가입할 회원 정보
     */
    
    @Override
    public void join(Member member) {
        // 회원 정보를 저장소에 저장
        memberRepository.save(member);
    }

    /**
     * 회원 ID로 회원 정보 조회
     * @param memberId 조회할 회원 ID
     * @return 조회된 회원 정보
     */
    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
