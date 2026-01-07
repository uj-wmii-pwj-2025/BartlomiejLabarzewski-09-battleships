package locales;

public interface Locale {
    public String yourBoardLabel();
    public String enemyBoardLabel();
    public String statusLabel();
    public String historyLabel();
    public String controlsLabel();
    public String messagesLabel();
    public String enemyShot(String cell);
    public String yourShot(String cell);
    public String start();
    public String enemyMiss();
    public String enemyShotUnsunk();
    public String enemyShotSunk();
    public String enemyShotSunkLast();
    public String yourMiss();
    public String yourShotUnsunk();
    public String yourShotSunk();
    public String yourShotSunkLast();
    public String unreachable();
    public String win();
    public String lose();
    public String up();
    public String left();
    public String down();
    public String right();
    public String shoot();
    public String yourTurn();
    public String enemyTurn();
    public String you();
    public String enm();
    public String communicationError();
}
