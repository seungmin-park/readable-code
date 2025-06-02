package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;

import java.util.List;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;
    private GameStatus gameStatus;


    public GameBoard(GameLevel gameLevel) {
        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
        landMineCount = gameLevel.getLandMineCount();
        initializeGameStatus();
    }

    public void initializeGame() {
        initializeGameStatus();
        CellPositions cellPositions = CellPositions.from(board);

        List<CellPosition> allPositions = cellPositions.getPositions();
        initializeEmptyCells(allPositions);

        List<CellPosition> landMineCellPositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMineCellPositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMineCellPositions);
        initializeNumberCells(numberPositionCandidates);
    }

    public void openAt(CellPosition cellPosition) {
        if (isLandMineCell(cellPosition)) {
            openOneCellAt(cellPosition);
            changeGameStatusToLose();
            return;
        }

        openSurroundCells(cellPosition);
        checkIfGameIsOver();
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();

        checkIfGameIsOver();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowIndexMoreThanOrEqual(getRowSize()) || cellPosition.isColIndexMoreThanOrEqual(getColSize());
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }

    public CellSnapShot getCellSnapShot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapShot();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    private void initializeGameStatus() {
        gameStatus = GameStatus.IN_PROGRESS;
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

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }

    private void openSurroundCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }
        openOneCellAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }


        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
        surroundedPositions.forEach(this::openSurroundCells);
    }

    private void openOneCellAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        Cell findCell = findCell(cellPosition);
        return findCell.hasLandMineCount();
    }

    private boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell openedCell = findCell(cellPosition);
        return openedCell.isOpened();
    }

    private void checkIfGameIsOver() {
        if (isAllCellChecked()) {
            gameStatus = GameStatus.WIN;
        }
    }

    private boolean isAllCellChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    // board에 집근하여 cell를 꺼내는 작업이 많네?
    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }
}
