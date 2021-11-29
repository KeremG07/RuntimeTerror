package ui.swing;

import javax.swing.*;
import java.awt.*;

public class BuildModeFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(140, 140, 140);
    JTextField simpleObstacle = new JTextField(5);
    JTextField firmObstacle = new JTextField(5);
    JTextField explosiveObstacle = new JTextField(5);
    JTextField giftObstacle = new JTextField(5);
    static String savePlace;

    private static BuildModeFrame instance;

    public static BuildModeFrame getInstance(String savePlace) {
        if (instance == null)
            instance = new BuildModeFrame(savePlace);
        return instance;
    }

    private BuildModeFrame(String savePlace) {
        super("Building Window");
        this.savePlace=savePlace;
        setBounds(300, 200, 1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = initializeMainPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel numberPanel = new JPanel(new GridBagLayout());
        numberPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(numberPanel);
        initializeObstacleNumbers(gbc, numberPanel);


        setVisible(true);
    }

    private JPanel initializeMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        return mainPanel;
    }
    private void initializeObstacleNumbers(GridBagConstraints gbc, JPanel numberPanel) {
        JLabel obstacleNumberLabel = new JLabel("Obstacle Counts:");
        gbc.gridx=0;
        gbc.gridy=1;
        numberPanel.add(obstacleNumberLabel,gbc);
        gbc.gridx=1;
        numberPanel.add(simpleObstacle,gbc);
        gbc.gridx=2;
        numberPanel.add(firmObstacle,gbc);
        gbc.gridx=3;
        numberPanel.add(explosiveObstacle,gbc);
        gbc.gridx=4;
        numberPanel.add(giftObstacle,gbc);
    }

}
