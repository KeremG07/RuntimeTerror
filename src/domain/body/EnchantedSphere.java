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
                           int length,
                           int width,
                           NoblePhantasm np) {
        super(x_coordinates, y_coordinates, length, width);
        unstoppable=false;
        this.np = np;
    }
    //In the beginning of the game Enchanted Sphere moves with Noble Phantasm.
    public void updateWithNP() {
        if(notShot){
            x = np.x + 44;
            y = np.y - width;
        }
    }
    public void move() {
        reflect();
        x += vx/Controller.ticksPerSecond;
        y += vy/Controller.ticksPerSecond;
    }
    //Handles reflection of Enchanted Sphere, it will be called every time before it executes its movement.
    public void reflect() {
        String wall = Controller.getInstance().hitFrame(x,y, width, height);
        boolean hitObstacle;
        if(wall.equals("UpperLeft") || wall.equals("UpperRight")){
            vx = -vx;
            vy = -vy;
        }
        else if(wall.equals("Left") || wall.equals("Right")){
            vx = -vx;
        }
        else if(wall.equals("Upper")){
            vy = -vy ;
        }
        else if(!wall.equals("None")){
            Controller.getInstance().getPlayer().loseChance();
            updateWithNP();
        }
        //np reflect (movement and corner cases are ignored)
        else if(this.compareCoordinates(np.x, np.y, np.width, np.height)){
            vy = -vy;
        }
        else {
            Obstacle crashingObstacle;
            for(Obstacle obstacle : Statistics.obstacleList){
                hitObstacle = this.compareCoordinates(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
                if(hitObstacle){
                    crashingObstacle = obstacle;
                    crashingObstacle.setNumberOfHits(crashingObstacle.getNumberOfHits()-1);
                    //non-moving obstacle hit reflect
                    if(!crashingObstacle.isMoving()){
                        //hit from top or bottom
                        if((x + width /2) >= crashingObstacle.x
                                && (x + width /2) <= crashingObstacle.x + crashingObstacle.width){
                            vy = -vy;
                        }
                        //hit from left or right
                        else if((y + height /2) >= crashingObstacle.y
                                && (x + height /2) <= crashingObstacle.y + crashingObstacle.height){
                            vx = -vx;
                        }
                    }
                    //moving obstacle hit reflect
                    else{
                        //hit from top or bottom
                        if((x + width /2) >= crashingObstacle.x
                                && (x + width /2) <= crashingObstacle.x + crashingObstacle.width){
                            vy = -vy;
                            if(vx * crashingObstacle.getVx() > 0){
                                //vx should be increased
                            }
                            else if(vx * crashingObstacle.getVx() < 0){
                                vx = -vx;
                            }
                            else{
                                if(crashingObstacle.getVx() > 0){
                                    vx += Math.abs(vy);
                                }
                                else{
                                    vx -= Math.abs(vy);
                                }
                            }
                        }
                        //hit from right or left (same with non-moving for now)
                        else if((y + height /2) >= crashingObstacle.y
                                && (x + height /2) <= crashingObstacle.y + crashingObstacle.height){
                            vx = -vx;
                        }
                    }
                    break;
                }
            }
        }
    }
    //Initializes the speed of the Enchanted sphere according to Noble Phantasm's angle.
    public void shootEnchantedSphere(){
        if(notShot){
            double normalAngle = np.normalAngle;
            vx = (int) (2*np.height *Math.cos(Math.toRadians(normalAngle)));
            vy = (int) (2*np.height *Math.sin(Math.toRadians(normalAngle)));
            notShot = false;
        }
    }
    
    public void setUnstoppableES(boolean bool){
        unstoppable = bool;
    }
}
