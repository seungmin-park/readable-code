package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class NumberCell implements Cell {
    private final int nearbyLandMineCount;
    private final CellState cellState = CellState.initialize();

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public CellSnapShot getSnapShot() {
        if (cellState.isOpened()) {
            return CellSnapShot.ofNumber(nearbyLandMineCount);
        }
        if (cellState.isFlagged()) {
            return CellSnapShot.ofFlag();
        }
        return CellSnapShot.ofUnchecked();
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isOpened();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
}
