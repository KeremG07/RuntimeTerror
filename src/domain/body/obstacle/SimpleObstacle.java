package domain.body.obstacle;
import domain.needForSpear.*;

public class SimpleObstacle extends Obstacle {

    public SimpleObstacle(double x_coordinates,
                          double y_coordinates,
                          double length,
                          double width,
                          double vx,
                          double vy,
                          String name,
                          int numberOfHits) {
        super(x_coordinates, y_coordinates, length, width, vx, vy, name, numberOfHits);
    }

    @Override
    public void move() {
        if(moving){
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            for (Obstacle obstacle : Statistics.obstacleList){
                canMoveRight &= obstacle.compareCoordinates(this.x + this.vx, this.y, this.length, this.width);
                canMoveLeft &= obstacle.compareCoordinates(this.x - this.vx, this.y, this.length, this.width);
            }
            if(!Controller.hitFrame(this.x + this.vx, this.y, this.length, this.width).equals("None")){
                canMoveRight = false;
            }
            if(!Controller.hitFrame(this.x - this.vx, this.y, this.length, this.width).equals("None")){
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
