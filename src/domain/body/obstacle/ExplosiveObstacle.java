package domain.body.obstacle;
import domain.needForSpear.*;
public class ExplosiveObstacle extends Obstacle {

    private double vx;
    private double vy;
    //The coordinates and the radius of the circle that the obstacle will move around.
    private final double circleRadius = 3*Controller.getInstance().getPlayer().getNoblePhantasm().width/2;
    private final double circleCenterX = this.x + width/2;
    private final double circleCenterY = this.y + length + circleRadius;
    //The degree between the circle's center and the obstacle.
    private double degree = 90;
    private String name;
    public ExplosiveObstacle(double x_coordinates,
                             double y_coordinates,
                             double length,
                             double width,
                             int numberOfHits) {
        super(x_coordinates, y_coordinates, length, width, numberOfHits);
        name = "Explosive";
    }

    //Doesn't move with the correct speed yet. Needs testing.
    @Override
    public void move() {
        //double velocity = Controller.getInstance().getPlayer().getNoblePhantasm().width/(4*Controller.ticksPerSecond);
        degree += 0.5;
        double rads = Math.toRadians(degree - 90); // 0 becomes the top
        this.x = Math.round((float) (circleCenterX + Math.cos(rads) * circleRadius));
        this.y = Math.round((float) (circleCenterY + Math.sin(rads) * circleRadius));
    }
}
