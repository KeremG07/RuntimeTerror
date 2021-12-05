package domain.body.obstacle;

public class GiftObstacle extends Obstacle{
    public String giftType;
    public GiftObstacle(int x_coordinates,
                        int y_coordinates,
                        int width,
                        int height,
                        int numberOfHits,
                        String giftType) {
        super(x_coordinates, y_coordinates, width, height, numberOfHits);
        this.giftType=giftType;
        this.moving = false;
        name = "Gift";
        vx = 0;
    }
    @Override
    public void move() {

    }
    public void createGift(){

    }
}
