package ui.swing;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;
import domain.body.obstacle.Obstacle;
import domain.needForSpear.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends JPanel {

    private static GameScreen instance;
    private static int count = 0;
    public static boolean initObstacles = false;

    public static GameScreen getInstance() {
        if (instance == null)
            count++;
            //System.out.println(count);
            instance = new GameScreen();
        return instance;
    }

    Controller controller;

    private Map<Image, Point> locations = new HashMap<Image, Point>();
    private HashMap<String, Image> images = new HashMap<String, Image>();
    private ArrayList<Obstacle> obstacleList;

    private GameScreen() {
        this.controller = Controller.getInstance();
        obstacleList = controller.getStatistics().getObstacleList();
        setImages();
        setPreferredSize(new Dimension(controller.getBuildGame().width, controller.getBuildGame().height));
        MouseAdapter ma = new MouseAdapter() {

            private Image dragImage;
            private Point clickOffset;
            private Boolean enabled = count != 2;
            private Obstacle obstacleDragged;
            @Override
            public void mousePressed(MouseEvent e) {
                if(!enabled) {
                    return;
                }
                for (Map.Entry<Image, Point> entry : locations.entrySet()) {
                    Image image = entry.getKey();
                    Point point = entry.getValue();
                    Rectangle bounds1 = new Rectangle(
                            point.x, point.y,
                            image.getWidth(GameScreen.this), image.getHeight(GameScreen.this));
                    Rectangle bounds2 = new Rectangle(
                            point.x-image.getWidth(GameScreen.this), point.y-image.getHeight(GameScreen.this),
                            image.getWidth(GameScreen.this), image.getHeight(GameScreen.this));
                    if (bounds1.contains(e.getPoint())) {
                        for(Obstacle o: obstacleList) {
                            if (bounds2.contains(e.getPoint())) {
                                obstacleDragged = o;
                                controller.getStatistics().removeObstacle(o);
                                break;
                            }
                        }
                        dragImage = image;
                        clickOffset = new Point(point.x - e.getPoint().x, point.y - e.getPoint().y);
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragImage = null;
                clickOffset = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragImage != null) {
                    Point dragPoint = new Point(e.getPoint());
                    dragPoint.x += clickOffset.x;
                    dragPoint.y += clickOffset.y;
                    locations.put(dragImage, dragPoint);
                    obstacleDragged.setCoordinates(dragPoint.x,dragPoint.y);
                    controller.getStatistics().addObstacle(obstacleDragged);
                    repaint();
                }
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //drawNoblePhantasm(g);
        //drawEnchantedSphere(g);
        if(initObstacles) {
            drawObstacles();
        }
        Graphics2D g2d = (Graphics2D) g.create();
        for (Map.Entry<Image, Point> entry : locations.entrySet()) {
            Point point = entry.getValue();
            g2d.drawImage(entry.getKey(), point.x, point.y, this);
        }
        g2d.dispose();
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

    public void drawObstacles() {
        locations.clear();
        for(Obstacle o: obstacleList) {
            locations.put(images.get("obstacle"), new Point(o.getCoordinates()[0], o.getCoordinates()[1]));
        }
    }

    private void setImages() {
        EnchantedSphere enchantedSphere = controller.getPlayer().getEnchantedSphere();
        NoblePhantasm noblePhantasm = controller.getPlayer().getNoblePhantasm();
        Image noblePhantasmImage = new ImageIcon("src/utilities/NoblePhantasm.png").getImage();
        Image enchantedSphereImage = new ImageIcon("src/utilities/EnchantedSphere.png").getImage();
        Image obstacleImage = new ImageIcon("src/utilities/EnchantedSphere.png").getImage();
        //locations.put(enchantedSphereImage, new Point(enchantedSphere.getCoordinates()[0], enchantedSphere.getCoordinates()[1]));
        //locations.put(noblePhantasmImage, new Point(noblePhantasm.getCoordinates()[0], noblePhantasm.getCoordinates()[1]));
        images.put("noblePhantasm", noblePhantasmImage);
        images.put("enchantedSphere", enchantedSphereImage);
        images.put("obstacle", obstacleImage);
    }
}
