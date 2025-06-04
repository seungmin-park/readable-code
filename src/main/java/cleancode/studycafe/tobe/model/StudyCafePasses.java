package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.io.reader.StudyCafeDataReader;

import java.util.List;

public class StudyCafePasses {
    private final List<StudyCafePass> studyCafePasses;

    private StudyCafePasses(List<StudyCafePass> studyCafePasses) {
        this.studyCafePasses = studyCafePasses;
    }

    public static StudyCafePasses of(List<StudyCafePass> studyCafePasses) {
        return new StudyCafePasses(studyCafePasses);
    }

    public static StudyCafePasses from(StudyCafeDataReader studyCafeDataReader) {
        List<StudyCafePass> readCafePasses = studyCafeDataReader.readStudyCafePasses();
        return of(readCafePasses);
    }

    public List<StudyCafePass> findStudyCafePasses(StudyCafePassType studyCafePassType) {
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }
}
