package ui.swing;

import domain.body.obstacle.Obstacle;
import domain.body.obstacle.SimpleObstacle;
import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class BuildModeFrame extends JFrame {
    private Controller controller;
    private ArrayList<Obstacle> obstacleList;
    private static final Color BACKGROUND_COLOR = new Color(140, 140, 140);

    private JTextField simpleObstacle = new JTextField(5);
    private JTextField firmObstacle = new JTextField(5);
    private JTextField giftObstacle = new JTextField(5);
    private JTextField explosiveObstacle = new JTextField(5);

    private JLabel simpleContainer = new JLabel(new ImageIcon("src/main/java/utilities/simpleObs.png"));
    private JLabel firmContainer = new JLabel(new ImageIcon("src/main/java/utilities/firmObs.png"));
    private JLabel giftContainer = new JLabel(new ImageIcon("src/main/java/utilities/giftObs.png"));
    private JLabel explosiveContainer = new JLabel(new ImageIcon("src/main/java/utilities/explosiveObs.png"));

    private JButton startGame = new JButton("Start Game");
    private JButton initObstacles = new JButton("Initialize obstacles");
    private static String savePlace;
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
        gameScreen.setBounds(0,0,this.getWidth(),this.getHeight()*600/800);
        // gridbag constraint parameters
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // panel settings
        JPanel numberPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Image numberPanelImage = new ImageIcon("src/main/java/utilities/numberPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(numberPanelImage, 0, 0, null);
            }
        };
        JPanel buttonPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Image buttonPanelImage = new ImageIcon("src/main/java/utilities/buttonPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(buttonPanelImage, 0, 0, null);
            }
        };
        numberPanel.setBounds(0,0,this.getWidth(),this.getHeight()*50/800);
        numberPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBounds(0,0,this.getWidth(),this.getHeight()*50/800);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(numberPanel);
        mainPanel.add(gameScreen);
        mainPanel.add(buttonPanel);
        initializeObstacleNumbers(gbc, numberPanel);
        initializeButton(gbc, buttonPanel);
        this.setLocationRelativeTo(null);
        setVisible(true);

        // listeners

        initObstacles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScreen.setInitObstacles(true);
                String[] obstaclesCount = new String[]{simpleObstacle.getText(), firmObstacle.getText(), explosiveObstacle.getText(), giftObstacle.getText()};
                controller.startNewGame(obstaclesCount);
                gameScreen.repaint(50L);
                initObstacles.setEnabled(false);
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
        obstacleNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.5;
        numberPanel.add(obstacleNumberLabel,gbc);
        simpleContainer.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.ipadx=10;
        gbc.gridx=1;
        numberPanel.add(simpleContainer, gbc);
        gbc.gridx=2;
        numberPanel.add(simpleObstacle,gbc);
        gbc.gridx=3;
        numberPanel.add(firmContainer);
        gbc.gridx=4;
        numberPanel.add(firmObstacle,gbc);
        gbc.gridx=5;
        numberPanel.add(giftContainer);
        gbc.gridx=6;
        numberPanel.add(giftObstacle,gbc);
        gbc.gridx=7;
        numberPanel.add(explosiveContainer);
        gbc.gridx=8;
        numberPanel.add(explosiveObstacle,gbc);
        gbc.gridx=9;
        gbc.anchor = GridBagConstraints.EAST;
        numberPanel.add(initObstacles, gbc);
    }

    private void initializeButton(GridBagConstraints gbc, JPanel buttonPanel) {
        gbc.gridy=1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(startGame,gbc);
    }

}
