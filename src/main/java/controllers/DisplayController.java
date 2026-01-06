package controllers;

import map.Board;
import tuic.*;

public class DisplayController {

    private TUIC rootTUIC;
    private BoardTUIC playerBoardTUIC;
    private BoardTUIC enemyBoardTUIC;
    private StringListTUIC historyTUIC;
    private StringListTUIC chatTUIC;
    private LabelTUIC statusTUIC;
    private BoardController playerBoardController;
    private BoardController enemyBoardController;

    public void setPlayerBoardTUIC(BoardTUIC playerBoardTUIC) {
        this.playerBoardTUIC = playerBoardTUIC;
    }

    public void setEnemyBoardTUIC(BoardTUIC enemyBoardTUIC) {
        this.enemyBoardTUIC = enemyBoardTUIC;
    }

    public void setHistoryTUIC(StringListTUIC historyTUIC) {
        this.historyTUIC = historyTUIC;
    }

    public void setChatTUIC(StringListTUIC chatTUIC) {
        this.chatTUIC = chatTUIC;
    }

    public void setStatusTUIC(LabelTUIC statusTUIC) {
        this.statusTUIC = statusTUIC;
    }

    public void setRootTUIC(TUIC rootTUIC) {
        this.rootTUIC = rootTUIC;
    }

    public void setChosenField(String field) {
        int row = field.charAt(0) - 'A';
        int col = field.charAt(1) - '1';
        enemyBoardTUIC.setChosenField(row, col);
    }

    public void markYourCellShot(int row, int col) {
        playerBoardTUIC.getBoard().setShot(row, col);
    }

    public void markEnemyCellWater(int row, int col) {
        enemyBoardTUIC.getBoard().setWater(row, col);
        enemyBoardTUIC.getBoard().setShot(row, col);
    }

    public void markEnemyCellShip(int row, int col) {
        enemyBoardTUIC.getBoard().setShip(row, col);
        enemyBoardTUIC.getBoard().setShot(row, col);
    }

    public void setPlayerBoard(Board playerBoard) {
        this.playerBoardTUIC.setBoard(playerBoard);
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoardTUIC.setBoard(enemyBoard);
    }

    public void setStatusLine(String line) {
        statusTUIC.setLine(line);
    }

    public void addChatMessage(String message) {
        chatTUIC.addEntry(message);
    }

    public void addHistoryEntry(String entry) {
        historyTUIC.addEntry(entry);
    }

    public void setPlayerBoardController(BoardController playerBoardController) {
        this.playerBoardController = playerBoardController;
    }

    public BoardController getPlayerBoardController() {
        return playerBoardController;
    }

    public void setEnemyBoardController(BoardController enemyBoardController) {
        this.enemyBoardController = enemyBoardController;
    }

    public BoardController getEnemyBoardController() {
        return enemyBoardController;
    }

    public void draw() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
        System.out.println(String.join("\n", rootTUIC.draw()));
    }

}
