package tests;

import domain.body.BodyFactory;
import domain.body.obstacle.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateObstacleTest {
    @Test
    //checks if the created obstacle is a simple obstacle.
    public void checkSimpleObstacleCreation(){
        String type = "Simple";
        int obsWidth = 80;
        int obsHeight = 8;
        double x= 10.0;
        double y= 20.0;
        int hitNum =1;
        Obstacle simpleObstacle = new SimpleObstacle(x,y,obsWidth,obsHeight,hitNum);
        Obstacle createdObstacle= BodyFactory.createObstacle(type,x,y,hitNum);
        assertTrue(simpleObstacle.getName().equals(createdObstacle.getName()));
    }
    @Test
    //checks if the created obstacle is a firm obstacle.
    public void checkFirmObstacleCreation(){
        String type = "Firm";
        int obsWidth = 80;
        int obsHeight = 8;
        double x= 10.0;
        double y= 20.0;
        int hitNum =3;
        Obstacle firmObstacle = new FirmObstacle(x,y,obsWidth,obsHeight,hitNum);
        Obstacle createdObstacle= BodyFactory.createObstacle(type,x,y,hitNum);
        assertTrue(firmObstacle.getName().equals(createdObstacle.getName()));
    }
    @Test
    //checks if the created obstacle is an explosive obstacle.
    public void checkExplosiveObstacleCreation(){
        String type = "Explosive";
        int obsWidth = 80;
        int obsHeight = 8;
        double x= 10.0;
        double y= 20.0;
        int hitNum =1;
        Obstacle explosiveObstacle = new ExplosiveObstacle(x,y,obsWidth,obsHeight,hitNum);
        Obstacle createdObstacle= BodyFactory.createObstacle(type,x,y,hitNum);
        assertTrue(explosiveObstacle.getName().equals(createdObstacle.getName()));
    }
    @Test
    //checks if the created obstacle is a gift obstacle.
    public void checkGiftObstacleCreation(){
        String type = "Gift";
        int obsWidth = 80;
        int obsHeight = 8;
        double x= 10.0;
        double y= 20.0;
        int hitNum =1;
        Obstacle giftObstacle = new GiftObstacle(x,y,obsWidth,obsHeight,hitNum);
        Obstacle createdObstacle= BodyFactory.createObstacle(type,x,y,hitNum);
        assertTrue(giftObstacle.getName().equals(createdObstacle.getName()));
    }
    @Test
    //checks if the created obstacle is a hollow purple obstacle.
    public void checkHollowPurpleObstacleCreation(){
        String type = "Hollow";
        int obsWidth = 80;
        int obsHeight = 8;
        double x= 10.0;
        double y= 20.0;
        int hitNum =1;
        Obstacle hollowPurpleObs = new HollowPurpleObs(x,y,obsWidth,obsHeight,hitNum);
        Obstacle createdObstacle= BodyFactory.createObstacle(type,x,y,hitNum);
        assertTrue(hollowPurpleObs.getName().equals(createdObstacle.getName()));
    }

}
