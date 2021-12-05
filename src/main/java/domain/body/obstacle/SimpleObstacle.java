package domain.body.obstacle;
import domain.needForSpear.*;

public class SimpleObstacle extends Obstacle {
    public SimpleObstacle(int x_coordinates,
                          int y_coordinates,
                          int width,
                          int height,
                          int numberOfHits) {
        super(x_coordinates, y_coordinates, width, height, numberOfHits);
        //Speed is initialized as this because otherwise it is smaller than 1 and doesn't have an effect.
        vx = 1;
        name = "Simple";
    }
    @Override
    public void move() {
        if(moving){
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            //Compares with every obstacle.
            for (Obstacle obstacle : Statistics.obstacleList){
                //Doesn't check whether it crashes with itself.
                if(!(obstacle.getCoordinates()[0] == this.x && obstacle.getCoordinates()[1] == this.y)){
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
