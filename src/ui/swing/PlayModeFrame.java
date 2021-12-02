package ui.swing;

import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayModeFrame extends JFrame {
    Controller controller;
    private static final Color BACKGROUND_COLOR = new Color(140, 140, 140);
    JButton pauseGame = new JButton("Pause Game");
    public int clockMs;

    private static PlayModeFrame instance;
    
    private PlayModeFrame() {
        super("Need For Spear by Runtime Terror");
        controller = Controller.getInstance();
        clockMs = Controller.ticksPerSecond*1;

        GameScreen gameScreen = GameScreen.getInstance();

        initializeFrame();

        // Panels Start Here
        JPanel mainPanel = initializeMainPanel();

        // GBC
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Score Panel
        JPanel scorePanel = new JPanel(new GridBagLayout());
        scorePanel.setBounds(0,0,1000,50);
        scorePanel.setBackground(BACKGROUND_COLOR);

        // Game Screen Panel
        gameScreen.setBounds(0,0,1000,600);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBounds(0,0,1000,50);
        buttonPanel.setBackground(BACKGROUND_COLOR);

        mainPanel.add(scorePanel);
        mainPanel.add(gameScreen);
        mainPanel.add(buttonPanel);

        initializeButton(gbc, buttonPanel);
        //initializeScores(gbc, scorePanel);
        // ...
        // Panels End Here

        gameScreen.setBorder(BorderFactory.createLineBorder(Color.red));

        setVisible(true);

        ActionListener tickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calls to controller are called here
                gameScreen.repaint();
                controller.updateEverything();
            }
        };

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
        buttonPanel.add(pauseGame,gbc);
    }

    private void initializeScores(GridBagConstraints gbc, JPanel scorePanel) {
        // TODO: fill with score informations
    }

    private void initializeFrame() {
        setBounds(0, 0, 1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
