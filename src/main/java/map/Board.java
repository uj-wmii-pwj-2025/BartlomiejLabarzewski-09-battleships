package map;

import java.util.LinkedList;
import java.util.Queue;

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

    public boolean isSunk(int row, int col) {

        if (getObject(row, col) == BoardObjectCell.WATER) return false;
        if (row < 0 || row >= getHeight()) return false;
        if (col < 0 || col >= getWidth()) return false;

        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(new int[]{row, col});

        boolean[][] visited = new boolean[getHeight()][getWidth()];

        while (!queue.isEmpty()) {

            int[] cur = queue.poll();

            if (cur[0] < 0 || getHeight() <= cur[0]) continue;
            if (cur[1] < 0 || getWidth() <= cur[1]) continue;
            if (visited[cur[0]][cur[1]]) continue;
            if (getObject(cur[0], cur[1]) == BoardObjectCell.WATER) continue;

            if (getShot(cur[0], cur[1]) == BoardShotCell.NOT_SHOT) return false;
            if (!queue.contains(new int[]{cur[0] - 1, cur[1]})) {queue.add(new int[]{cur[0] - 1, cur[1]});}
            if (!queue.contains(new int[]{cur[0] + 1, cur[1]})) {queue.add(new int[]{cur[0] + 1, cur[1]});}
            if (!queue.contains(new int[]{cur[0], cur[1] - 1})) {queue.add(new int[]{cur[0], cur[1] - 1});}
            if (!queue.contains(new int[]{cur[0], cur[1] + 1})) {queue.add(new int[]{cur[0], cur[1] + 1});}

            visited[cur[0]][cur[1]] = true;

        }
        return true;
    }

    public boolean isAllSunk() {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (getObject(row, col) == BoardObjectCell.SHIP && getShot(row, col) == BoardShotCell.NOT_SHOT) return false;
            }
        }
        return true;
    }
}
