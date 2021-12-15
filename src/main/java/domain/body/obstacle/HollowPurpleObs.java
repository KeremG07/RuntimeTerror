package domain.body.obstacle;


public class HollowPurpleObs extends Obstacle{
    public HollowPurpleObs(int x_coordinates,
                           int y_coordinates,
                           int width,
                           int height,
                           int numberOfHits) {
        super(x_coordinates, y_coordinates, width, height, numberOfHits);
        vx = 0;
        this.moving = false;
        name = "Hollow";
    }

    @Override
    public void move() {

    }

    @Override
    public void doWhenDestroyed() {
        //Do nothing because does not contribute to the score.
    }
}
