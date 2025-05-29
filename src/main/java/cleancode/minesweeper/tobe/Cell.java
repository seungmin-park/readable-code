package cleancode.minesweeper.tobe;

public class Cell {
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String CLOSED_CELL_SIGN = "□";
    private static final String OPENED_CELL_SIGN = "■";

    private final String sign;

    private Cell(String sign) {
        this.sign = sign;
    }

    public static Cell of(String sign) {
        return new Cell(sign);
    }

    public static Cell ofFlag() {
        return new Cell(FLAG_SIGN);
    }

    public static Cell ofLandMine() {
        return new Cell(LAND_MINE_SIGN);
    }

    public static Cell ofClosedSign() {
        return new Cell(CLOSED_CELL_SIGN);
    }

    public static Cell ofOpened() {
        return new Cell(OPENED_CELL_SIGN);
    }

    public static Cell ofNearbyLandMineCount(int count) {
        return Cell.of(String.valueOf(count));
    }

    public String getSign() {
        return sign;
    }

    public boolean isClosed() {
        // NPE this.sign.equals()
        return CLOSED_CELL_SIGN.equals(this.sign);
    }

    public boolean doesNotClosed() {
        return !isClosed();
    }
}
