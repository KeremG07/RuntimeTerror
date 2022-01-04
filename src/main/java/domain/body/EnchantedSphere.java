package domain.body;

import domain.body.obstacle.*;
import domain.needForSpear.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class EnchantedSphere extends Body {
    private boolean unstoppable;
    private NoblePhantasm np;
    private double vx;
    private double vy;
    private boolean notShot = true;

    public EnchantedSphere(double  x_coordinates,
                           double  y_coordinates,
                           double  width,
                           double  height,
                           NoblePhantasm np) {
        super(x_coordinates, y_coordinates, width, height);
        unstoppable = false;
        this.np = np;
    }

    //In the beginning of the game Enchanted Sphere moves with Noble Phantasm, until it is shot.
    //Or when the player loses a chance and Enchanted Sphere starts on Noble Phantasm again.
    public void updateWithNoblePhantasm() {
        if (notShot) {
            x = np.getCoordinates()[0] + 0.5 * (np.getWidth() * Math.cos(Math.toRadians(np.getNormalAngle())) - width);
            y = np.getCoordinates()[1] + 0.5 * np.getWidth() * Math.sin(Math.toRadians(np.getNormalAngle())) - height;
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
            double normalAngle = np.getNormalAngle();
            vx = -(2 * np.width * Math.cos(Math.toRadians(normalAngle + 90)));
            if(normalAngle == 0) vx = 0;
            vy = (2 * np.width * Math.sin(Math.toRadians(normalAngle + 90)));
            if(vy > 0){
                vy = -vy;
            }
            notShot = false;
        }
    }

    //checks if es crashes with np considering rotated np coordinates
    public boolean compareCoordinatesWithNoblePhantasm(){
        double xPosRight, yPosRight;
        ArrayList<Point2D.Double> positions = new ArrayList<Point2D.Double>();
        ArrayList<Double> distances = new ArrayList<Double>();
        if(np.getNormalAngle() <= 1.5 && np.getNormalAngle() >= -1.5){
            return this.compareCoordinates(np.x,np.y,np.width,np.height);
        }
        else {
            xPosRight = (np.x + np.width * Math.cos(Math.toRadians(np.getNormalAngle())));
            yPosRight = (np.y + np.width * Math.sin(Math.toRadians(np.getNormalAngle())));
            for(int i = 0; i<10000; i++) {
                positions.add(new Point2D.Double(np.x + i * (xPosRight-np.x) / 10000,np.y + i * (yPosRight-np.y) / 10000));
                distances.add(positions.get(i).distance(new Point2D.Double(x+width/2, y+height/2)));
            }
            for(int i=0; i<distances.size(); i++) {
                double dist = distances.get(i);
                if (dist>5.9 && dist<6.1) {
                    return true;
                } else {
                    continue;
                }
            }
        //same with previous, different calculations
        }
        return false;
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
            updateWithNoblePhantasm();
            vx = 0;
            vy = 0;
        }
        //np reflect (still has issues)
        else if (compareCoordinatesWithNoblePhantasm()) {
            noblePhantasmReflect();
        } else {
            Obstacle crashingObstacle;
            for (Obstacle obstacle : Controller.getInstance().getStatistics().getObstacleList()) {
                hitObstacle = this.compareCoordinates(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
                if (hitObstacle) {
                    crashingObstacle = obstacle;
                    if(this.unstoppable){
                        if(!crashingObstacle.isFrozen()){
                            crashingObstacle.setNumberOfHits(0);
                        } else {
                            crashingObstacle.setNumberOfHits(crashingObstacle.getNumberOfHits() - 1);
                            obstacleReflect(crashingObstacle);
                        }
                    } else {
                        if(!crashingObstacle.isFrozen()){
                            crashingObstacle.setNumberOfHits(crashingObstacle.getNumberOfHits() - 1);
                        }
                        obstacleReflect(crashingObstacle);
                    }
                    break;
                }
            }
        }
    }

    public void noblePhantasmReflect(){
        double speed = Math.sqrt(vx*vx + vy*vy);
        double angle = Math.toDegrees(Math.atan(vx/vy));
        double reflectAngle = angle + 2 * np.getNormalAngle();
        if(np.getNormalAngle() == -45 && vx == 0 && vy > 0){
            vx = -vy;
            vy = 0;
        }else if(np.getNormalAngle() == 45 && vx == 0 && vy > 0){
            vx = vy;
            vy = 0;
        }
        //these do not happen frequently, but still added.
        else if(vy == 0 && np.getNormalAngle() > 0){
            reflectAngle = 90 - 2 * np.getNormalAngle();
            vy = (-speed * Math.cos(Math.toRadians(reflectAngle)));
            vx = (-speed * Math.sin(Math.toRadians(reflectAngle)));
        }else if(vy == 0 && np.getNormalAngle() < 0){
            reflectAngle = 90 + 2 * np.getNormalAngle();
            vy = (-speed * Math.cos(Math.toRadians(reflectAngle)));
            vx = (speed * Math.sin(Math.toRadians(reflectAngle)));
            //main reflection case
        }else if(reflectAngle <= 90 || reflectAngle >= -90){
            if(vy > 0) {
                vy = (-speed * Math.cos(Math.toRadians(reflectAngle)));
                vx = (speed * Math.sin(Math.toRadians(reflectAngle)));
            }
        }else{
            vy = 10;
        }
    }

    public void obstacleReflect(Obstacle crashingObstacle){
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
                    if(vx > 0){
                        vx += 20;
                    }else{
                        vx -= 20;
                    }
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

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }
}

