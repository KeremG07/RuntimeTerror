package ui.swing;

import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;

public class EndFrame extends JFrame {
    private static EndFrame instance;

    private Image endFrameWonImage = new ImageIcon("src/main/java/utilities/endFrameWon.png").getImage();
    private Image endFrameLostImage = new ImageIcon("src/main/java/utilities/endFrameLost.png").getImage();

    private JPanel endFramePanel;

    private double score;
    private String scoreText;

    private boolean gameWon = false;


    public static EndFrame getInstance() {
        if (instance == null)
            instance = new EndFrame();
        return instance;
    }

    private EndFrame() {
        super("Game Over");
        initializeFrame();

        if(Controller.getInstance().getStatistics().getChances() != 0) gameWon = true;
        score = Math.round((Controller.getInstance().getStatistics().getScore() * 100) / 100.0);
        scoreText = Double.toString(score);

        if(gameWon) {
            endFramePanel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(endFrameWonImage, 0, 0, null);
                    g.drawString(scoreText, getWidth()*19/40, getHeight()*8/10);
                }
            };
        } else {
            endFramePanel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(endFrameLostImage, 0, 0, null);
                    g.drawString(scoreText, getWidth()*19/40, getHeight()*8/10);
                }
            };
        }

        add(endFramePanel);

        setVisible(true);
    }
    public void initializeFrame() {
        setBounds(0, 0, 600, 300);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
