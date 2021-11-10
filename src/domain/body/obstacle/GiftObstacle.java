package domain.body.obstacle;

public class GiftObstacle extends Obstacle{
    public String giftType;

    public GiftObstacle(double x_coordinates,
                        double y_coordinates,
                        double length,
                        double width,
                        double vx,
                        double vy,
                        String name,
                        boolean moving,
                        double movingProbability,
                        int numberOfHits,
                        String giftType) {
        super(x_coordinates, y_coordinates, length, width, vx, vy, name, moving, movingProbability, numberOfHits);
        this.giftType=giftType;
    }

    @Override
    public void move() {

    }
    public void createGift(){

    }
}
