package locales;

public class EnglishLocale implements Locale {
    @Override
    public String yourBoardLabel() {
        return "Your board";
    }

    @Override
    public String enemyBoardLabel() {
        return "Enemy board";
    }

    @Override
    public String statusLabel() {
        return "Status";
    }

    @Override
    public String historyLabel() {
        return "History";
    }

    @Override
    public String controlsLabel() {
        return "Controls";
    }

    @Override
    public String messagesLabel() {
        return "Messages";
    }

    @Override
    public String enemyShot(String cell) {
        return "Enemy has shot cell " + cell + "!";
    }

    @Override
    public String yourShot(String cell) {
        return "You have shot cell " + cell + "!";
    }

    @Override
    public String start() {
        return "The game has started!";
    }

    @Override
    public String enemyMiss() {
        return "The enemy has missed!";
    }

    @Override
    public String enemyShotUnsunk() {
        return "The enemy has shot your ship!";
    }

    @Override
    public String enemyShotSunk() {
        return "The enemy has sunk your ship!";
    }

    @Override
    public String enemyShotSunkLast() {
        return "The enemy has sunk your last ship!";
    }

    @Override
    public String yourMiss() {
        return "You have missed!";
    }

    @Override
    public String yourShotUnsunk() {
        return "You have shot an enemy ship!";
    }

    @Override
    public String yourShotSunk() {
        return "You have sunk an enemy ship!";
    }

    @Override
    public String yourShotSunkLast() {
        return "You have sunk the last enemy ship!";
    }

    @Override
    public String unreachable() {
        return "Unreachable!";
    }

    @Override
    public String win() {
        return "Victory";
    }

    @Override
    public String lose() {
        return "Defeat";
    }

    @Override
    public String up() {
        return "UP";
    }

    @Override
    public String left() {
        return "LEFT";
    }

    @Override
    public String down() {
        return "DOWN";
    }

    @Override
    public String right() {
        return "RIGHT";
    }

    @Override
    public String shoot() {
        return "SHOOT";
    }

    @Override
    public String yourTurn() {
        return "Your turn!";
    }

    @Override
    public String enemyTurn() {
        return "Enemy turn";
    }

    @Override
    public String you() {
        return "YOU";
    }

    @Override
    public String enm() {
        return "ENM";
    }

    @Override
    public String communicationError() {
        return "";
    }
}
