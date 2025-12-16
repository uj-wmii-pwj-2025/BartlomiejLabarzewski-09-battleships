package controllers;

import tuic.PlayerMapTUIC;
import tuic.StringListTUIC;
import tuic.TUIC;

public class DisplayController {

    private TUIC root;
    private PlayerMapTUIC playerMap;
    private PlayerMapTUIC enemyMap;
    private StringListTUIC history;

    public void setPlayerMap(PlayerMapTUIC playerMap) {
        this.playerMap = playerMap;
    }

    public void setEnemyMap(PlayerMapTUIC enemyMap) {
        this.enemyMap = enemyMap;
    }

    public void setHistory(StringListTUIC history) {
        this.history = history;
    }

    public void setRoot(TUIC root) {
        this.root = root;
    }

    public void addHistoryEntry(String entry) {
        history.addEntry(entry);
    }

    public void draw() {
        System.out.println(String.join("\n", root.draw()));
    }

}
