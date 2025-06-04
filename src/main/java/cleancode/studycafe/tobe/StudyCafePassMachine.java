package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.*;

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
        StudyCafePasses studyCafePasses = StudyCafePasses.from(studyCafeFileHandler);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.from(studyCafeFileHandler);

        if (studyCafePassType == StudyCafePassType.HOURLY) {
            List<StudyCafePass> hourlyPasses = studyCafePasses.findStudyCafePasses(StudyCafePassType.HOURLY);

            outputHandler.showPassListForSelection(hourlyPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
            Order order = Order.ofHourly(selectedPass);
            outputHandler.showPassOrderSummary(order);
            return;
        }
        if (studyCafePassType == StudyCafePassType.WEEKLY) {
            List<StudyCafePass> weeklyPasses = studyCafePasses.findStudyCafePasses(StudyCafePassType.WEEKLY);

            outputHandler.showPassListForSelection(weeklyPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
            Order order = Order.ofWeekly(selectedPass);
            outputHandler.showPassOrderSummary(order);
            return;
        }
        if (studyCafePassType == StudyCafePassType.FIXED) {
            List<StudyCafePass> fixedPasses = studyCafePasses.findStudyCafePasses(StudyCafePassType.FIXED);

            outputHandler.showPassListForSelection(fixedPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

            StudyCafeLockerPass lockerPass = lockerPasses.findStudyCafeLockerPass(selectedPass);

            outputHandler.askLockerPass(lockerPass);

            boolean lockerSelection = inputHandler.getLockerSelection();
            Order order = Order.ofMonthlyWithoutLocker(selectedPass);
            if (lockerSelection) {
                order = Order.ofMonthlyWithLocker(selectedPass, lockerPass);
            }

            outputHandler.showPassOrderSummary(order);
        }
    }
}
