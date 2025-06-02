package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.exception.GameException;

public interface OutputHandler {
    void showGameStartComments();

    void showBoard(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    // Exception 메서지를 어떻게 출력할지에 대한 책임이 handler한테 있다고 판단
    void showExceptionMessage(GameException e);

    void showSimpleMessage(String message);
}
