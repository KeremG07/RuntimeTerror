package domain.body.obstacle;

import domain.needForSpear.Controller;
import domain.needForSpear.Statistics;

import java.util.Random;

public class FirmObstacle extends Obstacle {

    private boolean movesRight;

    public FirmObstacle(double x_coordinates,
                        double y_coordinates,
                        double width,
                        double height,
                        int numberOfHits) {
        super(x_coordinates, y_coordinates, width, height, numberOfHits);
        //Speed is initialized as this because otherwise it is smaller than 1 and doesn't have an effect.
        vx = 1;
        name = "Firm";
        Random rand = new Random();
        movesRight = rand.nextBoolean();
    }

    @Override
    public void move() {
        if (moving) {
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            double speed = Math.abs(this.vx);
            //Compares with every obstacle.
            for (Obstacle obstacle : Controller.getInstance().getStatistics().getObstacleList()) {
                //Doesn't check whether it crashes with itself.
                if (!(obstacle.getCoordinates()[0] == x && obstacle.getCoordinates()[1] == y)) {
                    canMoveRight &= !(obstacle.compareCoordinates(this.x + speed, this.y, this.width, this.height));
                    canMoveLeft &= !(obstacle.compareCoordinates(this.x - speed, this.y, this.width, this.height));
                }
            }

            //Compares with every frame border.
            if (!Controller.getInstance().hitFrame(this.x + speed, this.y, this.width, this.height).equals("None")) {
                canMoveRight = false;

            }
            if (!Controller.getInstance().hitFrame(this.x - speed, this.y, this.width, this.height).equals("None")) {
                canMoveLeft = false;

            }
            // It moves right if it can, if not left.

            if (movesRight) {
                if (canMoveRight) {
                    if(this.vx < 0) this.vx = -this.vx;
                    this.x += this.vx;
                } else if (canMoveLeft) {
                    if(this.vx > 0) this.vx = -this.vx;
                    this.x += this.vx;
                    movesRight = false;
                }
            } else {
                if (canMoveLeft) {
                    if(this.vx > 0) this.vx = -this.vx;
                    this.x += this.vx;
                } else if (canMoveRight) {
                    if(this.vx < 0) this.vx = -this.vx;
                    this.x += this.vx;
                    movesRight = true;

                }


            }
        }
    }

    @Override
    public void doWhenDestroyed() {
        double oldScore = Controller.getInstance().getStatistics().getScore();
        long timeElapsed = Controller.getInstance().getStatistics().getTimeElapsed();
        Controller.getInstance().getStatistics().setScore(oldScore + (300.0/timeElapsed));
    }

}
