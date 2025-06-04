package cleancode.studycafe.tobe.model;

public class StudyCafeLockerPass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public boolean isSatisfiedAt(StudyCafePass studyCafePass) {
        return isSatisfiedPassType(studyCafePass) && isSatisfiedDuration(studyCafePass);
    }

    public boolean isAvailable() {
        return duration > 0 && price > 0;
    }

    public int getPrice() {
        return price;
    }

    public String display() {
        if (passType == StudyCafePassType.HOURLY) {
            return String.format("%s시간권 - %d원", duration, price);
        }
        if (passType == StudyCafePassType.WEEKLY) {
            return String.format("%s주권 - %d원", duration, price);
        }
        if (passType == StudyCafePassType.FIXED) {
            return String.format("%s주권 - %d원", duration, price);
        }
        return "";
    }

    private boolean isSatisfiedPassType(StudyCafePass studyCafePass) {
        return this.passType == studyCafePass.getPassType();
    }

    private boolean isSatisfiedDuration(StudyCafePass studyCafePass) {
        return this.duration == studyCafePass.getDuration();
    }
}
