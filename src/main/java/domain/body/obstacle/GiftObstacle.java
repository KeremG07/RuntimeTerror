package domain.body.obstacle;

import domain.body.fallingBody.Gift;
import domain.body.fallingBody.Remains;
import domain.needForSpear.Statistics;

public class GiftObstacle extends Obstacle{
    private String giftType;
    public GiftObstacle(double x_coordinates,
                        double y_coordinates,
                        double width,
                        double height,
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

    @Override
    public void doWhenDestroyed() {
        createGift();
        //NewScore = OldScore + 300/(CurrentTime-GameStartingTime)
    }

    public void createGift(){
        Statistics.addGift(new Gift(this.getCoordinates()[0]+40, this.getCoordinates()[1]+4, 32, 32, this.giftType, 30));
    }
}
