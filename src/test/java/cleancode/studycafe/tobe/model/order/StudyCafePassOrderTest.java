package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    @Test
    @DisplayName("최종 주문 금액은 ((좌석 이용 금액 + 사물함 이용 금액) - 할인 금액)이다.")
    void getTotalPrice() throws Exception {
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 10_000, 0.1);

        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 4_000);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        int seatPassPrice = seatPass.getPrice();
        int lockerPassPrice = lockerPass.getPrice();

        //when
        int totalPrice = passOrder.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo((seatPassPrice + lockerPassPrice) - passOrder.getDiscountPrice());
    }
}