package ui.swing;

import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;

public class EndFrame extends JFrame {
    private static EndFrame instance;
    private JLabel reason;
    private JLabel endMessage;
    private JLabel score;
    public static EndFrame getInstance() {
        if (instance == null)
            instance = new EndFrame();
        return instance;
    }

    private EndFrame() {
        super("Game Over");
        initializeFrame();
        if(Controller.getInstance().getStatistics().getChances() == 0){
            reason = new JLabel("You do not have any chances left.");
            endMessage = new JLabel("You have lost the game :(");

        }
        //If all obstacles are destroyed:
        else {
            reason = new JLabel("You destroyed all the obstacles!.");
            endMessage = new JLabel("You have won the game!");
        }
        reason.setFont(new Font("Serif", Font.PLAIN, 21));
        endMessage.setFont(new Font("Serif", Font.PLAIN, 21));
        score = new JLabel("Your score was: " + Controller.getInstance().getStatistics().getScore());
        score.setFont(new Font("Serif", Font.PLAIN, 21));
        addComponentsToPane(this.getContentPane());
        setVisible(true);
    }
    public void initializeFrame() {
        setBounds(0, 0, 600, 300);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        reason.setAlignmentX(Component.CENTER_ALIGNMENT);
        endMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(reason);
        pane.add(endMessage);
        pane.add(score);
    }
}
