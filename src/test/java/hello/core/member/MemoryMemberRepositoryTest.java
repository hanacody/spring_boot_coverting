package hello.core.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * MemoryMemberRepository 단위 테스트
 * 메모리 저장소의 데이터 저장 및 조회 기능을 테스트
 * 
 * 테스트 시나리오:
 * 1. 회원 정보를 메모리에 정상적으로 저장하는지 확인
 * 2. 저장된 회원을 ID로 정확히 조회하는지 확인
 * 3. 존재하지 않는 회원 조회 시 null 반환 확인
 * 4. 같은 ID로 중복 저장 시 기존 데이터 덮어쓰기 확인
 * 5. 저장된 회원의 모든 필드 정보가 올바른지 확인
 * 6. 테스트 간 데이터 격리 보장 확인
 */
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository;

    /**
     * 각 테스트 실행 전에 실행되는 메서드
     * 테스트마다 새로운 저장소 객체를 생성하여 테스트 간 격리 보장
     */
    @BeforeEach
    void beforeEach() {
        repository = new MemoryMemberRepository();
    }

    /**
     * 회원 저장 테스트
     * 회원이 정상적으로 저장되는지 확인
     */
    @Test
    void save() {
        // Given: 테스트 데이터 준비
        Member member = new Member(1L, "memberA", Grade.VIP);

        // When: 회원 저장
        repository.save(member);

        // Then: 저장된 회원 조회하여 검증
        Member findMember = repository.findById(1L);
        assertThat(findMember).isEqualTo(member);
    }

    /**
     * 회원 조회 테스트
     * 저장된 회원이 정상적으로 조회되는지 확인
     */
    @Test
    void findById() {
        // Given: 회원 저장
        Member member = new Member(1L, "memberA", Grade.BASIC);
        repository.save(member);

        // When: 회원 조회
        Member findMember = repository.findById(1L);

        // Then: 조회된 회원 정보 검증
        assertThat(findMember.getId()).isEqualTo(1L);
        assertThat(findMember.getName()).isEqualTo("memberA");
        assertThat(findMember.getGrade()).isEqualTo(Grade.BASIC);
    }

    /**
     * 존재하지 않는 회원 조회 테스트
     * 존재하지 않는 ID로 조회할 때 null이 반환되는지 확인
     */
    @Test
    void findByIdNotFound() {
        // When: 존재하지 않는 회원 조회
        Member findMember = repository.findById(999L);

        // Then: null 반환 확인
        assertThat(findMember).isNull();
    }

    /**
     * 중복 저장 테스트
     * 같은 ID의 회원을 다시 저장할 때 기존 데이터가 덮어써지는지 확인
     */
    @Test
    void saveDuplicate() {
        // Given: 첫 번째 회원 저장
        Member member1 = new Member(1L, "memberA", Grade.BASIC);
        repository.save(member1);

        // When: 같은 ID의 다른 회원 저장 (덮어쓰기)
        Member member2 = new Member(1L, "memberB", Grade.VIP);
        repository.save(member2);

        // Then: 마지막에 저장된 회원이 조회되는지 확인
        Member findMember = repository.findById(1L);
        assertThat(findMember.getName()).isEqualTo("memberB");
        assertThat(findMember.getGrade()).isEqualTo(Grade.VIP);
    }
}
