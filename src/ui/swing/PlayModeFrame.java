package ui.swing;

import javax.swing.*;
import java.awt.*;

public class PlayModeFrame extends JPanel {
    private static PlayModeFrame instance;
    
    private PlayModeFrame(LayoutManager layout) {
        super(layout);
    }

    public static PlayModeFrame getInstance() {
        if (instance == null) {
            LayoutManager layout = new GridBagLayout();
            // Here, Customize the PlayMode Frame at creation

            instance = new PlayModeFrame(layout);
        }
        return instance;
    }


}
