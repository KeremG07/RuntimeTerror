package ui.swing;

import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayModeFrame extends JFrame {
    private Controller controller = Controller.getInstance();
    private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;

    private JButton pauseGame = new JButton("Pause Game");
    private JButton resumeGame = new JButton("Resume Game");
    private JButton saveGame = new JButton("Save Game");
    private JButton quitGame = new JButton("Quit Game");

    private ImageIcon chance1 = new ImageIcon("src/main/java/utilities/chances1.png");
    private ImageIcon chance2 = new ImageIcon("src/main/java/utilities/chances2.png");
    private ImageIcon chance3 = new ImageIcon("src/main/java/utilities/chances3.png");

    private JLabel scoreLabel = new JLabel("", new ImageIcon("src/main/java/utilities/scoreText.png"), JLabel.CENTER);
    private JTextField scoreField = new JTextField(Double.toString(controller.getStatistics().getScore()));

    private ImageIcon abilityChanceGiving = new ImageIcon("src/main/java/utilities/abilityChanceGiving.png");
    private ImageIcon abilityChanceGivingDisabled = new ImageIcon("src/main/java/utilities/abilityChanceGivingDisabled.png");
    private ImageIcon abilityExpansion = new ImageIcon("src/main/java/utilities/abilityExpansion.png");
    private ImageIcon abilityExpansionDisabled = new ImageIcon("src/main/java/utilities/abilityExpansionDisabled.png");
    private ImageIcon abilityMagicalHex = new ImageIcon("src/main/java/utilities/abilityMagicalHex.png");
    private ImageIcon abilityMagicalHexDisabled = new ImageIcon("src/main/java/utilities/abilityMagicalHexDisabled.png");
    private ImageIcon abilityUnstoppableES = new ImageIcon("src/main/java/utilities/abilityUnstoppableES.png");
    private ImageIcon abilityUnstoppableESDisabled = new ImageIcon("src/main/java/utilities/abilityUnstoppableESDisabled.png");

    private ImageIcon ymirDefault = new ImageIcon("src/main/java/utilities/ymirDefault.png");
    private ImageIcon ymirDoubleAccel = new ImageIcon("src/main/java/utilities/ymirDoubleAccel.png");
    private ImageIcon ymirHollowPurple = new ImageIcon("src/main/java/utilities/ymirHollowPurple.png");
    private ImageIcon ymirInfinityVoid = new ImageIcon("src/main/java/utilities/ymirInfinityVoid.png");

    // Containers
    private JLabel chanceContainer = new JLabel("", chance3, JLabel.CENTER);

    private JLabel abilityChanceGivingContainer = new JLabel("", abilityChanceGivingDisabled, JLabel.CENTER);
    private JLabel abilityExpansionContainer = new JLabel("", abilityExpansionDisabled, JLabel.CENTER);
    private JLabel abilityMagicalHexContainer = new JLabel("", abilityMagicalHexDisabled, JLabel.CENTER);
    private JLabel abilityUnstoppableESContainer = new JLabel("", abilityUnstoppableESDisabled, JLabel.CENTER);

    private JLabel ymirContainer = new JLabel("", ymirDefault, JLabel.CENTER);


    private int clockMs;

    private static PlayModeFrame instance;
    
    private PlayModeFrame() {
        super("Need For Spear by Runtime Terror");
        clockMs = Controller.ticksPerSecond;
        GameScreen gameScreen = GameScreen.getInstance();
        controller.getStatistics().setStartTime(System.currentTimeMillis());
        initializeFrame();

        // Panels Start Here
        JPanel mainPanel = initializeMainPanel();

        // GBC
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Score Panel
        JPanel topPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Image playModeTopPanelImage = new ImageIcon("src/main/java/utilities/playModeTopPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(playModeTopPanelImage, 0, 0, null);
            }
        };
        topPanel.setBounds(0,0,this.getWidth(),this.getHeight()*50/800);
        topPanel.setBackground(BACKGROUND_COLOR);

        // Game Screen Panel
        gameScreen.setBounds(0,0,this.getWidth(),this.getHeight()*600/800);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Image playModeButtonPanelImage = new ImageIcon("src/main/java/utilities/playModeButtonPanel.png").getImage();
                super.paintComponent(g);
                g.drawImage(playModeButtonPanelImage, 0, 0, null);
            }
        };
        buttonPanel.setBounds(0,0,this.getWidth(),this.getHeight()*50/800);
        buttonPanel.setBackground(BACKGROUND_COLOR);

        mainPanel.add(topPanel);
        mainPanel.add(gameScreen);
        mainPanel.add(buttonPanel);

        initializeButton(gbc, buttonPanel);
        initializeTopPanel(gbc, topPanel);
        // Panels End Here
        
        this.setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);

        ActionListener tickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calls to controller are called here
                gameScreen.repaint();
                updateTopPanel();

                if(controller.getStatistics().getChances() == 0
                        || controller.getStatistics().getObstacleList().size() == 0){
                    EndFrame.getInstance();
                    dispose();
                }

                if(!controller.isPaused()) {
                    controller.updateEverything();
                }
            }
        };

        pauseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setPaused(true);

                pauseGame.setEnabled(false);
                resumeGame.setEnabled(true);
                saveGame.setEnabled(true);
                quitGame.setEnabled(true);
            }
        });

        resumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setPaused(false);

                pauseGame.setEnabled(true);
                resumeGame.setEnabled(false);
                saveGame.setEnabled(false);
                quitGame.setEnabled(false);
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveGame();
                saveGame.setEnabled(false);
            }
        });

        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.endGame();
                dispose();
            }
        });

        Timer timer = new Timer(clockMs, tickListener);
        timer.start();
        addKeyListener(new KeyboardController());
    }

    public static PlayModeFrame getInstance() {
        if (instance == null) {
            instance = new PlayModeFrame();
        }
        return instance;
    }

    private JPanel initializeMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        return mainPanel;
    }

    private void initializeButton(GridBagConstraints gbc, JPanel buttonPanel) {
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(pauseGame, gbc);
        gbc.gridx=1;
        buttonPanel.add(resumeGame, gbc);
        resumeGame.setEnabled(false);
        gbc.gridx=2;
        buttonPanel.add(saveGame, gbc);
        saveGame.setEnabled(false);
        gbc.gridx=3;
        buttonPanel.add(quitGame, gbc);
        quitGame.setEnabled(false);
    }

    private void initializeTopPanel(GridBagConstraints gbc, JPanel topPanel) {
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(chanceContainer, gbc);

        gbc.gridx=1;
        topPanel.add(scoreLabel, gbc);

        gbc.gridx=2;
        gbc.ipadx=100;
        topPanel.add(scoreField, gbc);

        gbc.gridx=3;
        gbc.ipadx=0;
        topPanel.add(abilityChanceGivingContainer, gbc);

        gbc.gridx=4;
        topPanel.add(abilityExpansionContainer, gbc);

        gbc.gridx=5;
        topPanel.add(abilityMagicalHexContainer, gbc);

        gbc.gridx=6;
        topPanel.add(abilityUnstoppableESContainer, gbc);

        gbc.gridx=7;
        topPanel.add(ymirContainer, gbc);
    }

    private void initializeFrame() {
        setBounds(0, 0, 1000, 800);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateTopPanel() {
        // Update Chances Image
        switch (controller.getStatistics().getChances()) {
            case 1:
                chanceContainer.setIcon(chance1);
                break;
            case 2:
                chanceContainer.setIcon(chance2);
                break;
            case 3:
                chanceContainer.setIcon(chance3);
                break;
        }

        // Update Score
        scoreField.setText(String.format("%.2f", controller.getStatistics().getScore()));

        // Update Ability Images
        // ...

        // Update Ymir Image
        if (controller.getYmir().isActive()) {
            switch (controller.getYmir().getCurrentAbility().getName()) {
                case "Double Accel":
                    ymirContainer.setIcon(ymirDoubleAccel);
                    break;
                case "Hollow Purple":
                    ymirContainer.setIcon(ymirHollowPurple);
                    break;
                case "Infinite Void":
                    ymirContainer.setIcon(ymirInfinityVoid);
                    break;
                default:
                    ymirContainer.setIcon(ymirDefault);
                    break;
            }
        } else {
            ymirContainer.setIcon(ymirDefault);
        }
    }
}
