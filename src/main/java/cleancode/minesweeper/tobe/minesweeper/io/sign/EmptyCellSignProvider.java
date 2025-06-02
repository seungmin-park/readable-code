package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class EmptyCellSignProvider implements CellSignProvidable {

    private static final String EMPTY_CELL_SIGN = "■";

    @Override
    public boolean supports(CellSnapShot snapShot) {
        return snapShot.isSameStatus(CellSnapshotStatus.EMPTY);
    }

    @Override
    public String provide(CellSnapShot cellSnapShot) {
        return EMPTY_CELL_SIGN;
    }
}
