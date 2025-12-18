package tuic;

public class CenteringPaneTUIC extends TUIC {

    TUIC component;

    int width;
    int height;

    public CenteringPaneTUIC(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setComponent(TUIC component) {
        this.component = component;
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

        StringBuilder sb = new StringBuilder();

        int trailingLines = (getHeight() - component.getHeight()) / 2;
        int leadingLines = getHeight() - component.getHeight() - trailingLines;

        int trailingSpaces = (getWidth() - component.getWidth()) / 2;
        int leadingSpaces = getWidth() - component.getWidth() - trailingSpaces;

        String[] componentImage = component.draw();

        for (int i = 0; i < trailingLines; i++) {
            sb.append(" ".repeat(getWidth()));
            sb.append("\n");
        }
        for (int i = 0; i < component.getHeight(); i++) {
            sb.append(" ".repeat(trailingSpaces));
            sb.append(componentImage[i]);
            sb.append(" ".repeat(leadingSpaces));
            sb.append("\n");
        }
        for (int i = 0; i < leadingLines; i++) {
            sb.append(" ".repeat(getWidth()));
            sb.append("\n");
        }

        return sb.toString().split("\n");

    }
}
