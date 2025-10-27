package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

/**
 * 정액 할인 정책 구현체
 * VIP 회원에게 고정 금액을 할인해주는 정책
 */
public class FixDiscountPolicy implements DiscountPolicy {

    // 할인 금액 상수 (1000원 고정 할인)
    private int discountFixAmount = 1000;

    /**
     * 정액 할인 금액 계산
     * VIP 회원에게만 고정 금액을 할인해주고, BASIC 회원은 할인 없음
     * 
     * @param member 할인 대상 회원
     * @param price 원래 가격
     * @return 할인된 금액 (VIP: 고정 할인, BASIC: 0원)
     */
    @Override
    public int discount(Member member, int price) {
        // VIP 회원인 경우에만 할인 적용
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
