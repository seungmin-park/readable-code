package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.io.reader.StudyCafeDataReader;

import java.util.List;

public class StudyCafeLockerPasses {
    private final List<StudyCafeLockerPass> lockerPasses;

    private StudyCafeLockerPasses(List<StudyCafeLockerPass> lockerPasses) {
        this.lockerPasses = lockerPasses;
    }

    public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> lockerPasses) {
        return new StudyCafeLockerPasses(lockerPasses);
    }

    public static StudyCafeLockerPasses from(StudyCafeDataReader studyCafeDataReader) {
        List<StudyCafeLockerPass> readLockerPasses = studyCafeDataReader.readLockerPasses();
        return of(readLockerPasses);
    }

    public StudyCafeLockerPass findStudyCafeLockerPass(StudyCafePass selectedPass) {
        return lockerPasses.stream()
                .filter(option -> option.isSatisfiedAt(selectedPass))
                .findFirst()
                .orElse(StudyCafeLockerPass.of(StudyCafePassType.FIXED, 0, 0));
    }
}
