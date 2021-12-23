package tests;
import static org.junit.jupiter.api.Assertions.*;

import domain.body.BodyFactory;
import domain.body.obstacle.*;
import org.junit.jupiter.api.Test;

public class ExplosiveObstacleTest {

    @Test
    //Makes sure that the circle which the explosive obstacle does circular motion around moves with explosive obstacle.
    public void checkCircleCenter() {
        double firstX = 50;
        double firstY = 50;
        ExplosiveObstacle EO = (ExplosiveObstacle)BodyFactory.createObstacle("Explosive",firstX,firstY,1);
        double firstCircleX = EO.getCircleCenterX();
        double firstCircleY = EO.getCircleCenterY();
        double postX = 100;
        double postY = 100;
        EO.setCoordinates(postX,postY);
        double postCircleX = EO.getCircleCenterX();
        double postCircleY = EO.getCircleCenterY();
        assertTrue((firstCircleX-firstX)==(postCircleX-postX));
        assertTrue((firstCircleY-firstY)==(postCircleY-postY));
    }

}

