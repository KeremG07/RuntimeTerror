package domain.body;

import domain.body.obstacle.*;
import domain.needForSpear.*;

public class EnchantedSphere extends Body {
    private boolean unstoppable;
    private NoblePhantasm np;
    private int vx;
    private int vy;
    private boolean notShot = true;

    public EnchantedSphere(int x_coordinates,
                           int y_coordinates,
                           int width,
                           int height,
                           NoblePhantasm np) {
        super(x_coordinates, y_coordinates, width, height);
        unstoppable = false;
        this.np = np;
    }

    //In the beginning of the game Enchanted Sphere moves with Noble Phantasm, until it is shot.
    //Or when the player loses a chance and Enchanted Sphere starts on Noble Phantasm again.
    public void updateWithNP() {
        if (notShot) {
            x = np.x + 44;
            y = np.y - height;
        }
    }

    public void move() {
        reflect();
        x += vx / Controller.ticksPerSecond;
        y += vy / Controller.ticksPerSecond;
    }

    //Initializes the speed of the Enchanted sphere according to Noble Phantasm's angle after the player shoots the
    // enchanted sphere, only if it isn't shot yet.
    public void shootEnchantedSphere() {
        if (notShot) {
            double normalAngle = np.normalAngle;
            vx = -(int) (2 * np.width * Math.cos(Math.toRadians(normalAngle + 90)));
            vy = (int) (2 * np.width * Math.sin(Math.toRadians(normalAngle + 90)));
            notShot = false;
        }
    }

    //Handles reflection of Enchanted Sphere, it will be called every time before it executes its movement.
    public void reflect() {
        String wall = Controller.getInstance().hitFrame(x, y, width, height);
        boolean hitObstacle;
        if (wall.equals("UpperLeft") || wall.equals("UpperRight")) {
            vx = -vx;
            vy = -vy;
        } else if (wall.equals("Left") || wall.equals("Right")) {
            vx = -vx;
        } else if (wall.equals("Upper")) {
            vy = -vy;
        } else if (!wall.equals("None")) {
            Controller.getInstance().getPlayer().loseChance();
            notShot = true;
            updateWithNP();
            vx = 0;
            vy = 0;
        }
        //np reflect (movement and corner cases are ignored for now)
        else if (this.compareCoordinates(np.x, np.y, np.width, np.height)) {
            vy = -vy;
        } else {
            Obstacle crashingObstacle;
            for (Obstacle obstacle : Statistics.obstacleList) {
                hitObstacle = this.compareCoordinates(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
                if (hitObstacle) {
                    crashingObstacle = obstacle;
                    crashingObstacle.setNumberOfHits(crashingObstacle.getNumberOfHits() - 1);
                    //non-moving obstacle hit reflect
                    if (!crashingObstacle.isMoving()) {
                        //hit from top or bottom
                        if ((x + width) >= crashingObstacle.x
                                && x <= crashingObstacle.x + crashingObstacle.width) {
                            vy = -vy;
                        }
                        //hit from left or right
                        else if ((y + height) >= crashingObstacle.y
                                && y <= crashingObstacle.y + crashingObstacle.height) {
                            vx = -vx;
                        }
                    }
                    //moving obstacle hit reflect
                    else {
                        //hit from top or bottom
                        if ((x + width) >= crashingObstacle.x
                                && x <= crashingObstacle.x + crashingObstacle.width) {
                            vy = -vy;
                            if (vx * crashingObstacle.getVx() > 0) {
                                vx += 5;
                            } else if (vx * crashingObstacle.getVx() < 0) {
                                vx = -vx;
                            } else {
                                if (crashingObstacle.getVx() > 0) {
                                    vx += Math.abs(vy);
                                } else {
                                    vx -= Math.abs(vy);
                                }
                            }
                        }
                        //hit from right or left (same with non-moving for now)
                        else if ((y + height) >= crashingObstacle.y
                                && y <= crashingObstacle.y + crashingObstacle.height) {
                            vx = -vx;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void setUnstoppableES(boolean bool) {
        unstoppable = bool;
    }

    public void setNotShot(boolean notShot) {
        this.notShot = notShot;
    }

    public boolean isNotShot() {
        return notShot;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
}

