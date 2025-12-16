package tuic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HBoxTUIC extends TUIC {

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
        int widthSum = 0;
        for (TUIC component : components) {
            widthSum += component.getWidth();
            widthSum += spacing * 3;
        }
        if (widthSum > 0) widthSum -= spacing * 3;
        return widthSum;
    }

    public int getHeight() {
        int maxHeight = 0;
        for (TUIC component : components) {
            if (maxHeight < component.getHeight()) {
                maxHeight = component.getHeight();
            }
        }
        return maxHeight;
    }

    public String[] draw() {

        StringBuilder sb = new StringBuilder();

        List<String[]> componentImages = Arrays.stream(components).map(TUIC::draw).toList();

        for (int col = 0; col < getHeight(); col++) {
            for (int i = 0; i < components.length; i++) {
                if (components[i].getHeight() > col) {
                    sb.append(componentImages.get(i)[col]);
                }
                else {
                    sb.append(" ".repeat(components[i].getWidth()));
                }
                sb.append(" ".repeat(spacing * 3));
            }
            sb.delete(sb.length() - spacing * 3, sb.length());
            sb.append("\n");
        }
        return sb.toString().split("\n");
    }


}
