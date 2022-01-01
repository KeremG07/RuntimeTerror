package domain.body.obstacle;


public class HollowPurpleObs extends Obstacle {
    private int activationTime = 450;
    public HollowPurpleObs(double x_coordinates,
                           double y_coordinates,
                           double width,
                           double height,
                           int numberOfHits) {
        super(x_coordinates, y_coordinates, width, height, numberOfHits);
        vx = 0;
        this.moving = false;
        name = "Hollow";
    }

    public int getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(int activationTime) {
        this.activationTime = activationTime;
    }

    @Override
    public void move() {

    }

    @Override
    public void doWhenDestroyed() {
        //Do nothing because does not contribute to the score.
    }
}
