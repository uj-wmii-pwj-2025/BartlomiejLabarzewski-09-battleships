package tuic;

public class LabelTUIC extends TUIC {

    String line;
    int maxWidth;

    public LabelTUIC(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public int getWidth() {
        if (line.length() > maxWidth) {
            return maxWidth;
        }
        return line.length();
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public String[] draw() {
        if (line.length() <= maxWidth) {
            return new String[] {line};
        }
        else {
            return new String[] {line.substring(0, maxWidth - 1) + "…"};
        }
    }
}
