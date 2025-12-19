package controllers;

import tuic.BoardTUIC;

public class BoardController {

    public BoardTUIC boardTUIC;

    public void setBoardTUIC(BoardTUIC boardTUIC) {
        this.boardTUIC = boardTUIC;
    }

    public void moveRight() {
        if (!boardTUIC.hasChosenField()) return;
        if (boardTUIC.getChosenCellCol() == boardTUIC.getWidth() - 1) return;
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
}
