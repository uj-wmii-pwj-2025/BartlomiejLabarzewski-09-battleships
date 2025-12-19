package tuic;

import map.Board;
import map.BoardCellStyle;
import map.BoardObjectCell;
import map.BoardShotCell;

public class BoardTUIC extends TUIC {

    Board board;
    BoardCellStyle style;
    int chosenCellRow;
    int chosenCellCol;
    private boolean hasSelectedCell;


    public BoardTUIC(BoardCellStyle style) {
        this.style = style;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasChosenField() {
        return hasSelectedCell;
    }

    public void setChosenField(int row, int col) {
        chosenCellRow = row;
        chosenCellCol = col;
        hasSelectedCell = true;
    }

    public void setChosenCellRow(int row) {
        chosenCellRow = row;
    }

    public int getChosenCellRow() {
        return chosenCellRow;
    }

    public void setChosenCellCol(int col) {
        chosenCellCol = col;
    }

    public int getChosenCellCol() {
        return chosenCellCol;
    }

    public void unsetChosenField() {
        hasSelectedCell = false;
    }

    @Override
    public int getWidth() {
        return 2 * board.getWidth() - 1;
    }

    @Override
    public int getHeight() {
        return board.getHeight();
    }

    @Override
    public String[] draw() {

        if (board == null) {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    sb.append("E ");
                }
                sb.delete(sb.length() - 1, sb.length());
                sb.append("\n");
            }
            return sb.toString().split("\n");
        }

        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                BoardObjectCell cellObject = board.getObject(row, col);
                BoardShotCell cellShot = board.getShot(row, col);
                if (cellObject == BoardObjectCell.UNKNOWN) {
                    sb.append(style.unknown());
                }
                else if (cellObject == BoardObjectCell.WATER && cellShot == BoardShotCell.NOT_SHOT) {
                    sb.append(style.waterUnshot());
                }
                else if (cellObject == BoardObjectCell.WATER && cellShot == BoardShotCell.SHOT) {
                    sb.append(style.waterShot());
                }
                else if (cellObject == BoardObjectCell.SHIP && cellShot == BoardShotCell.NOT_SHOT) {
                    sb.append(style.shipUnshot());
                }
                else if (cellObject == BoardObjectCell.SHIP && cellShot == BoardShotCell.SHOT) {
                    sb.append(style.shipShot());
                }
                sb.append(" ");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append("\n");
        }

        String[] lines = sb.toString().split("\n");
        if (hasSelectedCell) {
            lines[chosenCellRow] = lines[chosenCellRow].substring(0, 2 * chosenCellCol) + "\u001b[0;36m" + lines[chosenCellRow].charAt(2 * chosenCellCol) + "\u001b[0m" + lines[chosenCellRow].substring(2 * chosenCellCol + 1, getWidth());
        }
        return lines;
    }
}
