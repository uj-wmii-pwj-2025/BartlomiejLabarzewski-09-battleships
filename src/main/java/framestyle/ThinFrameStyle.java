package framestyle;

public class ThinFrameStyle implements FrameStyle {

    @Override
    public char getTopLeftCorner() {
        return '┌';
    }

    @Override
    public char getTopRightCorner() {
        return '┐';
    }

    @Override
    public char getBottomLeftCorner() {
        return '└';
    }

    @Override
    public char getBottomRightCorner() {
        return '┘';
    }

    @Override
    public char getHorizontalLine() {
        return '─';
    }

    @Override
    public char getVerticalLine() {
        return '│';
    }

}
