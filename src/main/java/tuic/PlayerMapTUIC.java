package tuic;

public class PlayerMapTUIC extends TUIC {

    char[][] map;
    int width;
    int height;


    public PlayerMapTUIC(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return 2 * width - 1;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    @Override
    public String[] draw() {

        if (map == null) {
            map = new char[height][width];
            for (int i = 0; i < height; i++) {
                map[i] = new char[width];
                for (int j = 0; j < width; j++) {
                    map[i][j] = '#';
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (char[] chars : map) {
            for (char aChar : chars) {
                sb.append(aChar).append(" ");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append("\n");
        }

        return sb.toString().split("\n");
    }
}
