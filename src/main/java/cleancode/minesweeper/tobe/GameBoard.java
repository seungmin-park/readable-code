package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.List;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
        landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        CellPositions cellPositions = CellPositions.from(board);

        List<CellPosition> allPositions = cellPositions.getPositions();
        initializeEmptyCells(allPositions);

        List<CellPosition> landMineCellPositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMineCellPositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMineCellPositions);
        initializeNumberCells(numberPositionCandidates);
    }

    private void initializeEmptyCells(List<CellPosition> allPositions) {
        for (CellPosition position : allPositions) {
            updateCellAt(position, new EmptyCell());
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMineCellPositions) {
        for (CellPosition position : landMineCellPositions) {
            updateCellAt(position, new LandMineCell());
        }
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for (CellPosition candidatePosition : numberPositionCandidates) {
            int count = countNearByLandMine(candidatePosition);
            if (count != 0) {
                updateCellAt(candidatePosition, new NumberCell(count));
            }

        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    // board에 집근하여 cell를 꺼내는 작업이 많네?
    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowIndexMoreThanOrEqual(getRowSize()) || cellPosition.isColIndexMoreThanOrEqual(getColSize());
    }

    private int countNearByLandMine(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize)
                .stream()
                .filter(this::isLandMineCell)
                .count();

        return (int) count;
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public void openSurroundCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }
        openAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }


        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
        surroundedPositions.forEach(this::openSurroundCells);
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        Cell findCell = findCell(cellPosition);
        return findCell.hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell openedCell = findCell(cellPosition);
        return openedCell.isOpened();
    }

    public boolean isAllCellChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    public CellSnapShot getCellSnapShot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapShot();
    }
}
