import controllers.BoardController;
import controllers.DisplayController;
import framestyle.FrameStyle;
import framestyle.ThinFrameStyle;
import map.EnemyBoardCellStyle;
import map.PlayerBoardCellStyle;
import tuic.*;

public class DisplayLoader {

    TUIC root = null;
    DisplayController controller = null;

    public TUIC load() {

        // 125 x 29

        FrameStyle frameStyle = new ThinFrameStyle();

        SpacerTUIC logoPlaceholder = new SpacerTUIC(55, 4);

        FrameTUIC logoFrame = new FrameTUIC();
        logoFrame.setContents(logoPlaceholder);
        logoFrame.setFrameStyle(frameStyle);

        BoardTUIC yourBoard = new BoardTUIC(new PlayerBoardCellStyle());

        PadPaneTUIC yourBoardPadding = new PadPaneTUIC();
        yourBoardPadding.setComponent(yourBoard);
        yourBoardPadding.setPadding(1);

        FrameTUIC yourBoardFrame = new FrameTUIC();
        yourBoardFrame.setContents(yourBoardPadding);
        yourBoardFrame.setFrameStyle(frameStyle);
        yourBoardFrame.setTitle("Your board");

        BoardTUIC enemyBoard = new BoardTUIC(new EnemyBoardCellStyle());

        PadPaneTUIC enemyBoardPadding = new PadPaneTUIC();
        enemyBoardPadding.setComponent(enemyBoard);
        enemyBoardPadding.setPadding(1);

        FrameTUIC enemyBoardFrame = new FrameTUIC();
        enemyBoardFrame.setContents(enemyBoardPadding);
        enemyBoardFrame.setFrameStyle(frameStyle);
        enemyBoardFrame.setTitle("Enemy board");

        HBoxTUIC boards = new HBoxTUIC();
        boards.setSpacing(1);
        boards.setComponents(new TUIC[] {yourBoardFrame, enemyBoardFrame});

        LabelTUIC status = new LabelTUIC(43);
        status.setLine("Foo");

        CenteringPaneTUIC statusPane = new CenteringPaneTUIC(55, 5);
        statusPane.setComponent(status);

        FrameTUIC statusFrame = new FrameTUIC();
        statusFrame.setContents(statusPane);
        statusFrame.setTitle("Status");
        statusFrame.setFrameStyle(frameStyle);

        VBoxTUIC screenMiddle = new VBoxTUIC();
        screenMiddle.setComponents(new TUIC[]{logoFrame, boards, statusFrame});
        screenMiddle.setSpacing(1);

        StringListTUIC history = new StringListTUIC(29, 25);

        PadPaneTUIC historyPadding = new PadPaneTUIC();
        historyPadding.setComponent(history);
        historyPadding.setPadding(1);

        FrameTUIC historyFrame = new FrameTUIC();
        historyFrame.setContents(historyPadding);
        historyFrame.setFrameStyle(frameStyle);
        historyFrame.setTitle("History");

        // SpacerTUIC filler = new SpacerTUIC(33, 29);

        StringListTUIC controls = new StringListTUIC(20, 25);
        controls.addEntry("W      -  UP");
        controls.addEntry("A      -  LEFT");
        controls.addEntry("S      -  DOWN");
        controls.addEntry("D      -  RIGHT");
        controls.addEntry("ENTER  -  SHOOT");

        PadPaneTUIC controlsPadding = new PadPaneTUIC();
        controlsPadding.setComponent(controls);
        controlsPadding.setPadding(1);

        FrameTUIC controlsFrame = new FrameTUIC();
        controlsFrame.setContents(controlsPadding);
        controlsFrame.setFrameStyle(frameStyle);
        controlsFrame.setTitle("Controls");

        HBoxTUIC root = new HBoxTUIC();
        root.setSpacing(1);
        root.setComponents(new TUIC[]{historyFrame, screenMiddle, controlsFrame});

        controller = new DisplayController();

        controller.setPlayerBoardTUIC(yourBoard);
        controller.setEnemyBoardTUIC(enemyBoard);
        controller.setControlsTUIC(controls);
        controller.setRootTUIC(root);
        controller.setHistoryTUIC(history);
        controller.setStatusTUIC(status);

        BoardController playerBoardController = new BoardController();
        playerBoardController.setBoardTUIC(yourBoard);
        controller.setPlayerBoardController(playerBoardController);

        BoardController enemyBoardController = new BoardController();
        enemyBoardController.setBoardTUIC(enemyBoard);
        controller.setEnemyBoardController(enemyBoardController);

        return root;

    }

    public TUIC getRoot() {
        return root;
    }

    public DisplayController getController() {
        return controller;
    }


}
