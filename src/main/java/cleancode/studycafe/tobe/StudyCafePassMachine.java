package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.Order;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showGameStartComments();

            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            calculateStudyCafePrice(studyCafePassType);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void calculateStudyCafePrice(StudyCafePassType studyCafePassType) {
        if (studyCafePassType == StudyCafePassType.HOURLY) {
            List<StudyCafePass> hourlyPasses = findStudyCafePasses(StudyCafePassType.HOURLY);

            outputHandler.showPassListForSelection(hourlyPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
            Order order = Order.ofHourly(selectedPass);
            outputHandler.showPassOrderSummary(order);
            return;
        }
        if (studyCafePassType == StudyCafePassType.WEEKLY) {
            List<StudyCafePass> weeklyPasses = findStudyCafePasses(StudyCafePassType.WEEKLY);

            outputHandler.showPassListForSelection(weeklyPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
            Order order = Order.ofWeekly(selectedPass);
            outputHandler.showPassOrderSummary(order);
            return;
        }
        if (studyCafePassType == StudyCafePassType.FIXED) {
            List<StudyCafePass> fixedPasses = findStudyCafePasses(StudyCafePassType.FIXED);

            outputHandler.showPassListForSelection(fixedPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

            Order order = Order.ofMonthlyWithoutLocker(selectedPass);

            StudyCafeLockerPass lockerPass = findStudyCafeLockerPass(selectedPass);
            outputHandler.askLockerPass(lockerPass);

            boolean lockerSelection = inputHandler.getLockerSelection();
            if (lockerSelection) {
                order = Order.ofMonthlyWithLocker(selectedPass, lockerPass);
            }

            outputHandler.showPassOrderSummary(order);
        }
    }

    //todo optional orElse null 수정하기
    private StudyCafeLockerPass findStudyCafeLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
        return lockerPass;
    }

    private List<StudyCafePass> findStudyCafePasses(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }

}
