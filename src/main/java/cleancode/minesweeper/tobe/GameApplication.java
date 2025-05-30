package cleancode.minesweeper.tobe;

public class GameApplication {

    //main 메서드는 전체 프로그램을 실행하는
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.run();
    }
}