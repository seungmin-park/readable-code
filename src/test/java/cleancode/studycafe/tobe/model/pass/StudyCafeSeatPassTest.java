package cleancode.studycafe.tobe.model.pass;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassTest {

    @Test
    @DisplayName("좌석 이용권의 타입과 같으면 참을 반환한다.")
    void returnTruePassTypeEqualsSeatPassType() throws Exception {
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 4, 4000, 0.0);
        //when
        boolean result = seatPass.isSamePassType(StudyCafePassType.WEEKLY);
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("좌석 이용권의 타입과 다르면 거짓을 반환한다.")
    void returnFalsePassTypeNotEqualsSeatPassType() throws Exception {
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 4, 4000, 0.0);
        //when
        boolean result = seatPass.isSamePassType(StudyCafePassType.HOURLY);
        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("좌석 이용권의 타입과 기간이 사물함 이용권의 타입과 기간이 일치하면 참을 반환한다.")
    void returnTrueSeatPassEqualLockerPassOfDurationAndType() throws Exception {
        //given
        StudyCafePassType fixedPassType = StudyCafePassType.FIXED;
        int passTypeDuration = 4;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(fixedPassType, passTypeDuration, 250000, 0);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(fixedPassType, passTypeDuration, 1000);

        //when
        boolean result = seatPass.isSameDurationType(lockerPass);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("좌석 이용권의 타입 혹은 기간이 사물함 이용권과 일치하지 않으면 거짓을 반환한다.")
    void returnFalseSeatPassNotEqualLockerPassOfDurationOrType() throws Exception {
        //given
        StudyCafePassType fixedPassType = StudyCafePassType.FIXED;
        int passTypeDuration = 4;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(fixedPassType, passTypeDuration, 250000, 0);
        StudyCafeLockerPass passTypeNoneMatchedLockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, passTypeDuration, 1000);
        StudyCafeLockerPass durationNoneMatchedLockerPass = StudyCafeLockerPass.of(fixedPassType, 12, 1000);

        //when
        boolean result1 = seatPass.isSameDurationType(passTypeNoneMatchedLockerPass);
        boolean result2 = seatPass.isSameDurationType(durationNoneMatchedLockerPass);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("좌석 이용권의 할인 금액은 (금액 * 할인 비율)이다.")
    void discountPriceEqualsPriceMultiplyDiscountRate() throws Exception {
        //given
        double discountRate = 0.1;
        int price = 250000;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, price, discountRate);
        //when
        int discountPrice = seatPass.getDiscountPrice();

        //then
        assertThat(discountPrice).isEqualTo((int) (price * discountRate));
    }
}