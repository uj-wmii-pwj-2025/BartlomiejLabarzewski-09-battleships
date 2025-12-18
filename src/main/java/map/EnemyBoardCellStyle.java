package map;

public class EnemyBoardCellStyle implements BoardCellStyle {
    @Override
    public char unknown() {
        return '?';
    }

    @Override
    public char waterUnshot() {
        return '?';
    }

    @Override
    public char shipUnshot() {
        return '?';
    }

    @Override
    public char waterShot() {
        return '.';
    }

    @Override
    public char shipShot() {
        return '#';
    }
}
