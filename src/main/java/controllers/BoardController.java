package controllers;

import map.BoardObjectCell;
import map.BoardShotCell;
import tuic.BoardTUIC;

import misc.Coordinates;

public class BoardController {

    public BoardTUIC boardTUIC;

    public void setBoardTUIC(BoardTUIC boardTUIC) {
        this.boardTUIC = boardTUIC;
    }

    public BoardTUIC getBoardTUIC() {
        return boardTUIC;
    }

    public void moveRight() {
        if (!boardTUIC.hasChosenField()) return;
        if (boardTUIC.getChosenCellCol() == boardTUIC.getBoard().getWidth() - 1) return;
        boardTUIC.setChosenCellCol(boardTUIC.getChosenCellCol() + 1);
    }

    public void moveLeft() {
        if (!boardTUIC.hasChosenField()) return;
        if (boardTUIC.getChosenCellCol() == 0) return;
        boardTUIC.setChosenCellCol(boardTUIC.getChosenCellCol() - 1);
    }

    public void moveUp() {
        if (!boardTUIC.hasChosenField()) return;
        if (boardTUIC.getChosenCellRow() == 0) return;
        boardTUIC.setChosenCellRow(boardTUIC.getChosenCellRow() - 1);
    }

    public void moveDown() {
        if (!boardTUIC.hasChosenField()) return;
        if (boardTUIC.getChosenCellRow() == boardTUIC.getHeight() - 1) return;
        boardTUIC.setChosenCellRow(boardTUIC.getChosenCellRow() + 1);
    }

    public String getChosenCell() {
        if (!boardTUIC.hasChosenField()) return null;

        int row = boardTUIC.getChosenCellRow();
        int col = boardTUIC.getChosenCellCol();

        return Coordinates.getCellFromCoordinates(row, col);
    }

    public BoardObjectCell getObjectStatus(String cell) {
        return boardTUIC.getBoard().getObject(Coordinates.getCellRow(cell), Coordinates.getCellCol(cell));
    }

    public BoardShotCell getShotStatus(String cell) {
        return boardTUIC.getBoard().getShot(Coordinates.getCellRow(cell), Coordinates.getCellCol(cell));
    }

    public void markShot(String cell) {
        boardTUIC.getBoard().setShot(Coordinates.getCellRow(cell), Coordinates.getCellCol(cell));
    }

    public void markWater(String cell) {
        boardTUIC.getBoard().setWater(Coordinates.getCellRow(cell), Coordinates.getCellCol(cell));
    }

    public void markShip(String cell) {
        boardTUIC.getBoard().setShip(Coordinates.getCellRow(cell), Coordinates.getCellCol(cell));
    }

}
