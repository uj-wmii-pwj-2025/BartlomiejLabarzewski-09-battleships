package tuic;

public abstract class TUIC {

    public int padding = 0;

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPadding() {
        return padding;
    }

    public int getPaddedWidth() {
        return 2 * padding + getWidth();
    }

    public int getPaddedHeight() {
        return 2 * padding + getHeight();
    }

    abstract public int getWidth();
    abstract public int getHeight();
    abstract public String[] draw();
}
