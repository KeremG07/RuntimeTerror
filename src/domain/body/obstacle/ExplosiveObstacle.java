package domain.body.obstacle;
import domain.needForSpear.*;
public class ExplosiveObstacle extends Obstacle {

    private int vx;
    private int vy;
    //The coordinates and the radius of the circle that the obstacle will move around.
    private final int circleRadius = 3*100/2;
    private final int circleCenterX = this.x + width/2;
    private final int circleCenterY = this.y + length + circleRadius;
    //The degree between the circle's center and the obstacle.
    private double degree = 90;
    private String name;
    public ExplosiveObstacle(int x_coordinates,
                             int y_coordinates,
                             int length,
                             int width,
                             int numberOfHits) {
        super(x_coordinates, y_coordinates, length, width, numberOfHits);
        name = "Explosive";
        vx = 100/(4*Controller.ticksPerSecond);
    }

    //Doesn't move with the correct speed yet. Needs testing.
    @Override
    public void move() {
        degree += 0.5;
        double rads = Math.toRadians(degree - 90); // 0 becomes the top
        this.x = Math.round((float) (circleCenterX + Math.cos(rads) * circleRadius));
        this.y = Math.round((float) (circleCenterY + Math.sin(rads) * circleRadius));
    }

}
