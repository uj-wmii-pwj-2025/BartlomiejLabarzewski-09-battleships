package tuic;

import java.util.stream.Stream;

public class SpacerTUIC extends TUIC {

    private final int width;
    private final int height;

    public SpacerTUIC(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String[] draw() {
        String line = " ".repeat(width);
        return Stream.generate(() -> line).limit(getHeight()).toArray(String[]::new);
    }
}
