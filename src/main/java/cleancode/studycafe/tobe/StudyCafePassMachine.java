package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.ConsoleInputHandler;
import cleancode.studycafe.tobe.io.ConsoleOutputHandler;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.reader.CsvStudyCafeDataReader;
import cleancode.studycafe.tobe.io.reader.StudyCafeDataReader;
import cleancode.studycafe.tobe.model.*;

import java.util.List;

public class StudyCafePassMachine {

    private final StudyCafeDataReader studyCafeDataReader = new CsvStudyCafeDataReader();
    private final InputHandler inputHandler = new ConsoleInputHandler();
    private final OutputHandler outputHandler = new ConsoleOutputHandler();

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
        StudyCafePasses studyCafePasses = StudyCafePasses.from(studyCafeDataReader);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.from(studyCafeDataReader);

        List<StudyCafePass> selectedPasses = studyCafePasses.findStudyCafePasses(studyCafePassType);
        outputHandler.showPassListForSelection(selectedPasses);
        StudyCafePass selectedPass = inputHandler.getSelectPass(selectedPasses);
        Order order = Order.ofWithoutLocker(selectedPass);

        if (selectedPass.isFixed()) {
            StudyCafeLockerPass lockerPass = lockerPasses.findStudyCafeLockerPass(selectedPass);
            outputHandler.askLockerPass(lockerPass);
            boolean lockerSelection = inputHandler.getLockerSelection();

            if (lockerSelection) {
                order = Order.ofWithLocker(selectedPass, lockerPass);
            }
        }
        outputHandler.showPassOrderSummary(order);

    }
}
