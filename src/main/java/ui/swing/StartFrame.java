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
    static JComboBox<String> jComboBox;
    static JTextField usernameBox;

    public StartFrame() throws HeadlessException {
        super("Need For Spear");

    }

    public static void main(String args[]) {
        StartFrame sf = new StartFrame();
        //LayoutManager layout = new GridBagLayout();
        //GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Image logoImage = new ImageIcon("src/main/java/utilities/startLogo.png").getImage();
                super.paintComponent(g);
                g.drawImage(logoImage, sf.getWidth() / 5 * 2, sf.getHeight() * 250 / 900, null);
            }

        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JButton newGameButton = new JButton("New Game");
        newGameButton.setActionCommand("new game");
        newGameButton.addActionListener(sf);
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setActionCommand("load game");
        loadGameButton.addActionListener(sf);
        JLabel label= new JLabel("Enter your username:  ");
        usernameBox = new JTextField(10);
        usernameBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usernameBox.setText("");
            }
        });


        JButton quitGameButton = new JButton("Quit");
        quitGameButton.setActionCommand("quit");
        quitGameButton.addActionListener(sf);
        panel.add(label,gbc);
        panel.add(usernameBox, gbc);
        String[] loadOptions = {"Local", "Database"};
        jComboBox = new JComboBox<>(loadOptions);
        panel.add(jComboBox);
        panel.add(newGameButton, gbc);
        panel.add(loadGameButton, gbc);
        panel.add(quitGameButton, gbc);

        sf.add(panel);
        sf.setBounds(100, 100, 1500, 900);
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
            gameScreen.initObstacles = true;
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

        if (e.getActionCommand().equals("quit")) {
            dispose();
        }


    }
}
