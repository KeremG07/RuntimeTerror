package ui.swing;

import domain.needForSpear.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
public class StartFrame extends JFrame implements ActionListener {

    public StartFrame() throws HeadlessException {
        super("Need For Spear");
    }

    public static void main(String args[]) {
        StartFrame sf = new StartFrame();
        LayoutManager layout = new GridBagLayout();
        JPanel panel = new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                Image logoImage = new ImageIcon("src/main/java/utilities/startLogo.png").getImage();
                super.paintComponent(g);
                g.drawImage(logoImage, 600, 250, null);
            }

        };

        JButton newGameButton = new JButton("New Game");
        newGameButton.setActionCommand("new game");
        newGameButton.addActionListener(sf);
        //JButton loadGameButton = new JButton("Load Game");
        //loadGameButton.setActionCommand("load game");
        //loadGameButton.addActionListener(sf);
        JButton quitGameButton = new JButton("Quit");
        quitGameButton.setActionCommand("quit");
        quitGameButton.addActionListener(sf);


        panel.add(newGameButton);
        //panel.add(loadGameButton);
        panel.add(quitGameButton);


        sf.add(panel);
        sf.setBounds(100, 100, 1500, 900);
        sf.setResizable(false);
        sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sf.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("new game")) {
            BuildModeFrame.getInstance("");
            dispose();
        }

        /*if(e.getActionCommand().equals("load game")) {
            Controller controller = Controller.getInstance();
            controller.loadGame();

        }*/

        if(e.getActionCommand().equals("quit")) {
            dispose();
        }
    }
}
