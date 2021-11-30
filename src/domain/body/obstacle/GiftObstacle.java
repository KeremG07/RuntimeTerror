package domain.body.obstacle;

public class GiftObstacle extends Obstacle{
    public String giftType;

    public GiftObstacle(double x_coordinates,
                        double y_coordinates,
                        double length,
                        double width,
                        String name,
                        int numberOfHits,
                        String giftType) {
        super(x_coordinates, y_coordinates, length, width, name, numberOfHits);
        this.giftType=giftType;
        this.moving = false;
    }

    @Override
    public void move() {

    }
    public void createGift(){

    }
}
