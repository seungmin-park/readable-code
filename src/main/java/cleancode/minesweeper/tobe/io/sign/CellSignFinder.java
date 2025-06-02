package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapShot;

import java.util.List;

public class CellSignFinder {

    private static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
            new EmptyCellSignProvider(),
            new FlagCellSignProvider(),
            new LandMineCellSignProvider(),
            new NumberCellSignProvider(),
            new UnCheckedCellSignProvider()
    );

    public String findCellSignFrom(CellSnapShot snapShot) {
        return CELL_SIGN_PROVIDERS.stream()
                .filter(provider -> provider.supports(snapShot))
                .findFirst()
                .map(provider -> provider.provide(snapShot))
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }
}
