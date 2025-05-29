package cleancode.minesweeper.tobe;

public class Cell {
    //열림 닫힘에서 체크 여부로 도메인 지식 변경
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_CELL_SIGN = "□";
    private static final String EMPTY_CELL_SIGN = "■";

    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlagged;
    private boolean isOpened;

    // Cell의 속성: 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태: 깃발 유무, 열림, 닫힘, 사용자가 확인함

    private Cell(int nearbyLandMineCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static Cell create() {
        return new Cell(0, false, false, false);
    }

    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isLandMine() {
        return isLandMine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean hasLandMineCount() {
        return this.nearbyLandMineCount != 0;
    }

    public String getSign() {
        // 열렸을 떄
        if (isOpened) {
            //지뢰인가?
            if (isLandMine) {
                return LAND_MINE_SIGN;
            }
            //주변에 지뢰가 존재하여 1이상의 수인가?
            if (hasLandMineCount()) {
                return String.valueOf(nearbyLandMineCount);
            }
            // 빈 셀
            return EMPTY_CELL_SIGN;
        }

        //닫혔을 때
        //깃발 꽂혔을 때
        if (isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_CELL_SIGN;
    }
}
