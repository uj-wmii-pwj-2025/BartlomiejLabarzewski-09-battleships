package controllers;

import tuic.LabelTUIC;
import tuic.PlayerMapTUIC;
import tuic.StringListTUIC;
import tuic.TUIC;

public class DisplayController {

    private TUIC root;
    private PlayerMapTUIC playerMap;
    private PlayerMapTUIC enemyMap;
    private StringListTUIC history;
    private StringListTUIC chat;
    private LabelTUIC status;

    public void setPlayerMap(PlayerMapTUIC playerMap) {
        this.playerMap = playerMap;
    }

    public void setEnemyMap(PlayerMapTUIC enemyMap) {
        this.enemyMap = enemyMap;
    }

    public void setHistory(StringListTUIC history) {
        this.history = history;
    }

    public void setChat(StringListTUIC chat) {
        this.chat = chat;
    }

    public void setStatus(LabelTUIC status) {
        this.status = status;
    }

    public void setRoot(TUIC root) {
        this.root = root;
    }

    public void setStatusLine(String line) {
        this.status.setLine(line);
    }

    public void addChatMessage(String message) {
        chat.addEntry(message);
    }

    public void addHistoryEntry(String entry) {
        history.addEntry(entry);
    }

    public void draw() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
        System.out.println(String.join("\n", root.draw()));
    }

}
