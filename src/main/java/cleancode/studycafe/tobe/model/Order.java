package cleancode.studycafe.tobe.model;

public class Order {

    private final StudyCafePass studyCafePass;
    private final StudyCafeLockerPass lockerPass;

    private Order(StudyCafePass studyCafePass, StudyCafeLockerPass studyCafeLockerPass) {
        this.studyCafePass = studyCafePass;
        this.lockerPass = studyCafeLockerPass;
    }

    public static Order of(StudyCafePass studyCafePass, StudyCafeLockerPass studyCafeLockerPass) {
        return new Order(studyCafePass, studyCafeLockerPass);
    }

    public static Order ofWithoutLocker(StudyCafePass selectedPass) {
        return new Order(selectedPass, StudyCafeLockerPass.of(selectedPass.getPassType(), 0, 0));
    }

    public static Order ofWithLocker(StudyCafePass selectedPass, StudyCafeLockerPass studyCafeLockerPass) {
        return new Order(selectedPass, studyCafeLockerPass);
    }

    public boolean hasLockerPass() {
        return lockerPass.isAvailable();
    }

    public boolean hasDiscount() {
        return studyCafePass.getDiscountRate() > 0;
    }

    public int calculateTotalPrice() {
        int discountPrice = calculateDiscountPrice();

        return studyCafePass.getPrice() - discountPrice + lockerPass.getPrice();
    }

    public int calculateDiscountPrice() {
        double discountRate = studyCafePass.getDiscountRate();
        return (int) (studyCafePass.getPrice() * discountRate);
    }

    public String displayStudyCafePass() {
        return studyCafePass.display();
    }

    public String displayLockerPass() {
        return lockerPass.display();
    }
}
