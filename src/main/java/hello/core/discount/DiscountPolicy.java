package hello.core.discount;

import hello.core.member.Member;

/**
 * 할인 정책 인터페이스
 * 다양한 할인 정책을 구현할 수 있는 계약
 */
public interface DiscountPolicy {
    
    /**
     * 할인 금액 계산
     * @param member 할인 대상 회원
     * @param price 원래 가격
     * @return 할인된 금액
     */
    int discount(Member member, int price);
}
