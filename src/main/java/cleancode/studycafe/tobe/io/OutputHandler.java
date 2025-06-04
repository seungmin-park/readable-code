package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.Order;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;

import java.util.List;

public interface OutputHandler {

    void showGameStartComments();

    void showPassListForSelection(List<StudyCafePass> passes);

    void askLockerPass(StudyCafeLockerPass lockerPass);

    void showPassOrderSummary(Order order);

    void showSimpleMessage(String message);
}
