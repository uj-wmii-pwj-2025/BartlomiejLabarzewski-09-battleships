package map;

public class PlayerBoardCellStyle implements BoardCellStyle {

    @Override
    public char unknown() {
        return '?';
    }

    @Override
    public char waterUnshot() {
        return '.';
    }

    @Override
    public char shipUnshot() {
        return '#';
    }

    @Override
    public char waterShot() {
        return '~';
    }

    @Override
    public char shipShot() {
        return '@';
    }
}
