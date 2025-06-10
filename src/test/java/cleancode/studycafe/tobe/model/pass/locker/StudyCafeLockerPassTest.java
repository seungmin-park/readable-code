package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassTest {

    @Test
    @DisplayName("사물함 이용권과 이용권 타입이 같으면 참을 반환한다.")
    void returnTruePassTypeEqualsLockerPassType() throws Exception {
        //given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 4000);
        //when
        boolean result = lockerPass.isSamePassType(StudyCafePassType.FIXED);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사물함 이용권과 이용권 타입이 다르면 거짓을 반환한다.")
    void returnFalsePassTypeNotEqualsLockerPassType() throws Exception {
        //given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 4000);
        //when
        boolean result = lockerPass.isSamePassType(StudyCafePassType.HOURLY);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("사물함 이용권과 이용 기간이 같으면 참을 반환한다.")
    void returnTrueDurationEqualsLockerPassDuration() throws Exception {
        //given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 4000);
        //when
        boolean result = lockerPass.isSameDuration(4);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사물함 이용권과 이용권 기간이 다르면 거짓을 반환한다.")
    void returnFalseDurationNotEqualsLockerPassDuration() throws Exception {
        //given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 4000);
        //when
        boolean result = lockerPass.isSameDuration(5);

        //then
        assertThat(result).isFalse();
    }
}