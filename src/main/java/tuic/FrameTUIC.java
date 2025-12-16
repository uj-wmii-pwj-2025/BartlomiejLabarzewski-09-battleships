package tuic;

import framestyle.FrameStyle;

import java.util.Map;

public class FrameTUIC extends TUIC {

    private String title;
    private TUIC contents;
    private FrameStyle frameStyle;

    public FrameTUIC() {
        title = "";
        contents = null;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContents(TUIC contents) {
        this.contents = contents;
    }

    public TUIC getContents() {
        return contents;
    }

    @Override
    public int getWidth() {
        return contents.getWidth() + 2;
    }

    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    public void setFrameStyle(FrameStyle frameStyle) {
        this.frameStyle = frameStyle;
    }

    @Override
    public int getHeight() {
        return contents.getHeight() + 2;
    }

    @Override
    public String[] draw() {

        StringBuilder result = new StringBuilder();

        String[] contentLines = contents.draw();

        if (contentLines.length == 0) {
            return new String[0];
        }

        result.append(frameStyle.getTopLeftCorner());

        switch (getWidth()) {
            case 2:
                break;
            case 3:
                result.append(frameStyle.getHorizontalLine());
                break;
            case 4:
                result.append(frameStyle.getHorizontalLine());
                result.append(frameStyle.getHorizontalLine());
            default:
                result.append(frameStyle.getHorizontalLine());
                if (title.length() > contents.getWidth() - 2) {
                    result.append(title, 0, contents.getWidth() - 3).append("…");
                }
                else {
                    result.append(title);
                    result.append(String.valueOf(frameStyle.getHorizontalLine()).repeat(contents.getWidth() - title.length() - 2));
                }
                result.append(frameStyle.getHorizontalLine());
        }

        result.append(frameStyle.getTopRightCorner());

        result.append("\n");

        for (String line : contentLines) {
            result.append(frameStyle.getVerticalLine());
            result.append(line);
            result.append(frameStyle.getVerticalLine());
            result.append("\n");
        }

        result.append(frameStyle.getBottomLeftCorner());
        result.append(String.valueOf(frameStyle.getHorizontalLine()).repeat(Math.max(0, contents.getWidth())));
        result.append(frameStyle.getBottomRightCorner());

        result.append("\n");

        return result.toString().split("\n");
    }
}
