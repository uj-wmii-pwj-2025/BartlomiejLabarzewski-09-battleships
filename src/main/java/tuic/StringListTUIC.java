package tuic;

import java.util.LinkedList;
import java.util.List;

public class StringListTUIC extends TUIC {

    private List<String> entries = new LinkedList<>();
    private int width;
    private int height;

    public StringListTUIC(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addEntry(String entry) {
        entries.add(entry);
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

        for (int i = 0; i < padding; i++) {
            sb.append(" ".repeat(getPaddedWidth())).append("\n");
        }

        List<String> lastEntries;

        if (entries.size() < getHeight()) {
            lastEntries = entries;
        }
        else {
            lastEntries = entries.subList(entries.size() - getHeight(), entries.size());
        }

        for (String s : lastEntries) {
            sb.append(" ".repeat(padding));
            if (s.length() <= getWidth()) {
                sb.append(s);
                sb.append(" ".repeat(getWidth() - s.length()));
            }
            else {
                sb.append(s, 0, getWidth() - 1).append("…");
            }
            sb.append(" ".repeat(padding));
            sb.append("\n");
        }

        for (int i = 0; i < getHeight() - lastEntries.size(); i++) {
            sb.append(" ".repeat(getPaddedWidth())).append("\n");
        }

        for (int i = 0; i < padding; i++) {
            sb.append(" ".repeat(getPaddedWidth())).append("\n");
        }

        return sb.toString().split("\n");
    }
}
