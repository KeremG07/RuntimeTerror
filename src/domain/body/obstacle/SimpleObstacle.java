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
            SimpleObstacle copyR = new SimpleObstacle(this.x + this.vx, this.y, this.length, this.width, this.vx, this.vy, this.name, this.numberOfHits);
            SimpleObstacle copyL = new SimpleObstacle(this.x - this.vx, this.y, this.length, this.width, this.vx, this.vy, this.name, this.numberOfHits);

            for (Obstacle obstacle : Statistics.obstacleList){
                canMoveRight &= copyR.compareCoordinates(obstacle);
                canMoveLeft &= copyL.compareCoordinates(obstacle);
            }
            canMoveRight &= copyR.compareFrame();
            canMoveLeft &= copyL.compareFrame();

            if(canMoveRight){
                this.x += this.vx;
            } else if(canMoveLeft){
                this.x -= this.vx;
            }
        }
    }
}
