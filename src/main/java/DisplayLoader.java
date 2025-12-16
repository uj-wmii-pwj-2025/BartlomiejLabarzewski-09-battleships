import controllers.DisplayController;
import framestyle.FrameStyle;
import framestyle.ThinFrameStyle;
import tuic.*;

public class DisplayLoader {

    TUIC root = null;
    DisplayController controller = null;

    public TUIC load() {

        // 125 x 29

        FrameStyle frameStyle = new ThinFrameStyle();

        PlayerMapTUIC yourBoard = new PlayerMapTUIC(10, 10);

        PadPaneTUIC yourBoardPadding = new PadPaneTUIC();
        yourBoardPadding.setComponent(yourBoard);
        yourBoardPadding.setPadding(1);

        FrameTUIC yourBoardFrame = new FrameTUIC();
        yourBoardFrame.setContents(yourBoardPadding);
        yourBoardFrame.setFrameStyle(frameStyle);
        yourBoardFrame.setTitle("Your board");

        PlayerMapTUIC enemyBoard = new PlayerMapTUIC(10, 10);

        PadPaneTUIC enemyBoardPadding = new PadPaneTUIC();
        enemyBoardPadding.setComponent(enemyBoard);
        enemyBoardPadding.setPadding(1);

        FrameTUIC enemyBoardFrame = new FrameTUIC();
        enemyBoardFrame.setContents(enemyBoardPadding);
        enemyBoardFrame.setFrameStyle(frameStyle);
        enemyBoardFrame.setTitle("Enemy board");

        VBoxTUIC boards = new VBoxTUIC();
        boards.setSpacing(1);
        boards.setComponents(new TUIC[] {yourBoardFrame, enemyBoardFrame});

        SpacerTUIC filler = new SpacerTUIC(64, 29);

        StringListTUIC actions = new StringListTUIC(20, 25);
        actions.addEntry("Szczur 1");
        actions.addEntry("Szczur 2");
        actions.addEntry("Szczur 3");
        actions.addEntry("Szczur 4");

        PadPaneTUIC actionsPadding = new PadPaneTUIC();
        actionsPadding.setComponent(actions);
        actionsPadding.setPadding(1);

        FrameTUIC actionsFrame = new FrameTUIC();
        actionsFrame.setContents(actionsPadding);
        actionsFrame.setFrameStyle(frameStyle);
        actionsFrame.setTitle("History");

        HBoxTUIC root = new HBoxTUIC();
        root.setSpacing(1);
        root.setComponents(new TUIC[]{filler, boards, actionsFrame});

        controller = new DisplayController();

        controller.setPlayerMap(yourBoard);
        controller.setEnemyMap(enemyBoard);
        controller.setHistory(actions);

        return root;

    }

    public TUIC getRoot() {
        return root;
    }

    public DisplayController getController() {
        return controller;
    }


}
