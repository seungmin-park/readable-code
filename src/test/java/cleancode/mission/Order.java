package cleancode.mission;

import java.util.List;

// 1.else 제거

/* 기조흐름
1. 주문의 아이템 수가 0이면 return false
    else
    2. 주문의 총 금액이 0보다 크면?
        2.1 회원 정보가 존재하지 않으면 return false
            else return true
        else
        2.2 주문 금액이 0보다 작으면 return false
 */

/* early return 방식으로 변경
1. 주문의 아이템 수가 0이면 return false
2. 주문 금액이 0보다 작으면 return false
3. 회원 정보가 존재하지 않으면 return false
depth를 줄일 수 있다.
order.getItems().size() == 0 hasItem으로 물어보는 형식으로 변경
아이템 뭉치를 강탈해 확인하는게 굉장히 무례하다.
존재해? 그렇지 않으면~ -> 존재하지 않으면
 */
public class Order {

    public boolean validateOrder(Order order) {
        if (order.hasItem()) {
            System.out.println("주문 항목이 없습니다.");
            return false;
        }
        if (order.isCustomerInfoMissing()) {
            System.out.println("사용자 정보가 없습니다.");
            return false;
        }
        if (order.hasInValidTotalPrice()) {
            System.out.println("올바르지 않은 총 가격입니다.");
            return false;
        }

        return true;
    }

    private boolean hasInValidTotalPrice() {
        return !hasValidTotalPrice();
    }

    private boolean hasValidTotalPrice() {
        return getTotalPrice() > 0;
    }

    private boolean isCustomerInfoMissing() {
        return !hasCustomerInfo();
    }

    private boolean hasItem() {
        return true;
    }

    private List<Item> getItems() {
        return List.of();
    }

    private boolean hasCustomerInfo() {
        return false;
    }

    private int getTotalPrice() {
        return 0;
    }

    private static class Item {

    }

}
