package ui.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HelpFrame extends JFrame {

    public HelpFrame() throws HeadlessException {
        super("Features & Gameplay");

        setBounds(100, 100, 1200, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel imagePanel = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel(new GridBagLayout());

        BufferedImage helpImage = null;
        try {
            helpImage = ImageIO.read(new File("src/main/java/utilities/HelpImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel imageLabel = new JLabel(new ImageIcon(helpImage));
        imagePanel.add(imageLabel);

        JButton closeButton = new JButton("Close Window");
        bottomPanel.add(closeButton);

        mainPanel.add(imagePanel);
        mainPanel.add(bottomPanel);

        add(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
