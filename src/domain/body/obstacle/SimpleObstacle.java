package domain.body.obstacle;
import domain.needForSpear.*;

public class SimpleObstacle extends Obstacle {
    public SimpleObstacle(int x_coordinates,
                          int y_coordinates,
                          int length,
                          int width,
                          int numberOfHits) {
        super(x_coordinates, y_coordinates, length, width, numberOfHits);
        vx = 1;
        name = "Simple";
    }
    @Override
    public void move() {
        if(moving){
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            for (Obstacle obstacle : Statistics.obstacleList){
                if(!(obstacle.getCoordinates()[0] == this.x && obstacle.getCoordinates()[1] == this.y)){
                    canMoveRight &= !(obstacle.compareCoordinates(this.x + this.vx, this.y, this.length, this.width));
                    canMoveLeft &= !(obstacle.compareCoordinates(this.x - this.vx, this.y, this.length, this.width));
                }
            }
            if(!Controller.getInstance().hitFrame(this.x + this.vx, this.y, this.length, this.width).equals("None")){
                canMoveRight = false;
            }
            if(!Controller.getInstance().hitFrame(this.x - this.vx, this.y, this.length, this.width).equals("None")){
                canMoveLeft = false;
            }
            if(canMoveRight){
                this.x += this.vx;
            } else if(canMoveLeft){
                this.x -= this.vx;
            }
        }
    }
}
