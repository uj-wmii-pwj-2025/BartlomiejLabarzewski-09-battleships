package tuic;

public class PadPaneTUIC extends TUIC {

    private int padding;
    private TUIC component;

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setComponent(TUIC component) {
        this.component = component;
    }

    @Override
    public int getWidth() {
        return component.getWidth() + 6 * padding;
    }

    @Override
    public int getHeight() {
        return component.getHeight() + 2 * padding;
    }

    @Override
    public String[] draw() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < padding; i++) {
            sb.append(" ".repeat(getWidth())).append("\n");
        }

        String[] componentImage = component.draw();

        for (String line : componentImage) {
            sb.append(" ".repeat(getPadding() * 3));
            sb.append(line);
            sb.append(" ".repeat(getPadding() * 3));
            sb.append("\n");
        }

        for (int i = 0; i < padding; i++) {
            sb.append(" ".repeat(getWidth())).append("\n");
        }

        return sb.toString().split("\n");
    }


}
