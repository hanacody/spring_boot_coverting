package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * 정액 할인 정책 테스트
 * FixDiscountPolicy의 할인 로직을 검증
 * 
 * 테스트 시나리오:
 * 1. VIP 회원에게 고정 1000원 할인 적용
 * 2. BASIC 회원은 할인 없음
 * 3. 가격이 할인 금액보다 작아도 VIP는 고정 할인 적용
 * 4. 가격에 관계없이 항상 동일한 할인 금액 적용
 */
class FixDiscountPolicyTest {

    FixDiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Test
    @DisplayName("VIP는 1000원 할인")
    void vip_o() {
        // Given: VIP 회원과 가격 설정
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        int price = 10000;

        // When: 할인 금액 계산
        int discount = discountPolicy.discount(member, price);

        // Then: VIP 회원은 1000원 할인
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
    @DisplayName("가격이 할인 금액보다 작아도 VIP는 고정 할인")
    void vip_small_price() {
        // Given: VIP 회원과 할인 금액보다 작은 가격
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        int price = 500; // 할인 금액(1000원)보다 작음

        // When: 할인 금액 계산
        int discount = discountPolicy.discount(member, price);

        // Then: VIP 회원은 여전히 1000원 할인 (음수가 될 수 있음)
        assertThat(discount).isEqualTo(1000);
    }
}
