package hello.core.order;

/**
 * 주문 정보를 담는 도메인 클래스
 * 주문의 전체 정보를 관리
 */
public class Order {

    private Long memberId;        // 주문한 회원 ID
    private String itemName;      // 주문 상품명
    private int itemPrice;         // 상품 가격
    private int discountPrice;     // 할인 금액

    /**
     * Order 생성자
     * @param memberId 주문한 회원 ID
     * @param itemName 주문 상품명
     * @param itemPrice 상품 가격
     * @param discountPrice 할인 금액
     */
    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    /**
     * 최종 결제 금액 계산
     * @return 상품 가격 - 할인 금액
     */
    public int calculatePrice() {
        return itemPrice - discountPrice;
    }

    /**
     * 주문한 회원 ID 반환
     * @return 회원 ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 주문 상품명 반환
     * @return 상품명
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 상품 가격 반환
     * @return 상품 가격
     */
    public int getItemPrice() {
        return itemPrice;
    }

    /**
     * 할인 금액 반환
     * @return 할인 금액
     */
    public int getDiscountPrice() {
        return discountPrice;
    }

    /**
     * 주문 정보를 문자열로 반환
     * @return 주문 정보 문자열
     */
    @Override
    public String toString() {
        return "Order{" +
                "memberId=" + memberId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
