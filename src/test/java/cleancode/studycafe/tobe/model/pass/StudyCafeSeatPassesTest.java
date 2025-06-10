package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassesTest {

    @Test
    @DisplayName("좌석 이용권 목록 중에서 이용권 타입이 같은 이용권만 가져온다.")
    void findSeatPassesIsEqualsPassType() throws Exception {
        //given
        StudyCafeSeatPass oneHourSeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1000, 0);
        StudyCafeSeatPass twoHourSeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 2000, 0);
        StudyCafeSeatPass threeHourSeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 3, 3000, 0);
        StudyCafeSeatPass fourWeekSeatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 4, 4000, 0);
        StudyCafeSeatPass sixWeekSeatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 6, 5000, 0);
        StudyCafeSeatPass fixSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 6000, 0);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(
                List.of(oneHourSeatPass, twoHourSeatPass, threeHourSeatPass, fourWeekSeatPass, sixWeekSeatPass, fixSeatPass)
        );

        //when
        List<StudyCafeSeatPass> hourlyPasses = seatPasses.findPassBy(StudyCafePassType.HOURLY);

        //then
        assertThat(hourlyPasses).hasSize(3)
                .containsExactlyInAnyOrder(oneHourSeatPass, twoHourSeatPass, threeHourSeatPass);
    }
}