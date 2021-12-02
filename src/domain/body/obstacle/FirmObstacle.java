package domain.body.obstacle;

import domain.needForSpear.Controller;
import domain.needForSpear.Statistics;

public class FirmObstacle extends Obstacle{
    public FirmObstacle(int x_coordinates,
                        int y_coordinates,
                        int length,
                        int width,
                        int numberOfHits) {
        super(x_coordinates, y_coordinates, length, width, numberOfHits);
        vx = 1;
        name = "Firm";
    }
    @Override
    public void move() {
        if(moving){
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            //Compares with every obstacle.
            for (Obstacle obstacle : Statistics.obstacleList){
                //Doesn't check whether it crashes with itself.
                if(!(obstacle.getCoordinates()[0] == x && obstacle.getCoordinates()[1] == y)){
                    canMoveRight &= !(obstacle.compareCoordinates(this.x + this.vx, this.y, this.width, this.height));
                    canMoveLeft &= !(obstacle.compareCoordinates(this.x - this.vx, this.y, this.width, this.height));
                }
            }
            //Compares with every frame border.
            if(!Controller.getInstance().hitFrame(this.x + this.vx, this.y, this.width, this.height).equals("None")){
                canMoveRight = false;
            }
            if(!Controller.getInstance().hitFrame(this.x - this.vx, this.y, this.width, this.height).equals("None")){
                canMoveLeft = false;
            }
            // It moves right if it can, if not left.
            if(canMoveRight){
                this.x += this.vx;
            } else if(canMoveLeft){
                this.x -= this.vx;
            }
        }
    }
}
