package ui.swing;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;
import domain.body.fallingBody.FallingBody;
import domain.body.fallingBody.Gift;
import domain.body.obstacle.*;
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
    public static boolean enabled;

    public static GameScreen getInstance() {
        if (instance == null)
            //System.out.println(count);
            instance = new GameScreen();
        count++;
        enabled = count != 2;
        return instance;
    }

    Controller controller;

    private Map<Point, Image> locations = new HashMap<Point, Image>();
    private HashMap<String, Image> images = new HashMap<String, Image>();
    private ArrayList<Obstacle> obstacleList;
    private ArrayList<FallingBody> fallingBodyList;

    private GameScreen() {
        this.controller = Controller.getInstance();
        obstacleList = controller.getStatistics().getObstacleList();
        fallingBodyList = controller.getStatistics().getFallingBodyList();
        setImages();
        setPreferredSize(new Dimension(controller.getFrameBorders()[0], controller.getFrameBorders()[1]));
        MouseAdapter ma = new MouseAdapter() {

            private Image dragImage;
            private Point clickOffset;
            private Obstacle obstacleDragged;

            @Override
            public void mousePressed(MouseEvent e) {
                if(!enabled) {
                    return;
                }

                for (Map.Entry<Point, Image> entry : locations.entrySet()) {
                    Image image = entry.getValue();
                    Point point = entry.getKey();
                    Rectangle bounds1 = new Rectangle(
                            point.x, point.y,
                            image.getWidth(GameScreen.this), image.getHeight(GameScreen.this));
                    if (bounds1.contains(e.getPoint())) {
                        for(Obstacle o: obstacleList) {
                            if (o.getCoordinates()[0] == point.x && o.getCoordinates()[1] == point.y) {
                                obstacleDragged = o;
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
                    locations.put(dragPoint, dragImage);
                    for(Obstacle o: obstacleList) {
                        if(o.equals(obstacleDragged)) {
                            o.setCoordinates(dragPoint.x, dragPoint.y);
                        }
                    }
                    repaint();
                }
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    @Override
    public void paint(Graphics g) {
        if (!initObstacles) {
            return;
        }
        super.paint(g);
        drawEnchantedSphere(g);
        drawNoblePhantasm(g);
        locations.clear();
        for (Obstacle o: obstacleList) {
            //System.out.printf(String.valueOf(o.getCoordinates()[0]) + "," + String.valueOf(o.getCoordinates()[1]) + "\n");
            drawObstacles(g, o);
        }
        for (FallingBody fb: fallingBodyList) {
            drawFallingBodies(g, fb);
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.dispose();
    }

    void drawNoblePhantasm(Graphics g) {
        NoblePhantasm noblePhantasm = controller.getPlayer().getNoblePhantasm();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform rotateTransform  = AffineTransform.getRotateInstance(Math.toRadians(noblePhantasm.getNormalAngle()),
                (noblePhantasm.getCoordinates()[0] + 50 - Math.sqrt(Math.pow(50,2) + Math.pow(4,2)) * Math.cos(Math.atan(0.08) + Math.toRadians(noblePhantasm.getNormalAngle()))),
                (noblePhantasm.getCoordinates()[1] + 4 - Math.sqrt(Math.pow(50,2) + Math.pow(4,2)) * Math.sin(Math.toRadians(noblePhantasm.getNormalAngle()))));
        //System.out.println(noblePhantasm.getCoordinates()[0] + 50.0 - Math.sqrt(Math.pow(50,2) + Math.pow(4,2)) * Math.cos(Math.atan(0.08) + Math.toRadians(noblePhantasm.getNormalAngle())));
        //System.out.println(noblePhantasm.getCoordinates()[1] - 4.0 - Math.sqrt(Math.pow(50,2) + Math.pow(4,2)) * Math.sin(Math.atan(0.08) + Math.toRadians(noblePhantasm.getNormalAngle())));
        //AffineTransform old = g2d.getTransform();
        rotateTransform.translate(noblePhantasm.getCoordinates()[0] + 50.0 - Math.sqrt(Math.pow(50,2) + Math.pow(4,2)) * Math.cos(Math.atan(0.08) + Math.toRadians(noblePhantasm.getNormalAngle())),
                noblePhantasm.getCoordinates()[1] + 4.0 - Math.sqrt(Math.pow(50,2) + Math.pow(4,2)) * Math.sin(Math.atan(0.08) + Math.toRadians(noblePhantasm.getNormalAngle())));
        g2d.drawImage(images.get("noblePhantasm"), rotateTransform , null);
        //g2d.setTransform(old);
    }

    void drawEnchantedSphere(Graphics g) {
        EnchantedSphere enchantedSphere = controller.getPlayer().getEnchantedSphere();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        // AffineTransform old = g2d.getTransform();
        at.translate(enchantedSphere.getCoordinates()[0], enchantedSphere.getCoordinates()[1]);
        g2d.drawImage(images.get("enchantedSphere"), at , null);
        //g2d.setTransform(old);
    }

    void drawObstacles(Graphics g, Obstacle o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("default", Font.BOLD, 8));
        g2d.setColor(Color.BLACK);
        AffineTransform at = new AffineTransform();
        // AffineTransform old = g2d.getTransform();
        at.translate(o.getCoordinates()[0], o.getCoordinates()[1]);
        Image imageToDraw;
        if (o instanceof SimpleObstacle) {
            imageToDraw = images.get("simpleObstacle");
        } else if (o instanceof FirmObstacle) {
            imageToDraw = images.get("firmObstacle");
        } else if (o instanceof ExplosiveObstacle) {
            imageToDraw = images.get("explosiveObstacle");
        } else {
            imageToDraw = images.get("giftObstacle");
        }
        locations.put(new Point(o.getCoordinates()[0], o.getCoordinates()[1]), imageToDraw);
        g2d.drawImage(imageToDraw, at, null);
        if(o instanceof FirmObstacle) {
            g2d.drawString(Integer.toString(o.getNumberOfHits()),
                    o.getCoordinates()[0] + 11*(o.width)/24,
                    o.getCoordinates()[1] + o.height - 2);
        }
        //g2d.setTransform(old);
    }

    void drawFallingBodies(Graphics g, FallingBody fb) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        // AffineTransform old = g2d.getTransform();
        at.translate(fb.getCoordinates()[0], fb.getCoordinates()[1]);
        Image imageToDraw;
        if (fb instanceof Gift) {
            imageToDraw = images.get("gift");
        } else {
            imageToDraw = images.get("remains");
        }
        locations.put(new Point(fb.getCoordinates()[0], fb.getCoordinates()[1]), imageToDraw);
        g2d.drawImage(imageToDraw, at, null);
    }

    private void setImages() {
        Image noblePhantasmImage = new ImageIcon("src/main/java/utilities/NoblePhantasm.png").getImage();
        Image enchantedSphereImage = new ImageIcon("src/main/java/utilities/EnchantedSphere.png").getImage();
        Image simpleObstacleImage = new ImageIcon("src/main/java/utilities/simpleObs.png").getImage();
        Image firmObstacleImage = new ImageIcon("src/main/java/utilities/firmObs.png").getImage();
        Image explosiveObstacleImage = new ImageIcon("src/main/java/utilities/explosiveObs.png").getImage();
        Image giftObstacleImage = new ImageIcon("src/main/java/utilities/giftObs.png").getImage();
        Image remainsImage = new ImageIcon("src/main/java/utilities/remains.png").getImage();
        Image giftImage = new ImageIcon("src/main/java/utilities/gift.png").getImage();
        images.put("noblePhantasm", noblePhantasmImage);
        images.put("enchantedSphere", enchantedSphereImage);
        images.put("simpleObstacle", simpleObstacleImage);
        images.put("firmObstacle", firmObstacleImage);
        images.put("explosiveObstacle", explosiveObstacleImage);
        images.put("giftObstacle", giftObstacleImage);
        images.put("remains", remainsImage);
        images.put("gift", giftImage);
    }
}
