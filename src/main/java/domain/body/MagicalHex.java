package domain.body;

import domain.body.obstacle.Obstacle;
import domain.needForSpear.Controller;
import domain.needForSpear.Statistics;

public class MagicalHex extends Body {

    private double vx;
    private double vy;
    private NoblePhantasm np;

    public MagicalHex(double x_coordinates,
                      double y_coordinates,
                      double width,
                      double height,
                      NoblePhantasm np) {
        super(x_coordinates, y_coordinates, width, height);
        this.np = np;
        initSpeed();
    }


    public void initSpeed() {
        double normalAngle = np.getNormalAngle();
        vx = -(2 * np.width * Math.cos(Math.toRadians(normalAngle + 90)));
        if(normalAngle == 0) vx = 0;
        vy = (2 * np.width * Math.sin(Math.toRadians(normalAngle + 90)));
        if(vy > 0){
            vy = -vy;
        }
    }


    public void move() {
        hit();
        x += vx / Controller.ticksPerSecond;
        y += vy / Controller.ticksPerSecond;
    }

    public void hit() {
        String wall = Controller.getInstance().hitFrame(x, y, width, height);
        boolean hitObstacle;
        if(!wall.equals("None")){
            Controller.getInstance().getStatistics().getMagicalHexList().remove(this);
        }else {
            Obstacle crashingObstacle;
            for (Obstacle obstacle : Controller.getInstance().getStatistics().getObstacleList()) {
                hitObstacle = this.compareCoordinates(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
                if (hitObstacle) {
                    crashingObstacle = obstacle;
                    if(!crashingObstacle.isFrozen()){
                        crashingObstacle.setNumberOfHits(crashingObstacle.getNumberOfHits() - 1);
                    }
                    Controller.getInstance().getStatistics().getMagicalHexList().remove(this);
                    break;
                }
            }
        }
    }
}
