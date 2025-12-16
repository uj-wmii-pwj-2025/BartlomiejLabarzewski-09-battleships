import controllers.DisplayController;
import framestyle.FrameStyle;
import framestyle.ThinFrameStyle;
import tuic.*;

import java.awt.*;

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

        HBoxTUIC boards = new HBoxTUIC();
        boards.setSpacing(1);
        boards.setComponents(new TUIC[] {yourBoardFrame, enemyBoardFrame});

        SpacerTUIC statusPlaceholder = new SpacerTUIC(55, 5);

        FrameTUIC statusFrame = new FrameTUIC();
        statusFrame.setContents(statusPlaceholder);
        statusFrame.setTitle("Status");
        statusFrame.setFrameStyle(frameStyle);

        VBoxTUIC screenMiddle = new VBoxTUIC();
        screenMiddle.setComponents(new TUIC[]{logoFrame, boards, statusFrame});
        screenMiddle.setSpacing(1);

        StringListTUIC chat = new StringListTUIC(29, 25);

        PadPaneTUIC chatPadding = new PadPaneTUIC();
        chatPadding.setComponent(chat);
        chatPadding.setPadding(1);

        FrameTUIC chatFrame = new FrameTUIC();
        chatFrame.setContents(chatPadding);
        chatFrame.setFrameStyle(frameStyle);
        chatFrame.setTitle("Chat");

        // SpacerTUIC filler = new SpacerTUIC(33, 29);

        StringListTUIC actions = new StringListTUIC(20, 25);

        PadPaneTUIC actionsPadding = new PadPaneTUIC();
        actionsPadding.setComponent(actions);
        actionsPadding.setPadding(1);

        FrameTUIC actionsFrame = new FrameTUIC();
        actionsFrame.setContents(actionsPadding);
        actionsFrame.setFrameStyle(frameStyle);
        actionsFrame.setTitle("History");

        HBoxTUIC root = new HBoxTUIC();
        root.setSpacing(1);
        root.setComponents(new TUIC[]{chatFrame, screenMiddle, actionsFrame});

        controller = new DisplayController();

        controller.setPlayerMap(yourBoard);
        controller.setEnemyMap(enemyBoard);
        controller.setHistory(actions);
        controller.setRoot(root);

        return root;

    }

    public TUIC getRoot() {
        return root;
    }

    public DisplayController getController() {
        return controller;
    }


}
