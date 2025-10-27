package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
// 테스트 코드에서 사용되는 assertThat 등의 검증 메서드 제공
// assertThat(actual).isEqualTo(expected)와 같이 사용하여 테스트 대상의 실제 값과 기대 값을 비교할 수 있음
// 코드가 가독성 좋고 간결하게 테스트 검증을 작성할 수 있도록 도와줌
/**
 * 스태틱 임포트(static import)는 특정 클래스의 static 멤버(메서드 또는 상수)를 
 * 클래스 이름 없이 직접 사용할 수 있게 해주는 Java의 임포트 방식입니다.
 * 
 * 예를 들어, 아래와 같이 작성하면
 * import static org.assertj.core.api.Assertions.*;
 * 
 * Assertions 클래스의 assertThat 등 static 메서드를
 * Assertions.assertThat(...) 대신에 assertThat(...) 형태로 바로 사용할 수 있습니다.
 * 
 * 이는 테스트 코드에서 검증 메서드나 상수 호출을 더 간결하고 읽기 쉽게 만들어줍니다.
 */
/**
 * static 메서드(정적 메서드)란?
 *
 * - 클래스에 소속된 메서드를 의미하며, 인스턴스를 생성하지 않고도 클래스명으로 직접 호출할 수 있습니다.
 * - 메모리에 클래스가 로드될 때 함께 메서드 영역에 올라가며, 객체의 상태(필드)에 의존하지 않는 로직에 주로 사용합니다.
 * - 대표적인 예로 Java의 Math 클래스의 Math.abs(), Math.max(), 그리고 JUnit, AssertJ 등의 테스트 라이브러리에서 제공하는 assertThat() 같은 검증 메서드들이 static 메서드입니다.
 * - 사용 예:
 *     - 클래스명.메서드명(파라미터);
 *     - 예시: Math.max(10, 20); // 20 반환
 *     - 예시: Assertions.assertThat(actual).isEqualTo(expected);
 */



/**
 * 정률 할인 정책 테스트
 * RateDiscountPolicy의 할인 로직을 검증
 * 
 * 테스트 시나리오:
 * 1. VIP 회원에게 가격의 10% 할인 적용
 * 2. BASIC 회원은 할인 없음
 * 3. 가격에 비례하여 할인 금액 계산
 * 4. 소수점 발생 시 정수로 처리 (버림)
 * 5. 다양한 가격대에서 정확한 할인율 적용 확인
 */


class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인")
    void vip_o() {
        // Given: VIP 회원과 가격 설정
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        int price = 10000;

        // When: 할인 금액 계산
        int discount = discountPolicy.discount(member, price);

        // Then: VIP 회원은 10% 할인 (1000원)
        // assertThat은 AssertJ가 제공하는 검증 메서드로, 테스트 대상 값(discount)이 기대값(1000)과 같은지 비교하여 다르면 테스트가 실패합니다.
        // 코드: assertThat(실제값).isEqualTo(기대값);
        // 예시: assertThat(discount).isEqualTo(1000); → discount가 1000이어야 테스트가 성공함
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("BASIC은 할인 없음")
    void basic_x() {
        // Given: BASIC 회원과 가격 설정
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);
        int price = 10000;

        // When: 할인 금액 계산
        int discount = discountPolicy.discount(member, price);

        // Then: BASIC 회원은 할인 없음
        assertThat(discount).isEqualTo(0);
    }

    @Test
    @DisplayName("VIP는 가격에 비례하여 할인")
    void vip_proportional_discount() {
        // Given: VIP 회원과 다양한 가격들
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        // When & Then: 각 가격별로 10% 할인 확인
        assertThat(discountPolicy.discount(member, 1000)).isEqualTo(100);  // 10%
        assertThat(discountPolicy.discount(member, 2000)).isEqualTo(200);  // 10%
        assertThat(discountPolicy.discount(member, 5000)).isEqualTo(500);  // 10%
        assertThat(discountPolicy.discount(member, 10000)).isEqualTo(1000); // 10%
    }

    @Test
    @DisplayName("할인 금액이 소수점인 경우 정수로 반올림")
    void vip_decimal_discount() {
        // Given: VIP 회원과 소수점이 발생하는 가격
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        int price = 3333; // 10% 할인 시 333.3원

        // When: 할인 금액 계산
        int discount = discountPolicy.discount(member, price);

        // Then: 소수점은 버림 처리 (333원)
        assertThat(discount).isEqualTo(333);
    }
}
