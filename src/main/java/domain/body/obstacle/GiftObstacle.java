package domain.body.obstacle;

import domain.body.fallingBody.Gift;
import domain.body.fallingBody.Remains;
import domain.needForSpear.Controller;
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
        double oldScore = Controller.getInstance().getStatistics().getScore();
        long timeElapsed = Controller.getInstance().getStatistics().getTimeElapsed();
        Controller.getInstance().getStatistics().setScore(oldScore + (300/timeElapsed));
        createGift();
    }

    public void createGift(){
        Controller.getInstance().getStatistics().addGift(new Gift(this.getCoordinates()[0]+40, this.getCoordinates()[1]+4, 32, 32, Controller.getInstance().getPlayer().getNoblePhantasm(), this.giftType, 30));
    }
}
