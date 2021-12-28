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
        assertTrue(EO.repOk());
        double firstCircleX = EO.getCircleCenterX();
        double firstCircleY = EO.getCircleCenterY();
        double postX = 100;
        double postY = 100;
        EO.setCoordinates(postX,postY);
        assertTrue(EO.repOk());
        double postCircleX = EO.getCircleCenterX();
        double postCircleY = EO.getCircleCenterY();
        assertTrue((firstCircleX-firstX)==(postCircleX-postX));
        assertTrue((firstCircleY-firstY)==(postCircleY-postY));
    }

    @Test
    public void checkRotateRightDegree() {
        ExplosiveObstacle EO = (ExplosiveObstacle)BodyFactory.createObstacle("Explosive",
                100, 100, 1);
        assertTrue(EO.repOk());
        EO.setMoving(true);
        assertTrue(EO.repOk());
        EO.setMovesRight(true);
        assertTrue(EO.repOk());
        EO.setDegree(90);
        assertTrue(EO.repOk());
        EO.move();
        assertTrue(EO.repOk());

        assertEquals(EO.getDegree(), 92);
    }

    @Test
    //checks if rotating left updates degree correctly.
    public void checkRotateLeftDegree() {
        ExplosiveObstacle EO = (ExplosiveObstacle)BodyFactory.createObstacle("Explosive",
                100, 100, 1);
        assertTrue(EO.repOk());
        EO.setMoving(true);
        assertTrue(EO.repOk());
        EO.setMovesRight(false);
        assertTrue(EO.repOk());
        EO.setDegree(90);
        assertTrue(EO.repOk());
        EO.move();
        assertTrue(EO.repOk());

        assertEquals(EO.getDegree(), 88);
    }
    
    @Test
    // checks if the x coordinate of the obstacle is updated correctly.
    public void  checkXCoordinate(){
        double initialX = 100;
        double initialY = 100;
        ExplosiveObstacle explosiveObstacle = (ExplosiveObstacle) BodyFactory.createObstacle("Explosive",initialX,initialY,1);
        assertTrue(explosiveObstacle.repOk());
        int degree = 90;
        explosiveObstacle.setDegree(degree);
        explosiveObstacle.setMoving(true);
        assertTrue(explosiveObstacle.repOk());
        explosiveObstacle.setMovesRight(true);
        assertTrue(explosiveObstacle.repOk());
        explosiveObstacle.move();
        assertTrue(explosiveObstacle.repOk());

        assertEquals(explosiveObstacle.getCoordinates()[0], 110);
    }

}

