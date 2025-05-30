package cleancode.minesweeper.tobe.cell;

public abstract class Cell {
    //열림 닫힘에서 체크 여부로 도메인 지식 변경
    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_CELL_SIGN = "□";

    protected boolean isFlagged;
    protected boolean isOpened;

    // Cell의 속성: 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태: 깃발 유무, 열림, 닫힘, 사용자가 확인함

    public abstract boolean isLandMine();

    public abstract String getSign();

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public abstract boolean hasLandMineCount();
}
