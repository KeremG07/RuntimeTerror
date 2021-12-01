package ui.swing;

import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;
import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.HashMap;

public class GameScreen extends JPanel {

    private static GameScreen instance;
    Controller controller;

    public static GameScreen getInstance() {
        if (instance == null)
            instance = new GameScreen();
        return instance;
    }

    private HashMap<String, Image> images = new HashMap<String, Image>();

    private GameScreen() {
        this.controller = Controller.getInstance();
        setImages();
        setPreferredSize(new Dimension(controller.getBuildGame().width, controller.getBuildGame().height));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawNoblePhantasm(g);
        drawEnchantedSphere(g);
    }

    void drawNoblePhantasm(Graphics g) {
        NoblePhantasm noblePhantasm = controller.getPlayer().getNoblePhantasm();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        AffineTransform old = g2d.getTransform();
        at.translate(noblePhantasm.getCoordinates()[0], noblePhantasm.getCoordinates()[1]);
        g2d.drawImage(images.get("noblePhantasm"), at , null);
        g2d.setTransform(old);
    }

    void drawEnchantedSphere(Graphics g) {
        EnchantedSphere enchantedSphere = controller.getPlayer().getEnchantedSphere();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        AffineTransform old = g2d.getTransform();
        at.translate(enchantedSphere.getCoordinates()[0], enchantedSphere.getCoordinates()[1]);
        g2d.drawImage(images.get("enchantedSphere"), at , null);
        g2d.setTransform(old);
    }

    private void setImages() {
        NoblePhantasm noblePhantasm = controller.getPlayer().getNoblePhantasm();
        Image noblePhantasmImage = new ImageIcon("src/utilities/NoblePhantasm.png").getImage();
        Image enchantedSphereImage = new ImageIcon("src/utilities/EnchantedSphere.png").getImage();
        images.put("noblePhantasm", noblePhantasmImage);
        images.put("enchantedSphere", enchantedSphereImage);
    }
}
