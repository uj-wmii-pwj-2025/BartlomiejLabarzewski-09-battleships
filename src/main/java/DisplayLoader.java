import controllers.BoardController;
import controllers.DisplayController;
import framestyle.FrameStyle;
import framestyle.ThinFrameStyle;
import locales.Locale;
import map.EnemyBoardCellStyle;
import map.PlayerBoardCellStyle;
import tuic.*;

public class DisplayLoader {

    TUIC root = null;
    DisplayController controller = null;
    boolean DEBUG = true;
    Locale locale;

    public DisplayLoader(Locale locale) {
        this.locale = locale;
    }

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
        yourBoardFrame.setTitle(locale.yourBoardLabel());

        BoardTUIC enemyBoard = new BoardTUIC(new EnemyBoardCellStyle());

        PadPaneTUIC enemyBoardPadding = new PadPaneTUIC();
        enemyBoardPadding.setComponent(enemyBoard);
        enemyBoardPadding.setPadding(1);

        FrameTUIC enemyBoardFrame = new FrameTUIC();
        enemyBoardFrame.setContents(enemyBoardPadding);
        enemyBoardFrame.setFrameStyle(frameStyle);
        enemyBoardFrame.setTitle(locale.enemyBoardLabel());

        HBoxTUIC boards = new HBoxTUIC();
        boards.setSpacing(1);
        boards.setComponents(new TUIC[] {yourBoardFrame, enemyBoardFrame});

        LabelTUIC status = new LabelTUIC(43);
        status.setLine("");

        CenteringPaneTUIC statusPane = new CenteringPaneTUIC(55, 5);
        statusPane.setComponent(status);

        FrameTUIC statusFrame = new FrameTUIC();
        statusFrame.setContents(statusPane);
        statusFrame.setTitle(locale.statusLabel());
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
        historyFrame.setTitle(locale.historyLabel());

        // SpacerTUIC filler = new SpacerTUIC(33, 29);

        VBoxTUIC rightPanel = new VBoxTUIC();
        rightPanel.setSpacing(1);

        StringListTUIC controls;
        StringListTUIC messages = null;

        if (DEBUG) {

            int CONTROLS_HEIGHT = 5;

            controls = new StringListTUIC(20, CONTROLS_HEIGHT);
            controls.addEntry("W      -  " + locale.up());
            controls.addEntry("A      -  " + locale.left());
            controls.addEntry("S      -  " + locale.down());
            controls.addEntry("D      -  " + locale.right());
            controls.addEntry("ENTER  -  " + locale.shoot());

            PadPaneTUIC controlsPadding = new PadPaneTUIC();
            controlsPadding.setComponent(controls);
            controlsPadding.setPadding(1);

            FrameTUIC controlsFrame = new FrameTUIC();
            controlsFrame.setContents(controlsPadding);
            controlsFrame.setFrameStyle(frameStyle);
            controlsFrame.setTitle(locale.controlsLabel());

            messages = new StringListTUIC(20, 20 - CONTROLS_HEIGHT);

            PadPaneTUIC messagesPadding = new PadPaneTUIC();
            messagesPadding.setComponent(messages);
            messagesPadding.setPadding(1);

            FrameTUIC messagesFrame = new FrameTUIC();
            messagesFrame.setContents(messagesPadding);
            messagesFrame.setFrameStyle(frameStyle);
            messagesFrame.setTitle(locale.messagesLabel());

            rightPanel.setComponents(new TUIC[]{controlsFrame, messagesFrame});

        }

        else {

            controls = new StringListTUIC(20, 25);
            controls.addEntry("W      -  " + locale.up());
            controls.addEntry("A      -  " + locale.left());
            controls.addEntry("S      -  " + locale.down());
            controls.addEntry("D      -  " + locale.right());
            controls.addEntry("ENTER  -  " + locale.shoot());

            PadPaneTUIC controlsPadding = new PadPaneTUIC();
            controlsPadding.setComponent(controls);
            controlsPadding.setPadding(1);

            FrameTUIC controlsFrame = new FrameTUIC();
            controlsFrame.setContents(controlsPadding);
            controlsFrame.setFrameStyle(frameStyle);
            controlsFrame.setTitle(locale.controlsLabel());

            rightPanel.setComponents(new TUIC[]{controlsFrame});

        }

        HBoxTUIC root = new HBoxTUIC();
        root.setSpacing(1);
        root.setComponents(new TUIC[]{historyFrame, screenMiddle, rightPanel});

        controller = new DisplayController();

        controller.setPlayerBoardTUIC(yourBoard);
        controller.setEnemyBoardTUIC(enemyBoard);
        controller.setControlsTUIC(controls);
        if (DEBUG) {
            controller.setMessagesTUIC(messages);
        }
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
