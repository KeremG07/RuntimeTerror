package ui.swing;

import domain.body.obstacle.Obstacle;
import domain.body.obstacle.SimpleObstacle;
import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class BuildModeFrame extends JFrame {
    private Controller controller;
    private ArrayList<Obstacle> obstacleList;
    private static final Color BACKGROUND_COLOR = new Color(140, 140, 140);

    private JTextField simpleObstacle = new JTextField(5);
    private JTextField firmObstacle = new JTextField(5);
    private JTextField giftObstacle = new JTextField(5);
    private JTextField explosiveObstacle = new JTextField(5);

    private JLabel simpleContainer1 = new JLabel(new ImageIcon("src/main/java/utilities/simpleObs.png"));
    private JLabel firmContainer1 = new JLabel(new ImageIcon("src/main/java/utilities/firmObs.png"));
    private JLabel giftContainer1 = new JLabel(new ImageIcon("src/main/java/utilities/giftObs.png"));
    private JLabel explosiveContainer1 = new JLabel(new ImageIcon("src/main/java/utilities/explosiveObs.png"));

    private JButton simpleButton = new JButton(new ImageIcon("src/main/java/utilities/simpleObs.png"));
    private JButton firmButton = new JButton(new ImageIcon("src/main/java/utilities/firmObs.png"));
    private JButton giftButton = new JButton(new ImageIcon("src/main/java/utilities/giftObs.png"));
    private JButton explosiveButton = new JButton(new ImageIcon("src/main/java/utilities/explosiveObs.png"));

    private String activeObstacle = "None";
    private boolean deleteActive = true;

    private JButton startGame = new JButton("Start Game");
    private JButton initObstacles = new JButton("Initialize obstacles");
    private JButton addObstacle = new JButton("Add");
    private JButton deleteObstacle = new JButton("Delete");
    private static BuildModeFrame instance;

    public static BuildModeFrame getInstance() {
        if (instance == null)
            instance = new BuildModeFrame();
        return instance;
    }

    private BuildModeFrame() {
        super("Building Window");
        controller = Controller.getInstance();
        obstacleList = controller.getStatistics().getObstacleList();
        setBounds(0, 0, 1000, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = initializeMainPanel();
        GameScreen gameScreen = GameScreen.getInstance();
        gameScreen.setBounds(0,200,this.getWidth(),600);
        // gridbag constraint parameters
        GridBagConstraints gbc = new GridBagConstraints();
        // panel settings
        JPanel numberPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(this.getWidth(), 100);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Image numberPanelImage = new ImageIcon("src/main/java/utilities/numberPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(numberPanelImage, 0, 0, null);
            }
        };
        JPanel addDeletePanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(this.getWidth(), 100);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Image numberPanelImage = new ImageIcon("src/main/java/utilities/numberPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(numberPanelImage, 0, 0, null);
            }
        };
        JPanel buttonPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(this.getWidth(), 100);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Image buttonPanelImage = new ImageIcon("src/main/java/utilities/buttonPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(buttonPanelImage, 0, 0, null);
            }
        };
        numberPanel.setBounds(0,0,this.getWidth(),100);
        numberPanel.setBackground(BACKGROUND_COLOR);
        addDeletePanel.setBounds(0,100,this.getWidth(),100);
        addDeletePanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBounds(0,800,this.getWidth(),100);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(numberPanel);
        mainPanel.add(addDeletePanel);
        mainPanel.add(gameScreen);
        mainPanel.add(buttonPanel);
        initializeObstacleNumbers(gbc, numberPanel);
        addDeleteObstacles(gbc, addDeletePanel);
        initializeButton(gbc, buttonPanel);
        this.setLocationRelativeTo(null);
        setVisible(true);

        simpleButton.setContentAreaFilled(false);
        simpleButton.setBorder(BorderFactory.createEmptyBorder());
        firmButton.setContentAreaFilled(false);
        firmButton.setBorder(BorderFactory.createEmptyBorder());
        giftButton.setContentAreaFilled(false);
        giftButton.setBorder(BorderFactory.createEmptyBorder());
        explosiveButton.setContentAreaFilled(false);
        explosiveButton.setBorder(BorderFactory.createEmptyBorder());

        // listeners
        addObstacle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!activeObstacle.equals("None") && Controller.getInstance().getStatistics().getObstacleList().size() < 110) {
                    Controller.getInstance().getBuildGame().putObstacleInCell(Controller.getInstance().getBuildGame().getLocationCells(), activeObstacle);
                    gameScreen.repaint();
                }
                activeObstacle = "None";
            }
        });

        deleteObstacle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteActive = true;
                MouseAdapter ma = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!deleteActive){
                            return;
                        }
                        Obstacle obstacleToDelete = null;
                        for (Map.Entry<Point2D.Double, Image> entry : gameScreen.getLocations().entrySet()) {
                            Image image = entry.getValue();
                            Point2D.Double point = entry.getKey();
                            Rectangle2D.Double bounds1 = new Rectangle2D.Double(
                                    point.x, point.y,
                                    image.getWidth(null), image.getHeight(null));
                            if (bounds1.contains(e.getPoint())) {
                                for(Obstacle o: Controller.getInstance().getStatistics().getObstacleList()) {
                                    if (o.getCoordinates()[0] == point.x && o.getCoordinates()[1] == point.y) {
                                        obstacleToDelete = o;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        try {
                            if (Controller.getInstance().getStatistics().getObstacleList().size() > 100) {
                                Controller.getInstance().getStatistics().getObstacleList().remove(obstacleToDelete);
                            }
                        } catch (Exception a) {

                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        gameScreen.repaint();
                        deleteActive = false;
                    }

                };
                gameScreen.addMouseListener(ma);
            }
        });

        simpleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeObstacle = "Simple";
            }
        });
        firmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeObstacle = "Firm";
            }
        });
        explosiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeObstacle = "Explosive";
            }
        });
        giftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeObstacle = "Gift";
            }
        });
        initObstacles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScreen.setInitObstacles(true);
                String[] obstaclesCount = new String[]{simpleObstacle.getText(), firmObstacle.getText(), explosiveObstacle.getText(), giftObstacle.getText()};
                controller.startNewGame(obstaclesCount);
                gameScreen.repaint(50L);
                initObstacles.setEnabled(false);
                startGame.setEnabled(true);
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
        simpleContainer1.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.ipadx=10;
        gbc.gridx=1;
        numberPanel.add(simpleContainer1, gbc);
        gbc.gridx=2;
        numberPanel.add(simpleObstacle,gbc);
        gbc.gridx=3;
        numberPanel.add(firmContainer1);
        gbc.gridx=4;
        numberPanel.add(firmObstacle,gbc);
        gbc.gridx=5;
        numberPanel.add(giftContainer1);
        gbc.gridx=6;
        numberPanel.add(giftObstacle,gbc);
        gbc.gridx=7;
        numberPanel.add(explosiveContainer1);
        gbc.gridx=8;
        numberPanel.add(explosiveObstacle,gbc);
        gbc.gridx=9;
        numberPanel.add(initObstacles, gbc);
    }

    private void addDeleteObstacles(GridBagConstraints gbc, JPanel addDeletePanel) {
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.5;
        //simpleContainer2.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.ipadx=10;
        addDeletePanel.add(simpleButton, gbc);
        gbc.gridx=1;
        addDeletePanel.add(firmButton,gbc);
        gbc.gridx=2;
        addDeletePanel.add(giftButton,gbc);
        gbc.gridx=3;
        addDeletePanel.add(explosiveButton,gbc);
        gbc.gridx=4;
        addDeletePanel.add(addObstacle,gbc);
        gbc.gridx=5;
        addDeletePanel.add(deleteObstacle,gbc);
        gbc.gridx=6;
    }

    private void initializeButton(GridBagConstraints gbc, JPanel buttonPanel) {
        gbc.gridy=1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(startGame,gbc);
        startGame.setEnabled(false);
    }
}


