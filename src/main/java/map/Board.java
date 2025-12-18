package map;

public class Board {

    private final int width;
    private final int height;
    BoardObjectCell[][] objects;
    BoardShotCell[][] shots;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        objects = new BoardObjectCell[height][width];
        shots = new BoardShotCell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                objects[y][x] = BoardObjectCell.UNKNOWN;
                shots[y][x] = BoardShotCell.NOT_SHOT;
            }
        }
    }

    public Board(char[][] map, int width, int height) {
        this.width = width;
        this.height = height;
        objects = new BoardObjectCell[height][width];
        shots = new BoardShotCell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                objects[y][x] = switch (map[y][x]) {
                    case '#' -> BoardObjectCell.SHIP;
                    case '.' -> BoardObjectCell.WATER;
                    default -> BoardObjectCell.UNKNOWN;
                };
                shots[y][x] = BoardShotCell.NOT_SHOT;
            }
        }
    }

    public void setShot(int row, int col) {
        shots[row][col] = BoardShotCell.SHOT;
    }

    public void setWater(int row, int col) {
        objects[row][col] = BoardObjectCell.WATER;
    }

    public void setShip(int row, int col) {
        objects[row][col] = BoardObjectCell.SHIP;
    }

    public BoardObjectCell getObject(int row, int col) {
        return objects[row][col];
    }

    public BoardShotCell getShot(int row, int col) {
        return shots[row][col];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
