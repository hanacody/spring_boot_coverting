package hello.core.order;

/**
 * 주문 서비스 인터페이스
 * 주문 관련 비즈니스 로직을 정의하는 계약
 */
public interface OrderService {

    /**
     * 주문 생성
     * 회원 정보를 조회하고 할인 정책을 적용하여 주문을 생성
     * 
     * @param memberId 주문한 회원 ID
     * @param itemName 주문 상품명
     * @param itemPrice 상품 가격
     * @return 생성된 주문 정보
     */
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
