package controllers;

import tuic.PlayerMapTUIC;
import tuic.StringListTUIC;

public class DisplayController {

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

    public void addHistoryEntry(String entry) {
        history.addEntry(entry);
    }


}
