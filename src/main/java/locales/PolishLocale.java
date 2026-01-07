package locales;

public class PolishLocale implements Locale {
    @Override
    public String yourBoardLabel() {
        return "Twoja plansza";
    }

    @Override
    public String enemyBoardLabel() {
        return "Plansza wroga";
    }

    @Override
    public String statusLabel() {
        return "Status";
    }

    @Override
    public String historyLabel() {
        return "Historia";
    }

    @Override
    public String controlsLabel() {
        return "Sterowanie";
    }

    @Override
    public String messagesLabel() {
        return "Wiadomości";
    }

    @Override
    public String enemyShot(String cell) {
        return "Wróg zaatakował pole " + cell + "!";
    }

    @Override
    public String yourShot(String cell) {
        return "Zaatakowałeś pole " + cell + "!";
    }

    @Override
    public String start() {
        return "Rozpoczęto grę!";
    }

    @Override
    public String enemyMiss() {
        return "Wróg spudłował!";
    }

    @Override
    public String enemyShotUnsunk() {
        return "Wróg trafił twój okręt!";
    }

    @Override
    public String enemyShotSunk() {
        return "Wróg zatopił twój okręt!";
    }

    @Override
    public String enemyShotSunkLast() {
        return "Wróg zatopił twój ostatni okręt!";
    }

    @Override
    public String yourMiss() {
        return "Spudłowałeś!";
    }

    @Override
    public String yourShotUnsunk() {
        return "Trafiłeś okręt wroga!";
    }

    @Override
    public String yourShotSunk() {
        return "Zatopiłeś okręt wroga!";
    }

    @Override
    public String yourShotSunkLast() {
        return "Zatopiłeś ostatni okręt wroga!";
    }

    @Override
    public String unreachable() {
        return "Niemożliwe!";
    }

    @Override
    public String win() {
        return "Wygrana";
    }

    @Override
    public String lose() {
        return "Przegrana";
    }

    @Override
    public String up() {
        return "GÓRA";
    }

    @Override
    public String left() {
        return "LEWO";
    }

    @Override
    public String down() {
        return "DÓŁ";
    }

    @Override
    public String right() {
        return "PRAWO";
    }

    @Override
    public String shoot() {
        return "STRZAŁ";
    }

    @Override
    public String yourTurn() {
        return "Twoja tura!";
    }

    @Override
    public String enemyTurn() {
        return "Tura wroga!";
    }

    @Override
    public String you() {
        return "TY ";
    }

    @Override
    public String enm() {
        return "WRG";
    }

    @Override
    public String communicationError() {
        return "Błąd komunikacji";
    }
}
