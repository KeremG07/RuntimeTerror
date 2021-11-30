package domain.body;

import domain.body.obstacle.Obstacle;
import domain.needForSpear.Controller;
import domain.needForSpear.Statistics;

public class EnchantedSphere extends Body {
    private boolean unstoppable;
    private NoblePhantasm np;
    private double vx;
    private double vy;
    public EnchantedSphere(double x_coordinates,
                           double y_coordinates,
                           double length,
                           double width,
                           NoblePhantasm np) {
        super(x_coordinates, y_coordinates, length, width);
        unstoppable=false;
        this.np = np;
    }

    public void updateWithNP() {
        x = np.x + 44;
        y = np.y - length;
    }
    public void move() {
        reflect();
        x += vx/Controller.ticksPerSecond;
        y += vy/Controller.ticksPerSecond;
    }

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
    
    public void shootEnchantedSphere(){
        vx = 0;
        vy = 2*np.width;
    }
    
    public void setUnstoppableES(boolean bool){
        unstoppable = bool;
    }
}
