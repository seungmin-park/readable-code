package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassesTest {

    @Test
    @DisplayName("좌석 이용권의 타입과 기간이 사물함 이용권의 타입과 기간이 일치하면 사용할 수 있다.")
    void seatPassCanUseLockerPassMatchedDurationAndPassType() throws Exception {
        //given
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(
                List.of(
                        StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 100000),
                        StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 100000)
                )
        );
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 10000, 0);

        //when
        Optional<StudyCafeLockerPass> lockerPassOptional = lockerPasses.findLockerPassBy(seatPass);

        //then
        assertThat(lockerPassOptional).isPresent();
    }

    @Test
    @DisplayName("좌석 이용권의 타입과 기간이 사물함 이용권의 타입 또는 기간이 일치하지 않으면 사용할 수 없다.")
    void seatPassCanNotUseLockerPassNoneMatchedDurationOrPassType() throws Exception {
        //given
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(
                List.of(
                        StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 100000),
                        StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 100000)
                )
        );
        StudyCafeSeatPass durationNonMatchedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 5, 10000, 0);
        StudyCafeSeatPass typeNonMatchedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 4, 10000, 0);

        //when
        Optional<StudyCafeLockerPass> durationNonMatchedSeatPassOptional = lockerPasses.findLockerPassBy(durationNonMatchedSeatPass);
        Optional<StudyCafeLockerPass> typeNonMatchedSeatPassOptional = lockerPasses.findLockerPassBy(typeNonMatchedSeatPass);

        //then
        assertThat(durationNonMatchedSeatPassOptional).isEmpty();
        assertThat(typeNonMatchedSeatPassOptional).isEmpty();
    }
}