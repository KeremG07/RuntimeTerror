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
    //In the beginning of the game Enchanted Sphere is moves with Noble Phantasm.
    public void updateWithNP() {
        if(notShot){
            x = np.x + 44;
            y = np.y - length;
        }
    }
    public void move() {
        reflect();
        x += vx/Controller.ticksPerSecond;
        y += vy/Controller.ticksPerSecond;
    }
    //Handles reflection of Enchanted Sphere, it will be called every time before it executes its movement.
    public void reflect() {
        String wall = Controller.getInstance().hitFrame(x,y,length,width);
        boolean hitObstacle = false;
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
        else {
            Obstacle crashingObstacle;
            for(Obstacle obstacle : Statistics.obstacleList){
                hitObstacle = this.compareCoordinates(obstacle.x, obstacle.y, obstacle.length, obstacle.width);
                if(hitObstacle){
                    crashingObstacle = obstacle;
                    crashingObstacle.setNumberOfHits(crashingObstacle.getNumberOfHits()-1);
                    break;
                }
            }
            //It will reflect according to the obstacle that it hit. The reflection is based on whether the obstacle moves,
            //where the enchanted sphere hit it from. Comparing coordinates of the two (seeing which one is on the left/
            //right or up/down compared to the other) will help decide how it will reflect.
            if(!hitObstacle){
                //If the enchanted sphere didn't hit an obstacle it should be checked whether it hit noble phantasm and
                //reflect accordingly.
            }
        }
    }
    //Initializes the speed of the Enchanted sphere according to Noble Phantasm's angle.
    public void shootEnchantedSphere(){
        if(notShot){
            double normalAngle = np.normalAngle;
            vx = (int) (2*np.width*Math.cos(Math.toRadians(normalAngle)));
            vy = (int) (2*np.width*Math.sin(Math.toRadians(normalAngle)));
            notShot = false;
        }
    }
    
    public void setUnstoppableES(boolean bool){
        unstoppable = bool;
    }
}
