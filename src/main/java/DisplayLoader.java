import controllers.DisplayController;
import framestyle.FrameStyle;
import framestyle.ThinFrameStyle;
import tuic.*;

public class DisplayLoader {

    TUIC root = null;
    DisplayController controller = null;

    public TUIC load() {

        FrameStyle frameStyle = new ThinFrameStyle();

        PlayerMapTUIC pmt1 = new PlayerMapTUIC(10, 10);
        pmt1.setPadding(1);

        FrameTUIC ft1 = new FrameTUIC();
        ft1.setContents(pmt1);
        ft1.setFrameStyle(frameStyle);
        ft1.setTitle("Your board");

        PlayerMapTUIC pmt2 = new PlayerMapTUIC(10, 10);
        pmt2.setPadding(1);

        FrameTUIC ft2 = new FrameTUIC();
        ft2.setContents(pmt2);
        ft2.setFrameStyle(frameStyle);
        ft2.setTitle("Enemy board");

        HBoxTUIC boards = new HBoxTUIC();
        boards.setSpacing(2);
        boards.setComponents(new TUIC[] {ft1, ft2});

        return boards;

    }

}
