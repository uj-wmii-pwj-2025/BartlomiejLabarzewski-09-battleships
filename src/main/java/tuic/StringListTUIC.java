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

        List<String> lastEntries;

        if (entries.size() <= getHeight()) {
            lastEntries = entries;
        }
        else {
            lastEntries = entries.subList(entries.size() - getHeight(), entries.size());
        }

        for (String s : lastEntries) {
            if (s.length() <= getWidth()) {
                sb.append(s);
                sb.append(" ".repeat(getWidth() - s.length()));
            }
            else {
                sb.append(s, 0, getWidth() - 1).append("…");
            }
            sb.append("\n");
        }

        for (int i = lastEntries.size(); i < getHeight() ; i++) {
            sb.append(" ".repeat(getWidth())).append("\n");
        }

        return sb.toString().split("\n");
    }
}
