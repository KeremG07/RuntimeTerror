package tests;
import static org.junit.jupiter.api.Assertions.*;

import domain.body.obstacle.Obstacle;
import org.junit.jupiter.api.Test;
import domain.needForSpear.*;

public class BuildGameTest {

    @Test
    //Makes sure the total count of each obstacle is less than the upper bound.
    public void checkUpperBoundObstacle(){
        String[] numOfObstacles = new String[]{"100", "10000", "4500", "6500"};
        BuildGame bg = new BuildGame(numOfObstacles);
        assertTrue(bg.getSimpleObstacleNum() + bg.getFirmObstacleNum() + bg.getExplosiveObstacleNum() + bg.getGiftObstacleNum() <= 100);
    }

    @Test
    //Makes sure the count of each obstacle is more than or equal to their minimum requirements.
    public void checkLowerBoundObstacle(){
        String[] numOfObstacles = new String[]{"0", "8", "2", "-70"};
        BuildGame bg = new BuildGame(numOfObstacles);
        assertTrue(bg.getSimpleObstacleNum() >= bg.simpleObstacleReq);
        assertTrue(bg.getFirmObstacleNum() >= bg.firmObstacleReq);
        assertTrue(bg.getExplosiveObstacleNum() >= bg.explosiveObstacleReq);
        assertTrue(bg.getGiftObstacleNum() >= bg.giftObstacleReq);
    }

    @Test
    //Makes sure the correct number of obstacles are created according to the user input.
    public void checkCountObstaclesCreated(){
        String[] numOfObstacles = new String[]{"75", "10", "5", "10"};
        BuildGame bg = new BuildGame(numOfObstacles);
        int countSimple = 0, countFirm = 0, countExplosive = 0, countGift = 0;
        for(Obstacle obstacle : Controller.getInstance().getStatistics().getObstacleList()){
            if(obstacle.getName().equals("Simple")){
                countSimple++;
            } else if(obstacle.getName().equals("Firm")){
                countFirm++;
            } else if(obstacle.getName().equals("Explosive")){
                countExplosive++;
            } else if(obstacle.getName().equals("Gift")){
                countGift++;
            }
        }
        assertTrue(countSimple == bg.getSimpleObstacleNum());
        assertTrue(countFirm == bg.getFirmObstacleNum());
        assertTrue(countExplosive == bg.getExplosiveObstacleNum());
        assertTrue(countGift == bg.getGiftObstacleNum());
    }

    @Test
    //Makes sure obstacles do not collide.
    public void checkObstacleCollision(){
        String[] numOfObstacles = new String[]{"75", "10", "5", "10"};
        BuildGame bg = new BuildGame(numOfObstacles);
        for(Obstacle obstacle1 : Controller.getInstance().getStatistics().getObstacleList()){
            for(Obstacle obstacle2 : Controller.getInstance().getStatistics().getObstacleList()){
                if(!obstacle1.equals(obstacle2)){
                    assertFalse(obstacle1.compareCoordinates(obstacle2.getCoordinates()[0], obstacle2.getCoordinates()[1],
                            obstacle2.getWidth(), obstacle2.getHeight()));
                }
            }
        }
    }

    @Test
    //Makes sure the firm obstacles have the correct number of hitNum attribute.
    public void checkFirmObstacleHitNum(){
        String[] numOfObstacles = new String[]{"75", "10", "5", "10"};
        BuildGame bg = new BuildGame(numOfObstacles);
        for(Obstacle obstacle : Controller.getInstance().getStatistics().getObstacleList()){
            if(obstacle.getName().equals("Firm")){
                int hitNum = obstacle.getNumberOfHits();
                assertTrue(3 <= hitNum && 5 >= hitNum);
            }
        }
    }

}
