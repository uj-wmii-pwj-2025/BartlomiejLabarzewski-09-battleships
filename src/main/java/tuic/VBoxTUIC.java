package tuic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VBoxTUIC extends TUIC {

    private TUIC[] components = new TUIC[0];
    public int spacing = 0;

    public TUIC[] getComponents() {
        return components;
    }

    public void setComponents(TUIC[] components) {
        this.components = components;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getSpacing() {
        return spacing;
    }

    public int getWidth() {
        int maxWidth = 0;
        for (TUIC component : components) {
            if (maxWidth < component.getWidth()) {
                maxWidth = component.getWidth();
            }
        }
        return maxWidth;
    }

    public int getHeight() {
        int heightSum = 0;
        for (TUIC component : components) {
            heightSum += component.getHeight();
            heightSum += spacing;
        }
        if (heightSum > 0) heightSum -= spacing;
        return heightSum;
    }

    public String[] draw() {

        StringBuilder sb = new StringBuilder();

        for (int componentID = 0;  componentID < components.length - 1; componentID++) {
            String[] componentImage = components[componentID].draw();
            for (String line : componentImage) {
                sb.append(line);
                sb.append(" ".repeat(getWidth() - components[componentID].getWidth())).append("\n");
            }
            for (int i = 0; i < spacing; i++) {
                sb.append(" ".repeat(getWidth())).append("\n");
            }
        }

        String[] componentImage = components[components.length - 1].draw();

        for (String line : componentImage) {
            sb.append(line);
            sb.append(" ".repeat(getWidth() - line.length())).append("\n");
        }

        return sb.toString().split("\n");
    }


}
