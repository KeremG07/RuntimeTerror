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
            if(vy > 0){
                vy = -vy;
            }
            notShot = false;
        }
    }

    //checks if es crashes with np considering rotated np coordinates
    public boolean compareCoordinatesWithNP(){
        int xPos, yPos;
        double slope;
        boolean crashing = false;
        if(np.normalAngle == 0 || Math.abs(np.normalAngle) == 1){
            return this.compareCoordinates(np.x,np.y,np.width,np.height);
        }
        else if(np.normalAngle > 0){
            //calculates rotated x,y coords of np (top left corner)
            xPos = (int) (np.x + np.width / 2 - np.width / 2 * Math.cos(Math.toRadians(np.normalAngle)));
            yPos = (int) (np.y - np.width / 2 * Math.sin(Math.toRadians(np.normalAngle)));

            //checks if ES crashes
            if(x > xPos && x < xPos + np.width * Math.cos(Math.toRadians(np.normalAngle))
                && y + height > yPos && y + height < yPos + np.width * Math.sin(Math.toRadians(np.normalAngle))){
                slope = (double)(y + height - yPos) / (double)(x - xPos);
                //since there is loss, checks the slope in an interval
                if(slope == Math.tan(Math.toRadians(np.normalAngle)) ||
                   (slope <= Math.tan(Math.toRadians(np.normalAngle+3.5)) && slope >= Math.tan(Math.toRadians(np.normalAngle-3.5)))){
                    crashing = true;
                }
            //corner (not sure if this works)
            }else if(x <= xPos && xPos - x < np.height && y + height > yPos && y+height-yPos < np.height){
                crashing = true;
            }
        //same with previous, different calculations
        }else{
            xPos = (int) (np.x + np.width / 2 - np.width / 2 * Math.cos(Math.toRadians(np.normalAngle)));
            yPos = (int) (np.y - np.width / 2 * Math.sin(Math.toRadians(np.normalAngle)));

            if(x + width > xPos && x + width < xPos + np.width * Math.cos(Math.toRadians(np.normalAngle))
                    && y + height < yPos && y + height > yPos + np.width * Math.sin(Math.toRadians(np.normalAngle))){
                slope = (double)(y + height - yPos) / (double)(x + width - xPos);
                if(slope == Math.tan(Math.toRadians(np.normalAngle)) ||
                   (slope >= Math.tan(Math.toRadians(np.normalAngle-3.5)) && slope <= Math.tan(Math.toRadians(np.normalAngle+3.5)))){
                    crashing = true;
                }
            }else if(x >= xPos + np.width * Math.cos(Math.toRadians(np.normalAngle))
                    && x <= xPos + np.width * Math.cos(Math.toRadians(np.normalAngle)) + np.height &&
                    y + height >= yPos + np.width * Math.sin(Math.toRadians(np.normalAngle))
                    && y + height < yPos + np.width * Math.sin(Math.toRadians(np.normalAngle)) + np.height){
                crashing = true;
            }
        }
        return crashing;
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
        //np reflect (still has issues)
        else if (compareCoordinatesWithNP()) {
            double speed = Math.sqrt(vx*vx + vy*vy);
            double angle = Math.toDegrees(Math.atan((double) vx/vy));
            double reflectAngle = angle + 2 * np.normalAngle;
            if(np.normalAngle == -45 && vx == 0 && vy > 0){
                vx = -vy;
                vy = 0;
            }else if(np.normalAngle == 45 && vx == 0 && vy > 0){
                vx = vy;
                vy = 0;
            }/*else if(np.normalAngle == -45 && vy == 0 && vx != 0){
                vy = -vx;
                vx = 0;
            }else if(np.normalAngle == 45 && vy == 0 && vx != 0){
                vy = vx;
                vx = 0;
            }else if((np.normalAngle == 45 || np.normalAngle == -45) && Math.abs(vx) == vy){
                vx = -vx;
                vy = -vy;
            }*/
            //these do not happen frequently, but still added.
            else if(vy == 0 && np.normalAngle > 0){
                reflectAngle = 90 - 2 * np.normalAngle;
                vy = (int) (-speed * Math.cos(Math.toRadians(reflectAngle)));
                vx = (int) (-speed * Math.sin(Math.toRadians(reflectAngle)));
            }else if(vy == 0 && np.normalAngle < 0){
                reflectAngle = 90 + 2 * np.normalAngle;
                vy = (int) (-speed * Math.cos(Math.toRadians(reflectAngle)));
                vx = (int) (speed * Math.sin(Math.toRadians(reflectAngle)));
            //main reflection case
            }else if(reflectAngle <= 90 || reflectAngle >= -90){
                if(vy > 0) {
                    vy = (int) (-speed * Math.cos(Math.toRadians(reflectAngle)));
                    vx = (int) (speed * Math.sin(Math.toRadians(reflectAngle)));
                }
            }else{
                vy = 10;
            }

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

