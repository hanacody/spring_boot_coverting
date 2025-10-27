package hello.core.member;

import java.util.HashMap;
import java.util.Map;

/**
 * 메모리에 회원 정보를 저장하는 구현체
 * 실제 데이터베이스 대신 HashMap을 사용하여 메모리에 저장
 */
public class MemoryMemberRepository implements MemberRepository {

    // 메모리에 회원 정보를 저장하는 Map
    // Key: 회원 ID, Value: 회원 객체
    /**
     * 회원 정보를 저장하는 Map
     * Key는 회원의 ID(Long 타입), Value는 회원 객체(Member)
     * 실제 운영에서는 데이터베이스를 사용하지만, 여기서는 간단히 메모리 저장용으로 사용한다.
     */

    /**
     * Map 클래스는 키-값(key-value) 쌍으로 데이터를 저장할 때 사용한다.
     * 예를 들어 회원 ID(키)에 해당하는 회원 객체(값)처럼,
     * 특정 키로 값을 빠르게 조회, 저장, 삭제하고 싶을 때 Map을 활용한다.
     * 대표적으로 HashMap, TreeMap, LinkedHashMap 등이 있다.
     */
    // 콘커런트해쉬맵(ConcurrentHashMap)은 멀티스레드 환경에서 안전하게 데이터를 저장하고 조회할 수 있도록 설계된 Map 구현체입니다.
    // 여러 스레드가 동시에 put, get, remove 연산을 수행해도 데이터 일관성을 보장합니다.
    // HashMap은 동기화가 되어 있지 않아 멀티스레드 환경에서 사용할 때 문제가 생길 수 있으나,
    // ConcurrentHashMap은 내부적으로 세그먼트나 락 분할 등 최적화를 통해 동시성을 지원합니다.
    //
    // 일반적으로 서버, 웹 애플리케이션 등 여러 스레드가 동시에 Map에 접근하는 환경이라면 HashMap 대신 ConcurrentHashMap을 사용해야 안전합니다.
    // 예시:
    // private static Map<Long, Member> store = new ConcurrentHashMap<>();
    //
    // 하지만 여기 MemoryMemberRepository는 예제로 단일 스레드나 테스트 용도로 설계되어 HashMap을 사용합니다.
    // 실제 멀티스레드 환경에서는 ConcurrentHashMap 사용을 고려해야 합니다.
    private static Map<Long, Member> store = new HashMap<>();

    /**
     * 회원 정보를 메모리에 저장
     * @param member 저장할 회원 정보
     */
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    /**
     * 회원 ID로 회원 정보 조회
     * @param memberId 조회할 회원 ID
     * @return 조회된 회원 정보, 없으면 null
     */
    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
