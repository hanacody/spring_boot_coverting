package hello.core.member;

/**
 * 회원 정보를 담는 도메인 클래스
 */

/**
 * 도메인 클래스(Domain Class)란?
 * - 비즈니스 도메인(예: 회원, 주문, 상품 등)을 코드로 표현한 클래스.
 * - 비즈니스 규칙, 속성, 주요 메서드들을 포함하고, 주로 데이터(정보)를 담거나 조작함.
 * - 예) Member, Order 등과 같은 클래스가 도메인 클래스임.
 *
 * 인터페이스(Interface)와 구현체(Implementation)의 차이:
 * - 인터페이스(Interface):
 *     - 동작(메서드 시그니처, 약속, 계약)만 정의하고 실제 내용은 없음.
 *     - 예: OrderService (주문 서비스가 무엇을 할 수 있는지 정의. 실제 코드는 없음)
 * - 구현체(Implementation):
 *     - 인터페이스에서 정의한 동작을 실제로 구현한 클래스.
 *     - 예: OrderServiceImpl (실제 주문 생성, 할인 적용 등의 로직 구현)
 * - 즉, 인터페이스는 '규약'이고, 구현체는 '실제 동작'임.
 */

public class Member {

    private Long id;
    private String name;
    private Grade grade;

    /**
     * Member 생성자
     * @param id 회원 ID
     * @param name 회원 이름
     * @param grade 회원 등급
     */
    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    /**
     * 회원 ID 반환
     * @return 회원 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 회원 이름 반환
     * @return 회원 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 회원 등급 반환
     * @return 회원 등급
     */
    public Grade getGrade() {
        return grade;
    }
    
}