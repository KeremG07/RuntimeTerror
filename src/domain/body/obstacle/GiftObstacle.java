package domain.body.obstacle;

public class GiftObstacle extends Obstacle{
    public String giftType;
    private String name;
    public GiftObstacle(int x_coordinates,
                        int y_coordinates,
                        int length,
                        int width,
                        int numberOfHits,
                        String giftType) {
        super(x_coordinates, y_coordinates, length, width, numberOfHits);
        this.giftType=giftType;
        this.moving = false;
        name = "Gift";
        vx = 0;
    }
    public String getName(){
        return name;
    }
    @Override
    public void move() {

    }
    public void createGift(){

    }
}
