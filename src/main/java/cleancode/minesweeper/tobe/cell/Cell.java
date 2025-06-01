package cleancode.minesweeper.tobe.cell;

public interface Cell {
    //열림 닫힘에서 체크 여부로 도메인 지식 변경
    String FLAG_SIGN = "⚑";
    String UNCHECKED_CELL_SIGN = "□";

    boolean isLandMine();

    boolean hasLandMineCount();

    String getSign();

    void flag();

    void open();

    boolean isChecked();

    boolean isOpened();
}