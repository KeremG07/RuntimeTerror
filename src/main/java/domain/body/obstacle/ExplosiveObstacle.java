package domain.body.obstacle;
import domain.body.fallingBody.Remains;
import domain.needForSpear.*;

import java.util.Random;

public class ExplosiveObstacle extends Obstacle {
// OVERVIEW: ExplosiveObstacles are circular obstacles that move in circular orbits and explode when hit.
    //The coordinates and the radius of the circle that the obstacle will move around.
    private final double circleRadius = height/2;
    private double circleCenterX = this.x;
    private double circleCenterY = this.y;
    //The degree between the circle's center and the obstacle.
    private double degree = 90;
    private boolean movesRight;
    // Abstraction function:
    // represents an explosive obstacle at (x,y) with corresponding size values
    // Rep invariant:
    // x>=0,
    // y>=0,
    // width==32,
    // height==32,
    // name=="Explosive"
    public ExplosiveObstacle(double x_coordinates,
                             double y_coordinates,
                             double width,
                             double height,
                             int numberOfHits) {
        super(x_coordinates, y_coordinates, width, height, numberOfHits);
        name = "Explosive";
        vx = 100/(4*Controller.ticksPerSecond);
        Random rand = new Random();
        movesRight = rand.nextBoolean();
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
    public double getCircleRadius() {
        return circleRadius;
    }

    public double getCircleCenterX() {
        return circleCenterX;
    }

    public double getCircleCenterY() {
        return circleCenterY;
    }

    public void setMovesRight(boolean bool) {
        movesRight = bool;
    }

    public boolean getMovesRight() {
        return movesRight;
    }

    @Override
    public void move() {
        //REQUIRES: moving == true
        //MODIFIES: x, y, degree, movesRight
        //EFFECTS: If the obstacle is a moving obstacle, updates its attributes to reflect its rotating
        //displacement based on whether it's blocked or not from its left and right
        if(moving){
            double radsR = Math.toRadians(degree + 2 + 90); // 0 becomes the top.
            int newXR = Math.round((float) (circleCenterX + Math.cos(radsR) * circleRadius));
            int newYR = Math.round((float) (circleCenterY + Math.sin(radsR) * circleRadius));

            double radsL = Math.toRadians(degree - 2 + 90);// 0 becomes the top.
            int newXL = Math.round((float) (circleCenterX + Math.cos(radsL) * circleRadius));
            int newYL = Math.round((float) (circleCenterY + Math.sin(radsL) * circleRadius));

            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            //Compares with every obstacle.
            for (Obstacle obstacle : Statistics.getObstacleList()) {
                //Doesn't check whether it crashes with itself.
                if (!(obstacle.getCoordinates()[0] == this.x && obstacle.getCoordinates()[1] == this.y)) {
                    canMoveRight &= !(obstacle.compareCoordinates(newXR, newYR, this.width, this.height));
                    canMoveLeft &= !(obstacle.compareCoordinates(newXL, newYL, this.width, this.height));
                }
            }
            //Compares with every frame border.
            if (!Controller.getInstance().hitFrame(newXR, newYR, this.width, this.height).equals("None")) {
                canMoveRight = false;
            }
            if (!Controller.getInstance().hitFrame(newXL, newYL, this.width, this.height).equals("None")) {
                canMoveLeft = false;
            }
            // It moves right if it can, if not left.
            if (movesRight) {
                if (canMoveRight) {
                    this.x = newXR;
                    this.y = newYR;
                    degree += 2;
                } else if (canMoveLeft) {
                    this.x = newXL;
                    this.y = newYL;
                    degree -= 2;
                    movesRight = false;
                }
            } else {
                if (canMoveLeft) {
                    this.x = newXL;
                    this.y = newYL;
                    degree -= 2;
                } else if (canMoveRight) {
                    this.x = newXR;
                    this.y = newYR;
                    degree += 2;
                    movesRight = true;
                }
            }

        }
    }

    @Override
    public void doWhenDestroyed() {
        explode();
        //NewScore = OldScore + 300/(CurrentTime-GameStartingTime)
    }

    public void explode() {
        Statistics.addRemains(new Remains(this.getCoordinates()[0]+16, this.getCoordinates()[1]+16, 32, 32, 1));
    }

    @Override
    public void setCoordinates(double x, double y) {
        //REQUIRES: x,y >=0
        //MODIFIES: circleCenterX, circleCenterY
        //EFFECTS: Whenever the explosive obstacle is moved (in build mode), its center which the obstacle
        //does circular motion around is moved accordingly.
        double offsetx = this.x - circleCenterX;
        double offsety = this.y - circleCenterY;
        this.x = x;
        this.y = y;
        circleCenterX = this.x - offsetx;
        circleCenterY = this.y - offsety;
    }

    public boolean repOk() {
        if (x < 0) return false;
        if (y < 0) return false;
        if (width != 32) return false;
        if (height != 32) return false;
        if (!name.equals("Explosive")) return false;
        return true;
    }
}
