package ui.swing;

import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;

public class BuildModeFrame extends JFrame {
    Controller controller;
    private static final Color BACKGROUND_COLOR = new Color(140, 140, 140);
    JTextField simpleObstacle = new JTextField(5);
    JTextField firmObstacle = new JTextField(5);
    JTextField explosiveObstacle = new JTextField(5);
    JTextField giftObstacle = new JTextField(5);
    JButton startGame = new JButton("Start Game");
    JButton initObstacles = new JButton("Initialize obstacles");
    static String savePlace;

    private static BuildModeFrame instance;

    public static BuildModeFrame getInstance(String savePlace) {
        if (instance == null)
            instance = new BuildModeFrame(savePlace);
        return instance;
    }

    private BuildModeFrame(String savePlace) {
        super("Building Window");
        controller = Controller.getInstance();
        this.savePlace=savePlace;
        setBounds(0, 0, 1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = initializeMainPanel();
        JPanel gameScreen = controller.buildGame();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel numberPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        numberPanel.setBounds(0,0,1000,50);
        numberPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBounds(0,0,1000,50);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        gameScreen.setBounds(0,0,1000,600);
        mainPanel.add(numberPanel);
        mainPanel.add(gameScreen);
        mainPanel.add(buttonPanel);
        initializeObstacleNumbers(gbc, numberPanel);
        gameScreen.setBorder(BorderFactory.createLineBorder(Color.red));
        System.out.println(numberPanel.getBounds());
        System.out.println(buttonPanel.getBounds());
        System.out.println(gameScreen.getBounds());
        initializeButton(gbc, buttonPanel);
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
        gbc.gridy=0;
        gbc.weightx=1;
        numberPanel.add(obstacleNumberLabel,gbc);
        gbc.gridx=1;
        numberPanel.add(simpleObstacle,gbc);
        gbc.gridx=2;
        numberPanel.add(firmObstacle,gbc);
        gbc.gridx=3;
        numberPanel.add(explosiveObstacle,gbc);
        gbc.gridx=4;
        numberPanel.add(giftObstacle,gbc);
        gbc.gridx=5;
        gbc.anchor = GridBagConstraints.EAST;
        numberPanel.add(initObstacles, gbc);
    }

    private void initializeButton(GridBagConstraints gbc, JPanel buttonPanel) {
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(startGame,gbc);
    }
}
