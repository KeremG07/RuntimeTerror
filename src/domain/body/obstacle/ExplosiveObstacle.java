package domain.body.obstacle;
import java.util.Random;
public class ExplosiveObstacle extends Obstacle {

    public ExplosiveObstacle(double x_coordinates,
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

    }
}
