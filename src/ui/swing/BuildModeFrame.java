package ui.swing;

import domain.body.obstacle.Obstacle;
import domain.body.obstacle.SimpleObstacle;
import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BuildModeFrame extends JFrame {
    Controller controller;
    private ArrayList<Obstacle> obstacleList;

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
        obstacleList = controller.getStatistics().getObstacleList();
        this.savePlace=savePlace;
        setBounds(0, 0, 1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = initializeMainPanel();
        GameScreen gameScreen = GameScreen.getInstance();
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
        /*System.out.println(numberPanel.getBounds());
        System.out.println(buttonPanel.getBounds());
        System.out.println(gameScreen.getBounds());*/
        initializeButton(gbc, buttonPanel);
        setVisible(true);

        initObstacles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScreen.initObstacles = true;
                String[] obstaclesCount = new String[]{simpleObstacle.getText(), firmObstacle.getText(), explosiveObstacle.getText(), giftObstacle.getText()};
                controller.startNewGame(obstaclesCount);
                gameScreen.repaint(50L);
            }
        });

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayModeFrame.getInstance();
                dispose();
            }
        });
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
