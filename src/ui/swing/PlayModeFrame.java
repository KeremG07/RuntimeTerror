package ui.swing;

import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;

public class PlayModeFrame extends JFrame {
    Controller controller;

    private static PlayModeFrame instance;
    
    private PlayModeFrame() {
        super("Need For Spear by Runtime Terror");
        controller = Controller.getInstance();

        setBounds(0, 0, 1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameScreen gameScreen = controller.buildGame();

        setVisible(true);
    }

    public static PlayModeFrame getInstance() {
        if (instance == null) {
            instance = new PlayModeFrame();
        }
        return instance;
    }


}
