package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class NumberCellSignProvider implements CellSignProvidable {

    @Override
    public boolean supports(CellSnapShot snapShot) {
        return snapShot.isSameStatus(CellSnapshotStatus.NUMBER);
    }

    @Override
    public String provide(CellSnapShot cellSnapShot) {
        return String.valueOf(cellSnapShot.getNearbyLandMineCount());
    }
}
