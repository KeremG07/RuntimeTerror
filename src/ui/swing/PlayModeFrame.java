package ui.swing;

import javax.swing.*;

public class PlayModeFrame extends JPanel {
    private static PlayModeFrame instance;
    private PlayModeFrame() {}

    public static PlayModeFrame getInstance() {
        if (instance == null)
            instance = new PlayModeFrame();
        return instance;
    }


}
