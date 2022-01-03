package domain.ymirAbility;
import domain.body.*;
import domain.body.obstacle.Obstacle;
import domain.needForSpear.Controller;

import java.util.Random;

public class HollowPurple extends YmirAbility {
    public final double hollowPurpleNum = 8;
    private Random randi = new Random();

    public HollowPurple() {
        super();
        name = "Hollow Purple";
        for(int i=0; i<hollowPurpleNum; i++){
            addHollowPurpleObs();
        }
    }

    public void addHollowPurpleObs() {
        boolean notOverlaps = true;
        int x;
        int y;
        while (true) {
            x = randi.nextInt(900);
            y = randi.nextInt(392);
            for (Obstacle o: Controller.getInstance().getStatistics().getObstacleList()) {
                if (o.compareCoordinates(x,y, 100, 8)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                Controller.getInstance().getStatistics().addObstacle(BodyFactory.createObstacle("Hollow",x,y,1));
                return;
            }
        }
    }
}
