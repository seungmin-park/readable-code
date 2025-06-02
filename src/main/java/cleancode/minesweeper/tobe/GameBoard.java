package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
        landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMinRow = new Random().nextInt(rowSize);
            int landMineCol = new Random().nextInt(colSize);
            LandMineCell landMineCell = new LandMineCell();
            board[landMinRow][landMineCol] = landMineCell;
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                if (isLandMineCell(cellPosition)) {
                    continue;
                }
                int count = countNearByLandMine(cellPosition);
                if (count == 0) {
                    continue;
                }
                NumberCell numberCell = new NumberCell(count);
                board[row][col] = numberCell;
            }
        }
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(CellPosition cellPosition) {
        Cell findCell = findCell(cellPosition);
        return findCell.getSign();
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
        return Arrays.stream(board).flatMap(Arrays::stream).allMatch(Cell::isChecked);
        //cell.getSign().equals() -> cell.EqualsSign -> cell.isClosed -> cell.isChecked
    }
}
