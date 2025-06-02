package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class UnCheckedCellSignProvider implements CellSignProvidable {

    private static final String UNCHECKED_CELL_SIGN = "□";

    @Override
    public boolean supports(CellSnapShot snapShot) {
        return snapShot.isSameStatus(CellSnapshotStatus.UNCHECKED);
    }

    @Override
    public String provide(CellSnapShot cellSnapShot) {
        return UNCHECKED_CELL_SIGN;
    }
}
