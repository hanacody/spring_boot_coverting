package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

/**
 * 정률 할인 정책 구현체
 * VIP 회원에게 가격의 일정 비율을 할인해주는 정책
 */
public class RateDiscountPolicy implements DiscountPolicy {

    // 할인 비율 상수 (10% 할인)
    private int discountPercent = 10;

    /**
     * 정률 할인 금액 계산
     * VIP 회원에게 가격의 10%를 할인해주고, BASIC 회원은 할인 없음
     * 
     * @param member 할인 대상 회원
     * @param price 원래 가격
     * @return 할인된 금액 (VIP: 가격의 10%, BASIC: 0원)
     */
    @Override
    public int discount(Member member, int price) {
        // VIP 회원인 경우에만 할인 적용
        if (member.getGrade() == Grade.VIP) {
            // 가격의 할인 비율만큼 할인 금액 계산
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
