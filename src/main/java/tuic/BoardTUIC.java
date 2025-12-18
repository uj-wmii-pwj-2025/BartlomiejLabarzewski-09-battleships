package tuic;

import map.Board;
import map.BoardCellStyle;
import map.BoardObjectCell;
import map.BoardShotCell;

public class BoardTUIC extends TUIC {

    Board board;
    BoardCellStyle style;


    public BoardTUIC(BoardCellStyle style) {
        this.style = style;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
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

        return sb.toString().split("\n");
    }
}
