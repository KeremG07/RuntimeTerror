package ui.swing;

import domain.needForSpear.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.*;

public class StartFrame extends JFrame implements ActionListener {
    private static JComboBox<String> jComboBox;
    private static JTextField usernameBox;

    public StartFrame() throws HeadlessException {
        super("Need For Spear");

    }

    public static void main(String args[]) {
        StartFrame sf = new StartFrame();
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Image logoImage = new ImageIcon("src/main/java/utilities/startLogo.png").getImage();
                Image bgImage = new ImageIcon("src/main/java/utilities/startFrame.png").getImage();
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, null);
                g.drawImage(logoImage, sf.getWidth() / 12 * 4, sf.getHeight() * 250 / 900, null);
            }

        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        JButton newGameButton = new JButton("New Game");
        newGameButton.setActionCommand("new game");
        newGameButton.addActionListener(sf);
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setActionCommand("load game");
        loadGameButton.addActionListener(sf);
        JLabel label= new JLabel("Enter your username:  ");
        label.setForeground(Color.WHITE);
        usernameBox = new JTextField(10);
        usernameBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usernameBox.setText("");
            }
        });

        JButton helpFrameButton = new JButton("Help");
        helpFrameButton.setActionCommand("help");
        helpFrameButton.addActionListener(sf);
        JButton quitGameButton = new JButton("Quit");
        quitGameButton.setActionCommand("quit");
        quitGameButton.addActionListener(sf);
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(label,gbc);
        gbc.gridx=1;
        panel.add(usernameBox, gbc);
        String[] loadOptions = {"Local", "Database"};
        jComboBox = new JComboBox<>(loadOptions);
        gbc.gridx=2;
        panel.add(jComboBox);
        gbc.gridx=0;
        gbc.gridy=1;
        panel.add(newGameButton, gbc);
        gbc.gridx=1;
        panel.add(loadGameButton, gbc);
        gbc.gridx=2;
        panel.add(helpFrameButton, gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        panel.add(quitGameButton, gbc);
        panel.setBackground(Color.DARK_GRAY);

        sf.add(panel);
        sf.setBounds(100, 100, 1000, 800);
        sf.setResizable(true);
        sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sf.setLocationRelativeTo(null);
        sf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("new game")) {
            Controller controller = Controller.getInstance();
            controller.getPlayer().setSave(jComboBox.getSelectedItem().toString());
            controller.getStatistics().setUsername(usernameBox.getText());
            BuildModeFrame.getInstance("");
            dispose();
        }
        if (e.getActionCommand().equals("load game")) {
            Controller controller = Controller.getInstance();
            GameScreen gameScreen = GameScreen.getInstance();
            gameScreen.setInitObstacles(true);
            controller.getPlayer().setSave(jComboBox.getSelectedItem().toString());
            controller.getStatistics().setUsername(usernameBox.getText());
            boolean usernameExists = controller.loadGame();
            if (usernameExists) {
                gameScreen.repaint(50L);
                PlayModeFrame.getInstance();
                controller.startPlaying();
                dispose();
            }
        }
        if (e.getActionCommand().equals("help")) {
            new HelpFrame();
        }
        if (e.getActionCommand().equals("quit")) {
            dispose();
        }
    }
}
