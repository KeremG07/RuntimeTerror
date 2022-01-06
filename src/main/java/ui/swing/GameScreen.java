package ui.swing;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import domain.body.EnchantedSphere;
import domain.body.MagicalHex;
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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends JPanel {

    private static GameScreen instance;
    private static int count = 0;
    private static boolean initObstacles = false;
    private static boolean enabled;
    private Controller controller;
    private Map<Point2D.Double, Image> locations = new HashMap<Point2D.Double, Image>();
    private HashMap<String, Image> images = new HashMap<String, Image>();
    private ArrayList<Obstacle> obstacleList;
    private ArrayList<FallingBody> fallingBodyList;
    private ArrayList<MagicalHex> magicalHexList;
    private Image bgImage = new ImageIcon("src/main/java/utilities/background.png").getImage();

    public static GameScreen getInstance() {
        if (instance == null)
            instance = new GameScreen();
        count++;
        enabled = count != 2; // to check GameScreen is at PlayModeFrame
        return instance;
    }
    private GameScreen() {
        this.controller = Controller.getInstance();
        obstacleList = controller.getStatistics().getObstacleList();
        fallingBodyList = controller.getStatistics().getFallingBodyList();
        magicalHexList = controller.getStatistics().getMagicalHexList();
        setImages();
        setPreferredSize(new Dimension((int)controller.getFrameBorders()[0], (int)controller.getFrameBorders()[1]));


        // dragging part
        MouseAdapter ma = new MouseAdapter() {

            private Image dragImage;
            private Point2D.Double clickOffset;
            private Obstacle obstacleDragged;

            @Override
            public void mousePressed(MouseEvent e) {
                if(!enabled) { // drag only in BuildMode
                    return;
                }

                for (Map.Entry<Point2D.Double, Image> entry : locations.entrySet()) {
                    Image image = entry.getValue();
                    Point2D.Double point = entry.getKey();
                    Rectangle2D.Double bounds1 = new Rectangle2D.Double(
                            point.x, point.y,
                            image.getWidth(GameScreen.this), image.getHeight(GameScreen.this));
                    if (bounds1.contains(e.getPoint())) {
                        for(Obstacle o: obstacleList) {
                            if (o.getCoordinates()[0] == point.x && o.getCoordinates()[1] == point.y) {
                                obstacleDragged = o; // dragged obstacle
                                break;
                            }
                        }
                        dragImage = image; // dragged image
                        clickOffset = new Point2D.Double(point.x - e.getPoint().x, point.y - e.getPoint().y);
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
                    boolean collides = false;
                    Point2D.Double dragPoint = new Point2D.Double(e.getX(), e.getY());
                    dragPoint.x += clickOffset.x;
                    dragPoint.y += clickOffset.y;
                    // to check dragged obstacle doesn't overlap with any other obstacle
                    for(Obstacle o: obstacleList) {
                        if(o.compareCoordinates(dragPoint.x, dragPoint.y, obstacleDragged.getWidth(), obstacleDragged.getHeight())) {
                            collides = true;
                            break;
                        }
                    }
                    // new obstacle location
                    if (!collides && (dragPoint.y <= 400)) {
                        locations.put(dragPoint, dragImage);
                        for (Obstacle o : obstacleList) {
                            if (o.equals(obstacleDragged)) {
                                o.setCoordinates(dragPoint.x, dragPoint.y);
                            }
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
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
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
            drawObstacles(g, o);
        }
        for (FallingBody fb: fallingBodyList) {
            drawFallingBodies(g, fb);
        }
        for (MagicalHex hex: magicalHexList) {
            drawHexProjectiles(g, hex);
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.dispose();
    }

    void drawNoblePhantasm(Graphics g) {
        NoblePhantasm noblePhantasm = controller.getPlayer().getNoblePhantasm();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform rotateTransform  = AffineTransform.getRotateInstance(Math.toRadians(noblePhantasm.getNormalAngle()),
                noblePhantasm.getCoordinates()[0],
                noblePhantasm.getCoordinates()[1]);
        rotateTransform.translate(noblePhantasm.getCoordinates()[0],
                noblePhantasm.getCoordinates()[1]);
        if(controller.getPlayer().getActiveAbility().equals("DoubleNP")) {
            g2d.drawImage(images.get("noblePhantasmExpanded"), rotateTransform , null);
        } else if(controller.getPlayer().getActiveAbility().equals("MagicalHex")) {
            g2d.drawImage(images.get("noblePhantasmMagicalHex"), rotateTransform , null);
        } else {
            g2d.drawImage(images.get("noblePhantasm"), rotateTransform , null);
        }
    }

    void drawEnchantedSphere(Graphics g) {
        EnchantedSphere enchantedSphere = controller.getPlayer().getEnchantedSphere();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.translate(enchantedSphere.getCoordinates()[0], enchantedSphere.getCoordinates()[1]);
        if(controller.getPlayer().getActiveAbility().equals("Unstoppable")) {
            g2d.drawImage(images.get("unstoppableEnchantedSphere"), at , null);
        } else {
            g2d.drawImage(images.get("enchantedSphere"), at , null);
        }
    }

    void drawObstacles(Graphics g, Obstacle o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("default", Font.BOLD, 8));
        g2d.setColor(Color.BLACK);
        AffineTransform at = new AffineTransform();
        at.translate(o.getCoordinates()[0], o.getCoordinates()[1]);
        Image imageToDraw;
        // obstacle type and frozen
        if (o.isFrozen()) {
            if (o instanceof ExplosiveObstacle) {
                imageToDraw = images.get("frozenRound");
            } else {
                imageToDraw = images.get("frozenLong");
            }
        } else {
            if (o instanceof SimpleObstacle) {
                imageToDraw = images.get("simpleObstacle");
            } else if (o instanceof FirmObstacle) {
                imageToDraw = images.get("firmObstacle");
            } else if (o instanceof ExplosiveObstacle) {
                imageToDraw = images.get("explosiveObstacle");
            } else if (o instanceof  GiftObstacle) {
                imageToDraw = images.get("giftObstacle");
            } else {
                imageToDraw = images.get("hollowPurpleObstacle");
            }
        }
        locations.put(new Point2D.Double(o.getCoordinates()[0], o.getCoordinates()[1]), imageToDraw);
        g2d.drawImage(imageToDraw, at, null);
        // firm obstacle hit count
        if(o instanceof FirmObstacle) {
            g2d.drawString(Integer.toString(o.getNumberOfHits()),
                    (int) (o.getCoordinates()[0] + 11*(o.getWidth())/24),
                    (int) (o.getCoordinates()[1] + o.getHeight() - 2));
        }
    }

    void drawFallingBodies(Graphics g, FallingBody fb) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.translate(fb.getCoordinates()[0], fb.getCoordinates()[1]);
        Image imageToDraw;
        if (fb instanceof Gift) {
            imageToDraw = images.get("gift");
        } else {
            imageToDraw = images.get("remains");
        }
        locations.put(new Point2D.Double(fb.getCoordinates()[0], fb.getCoordinates()[1]), imageToDraw);
        g2d.drawImage(imageToDraw, at, null);
    }

    void drawHexProjectiles(Graphics g, MagicalHex hex) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.translate(hex.getCoordinates()[0], hex.getCoordinates()[1]);
        Image imageToDraw;
        imageToDraw = images.get("hexProjectile");
        locations.put(new Point2D.Double(hex.getCoordinates()[0], hex.getCoordinates()[1]), imageToDraw);
        g2d.drawImage(imageToDraw, at, null);
    }

    private void setImages() {
        Image noblePhantasmImage = new ImageIcon("src/main/java/utilities/NoblePhantasm.png").getImage();
        Image noblePhantasmExpandedImage = new ImageIcon("src/main/java/utilities/npExpanded.png").getImage();
        Image noblePhantasmMagicalHexImage = new ImageIcon("src/main/java/utilities/npMagicalHex.png").getImage();
        Image enchantedSphereImage = new ImageIcon("src/main/java/utilities/EnchantedSphere.png").getImage();
        Image unstoppableEnchantedSphereImage = new ImageIcon("src/main/java/utilities/esUnstoppable.png").getImage();
        Image simpleObstacleImage = new ImageIcon("src/main/java/utilities/simpleObs.png").getImage();
        Image firmObstacleImage = new ImageIcon("src/main/java/utilities/firmObs.png").getImage();
        Image explosiveObstacleImage = new ImageIcon("src/main/java/utilities/explosiveObs.png").getImage();
        Image giftObstacleImage = new ImageIcon("src/main/java/utilities/giftObs.png").getImage();
        Image hollowPurpleObstacleImage = new ImageIcon("src/main/java/utilities/hollowPurpleObs.png").getImage();
        Image remainsImage = new ImageIcon("src/main/java/utilities/remains.png").getImage();
        Image giftImage = new ImageIcon("src/main/java/utilities/gift.png").getImage();
        Image frozenRectangleImage = new ImageIcon("src/main/java/utilities/frozenLongObs.png").getImage();
        Image frozenCircleImage = new ImageIcon("src/main/java/utilities/frozenRoundObs.png").getImage();
        Image hexProjectile = new ImageIcon("src/main/java/utilities/hexProjectile.png").getImage();
        images.put("noblePhantasm", noblePhantasmImage);
        images.put("noblePhantasmExpanded", noblePhantasmExpandedImage);
        images.put("noblePhantasmMagicalHex", noblePhantasmMagicalHexImage);
        images.put("enchantedSphere", enchantedSphereImage);
        images.put("unstoppableEnchantedSphere", unstoppableEnchantedSphereImage);
        images.put("simpleObstacle", simpleObstacleImage);
        images.put("firmObstacle", firmObstacleImage);
        images.put("explosiveObstacle", explosiveObstacleImage);
        images.put("giftObstacle", giftObstacleImage);
        images.put("remains", remainsImage);
        images.put("gift", giftImage);
        images.put("hollowPurpleObstacle", hollowPurpleObstacleImage);
        images.put("frozenLong", frozenRectangleImage);
        images.put("frozenRound", frozenCircleImage);
        images.put("hexProjectile", hexProjectile);

    }

    public static boolean isInitObstacles() {
        return initObstacles;
    }

    public static void setInitObstacles(boolean initObstacles) {
        GameScreen.initObstacles = initObstacles;
    }


    public Map<Point2D.Double, Image> getLocations() { return locations; }
}
