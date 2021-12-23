package tests;
import static org.junit.jupiter.api.Assertions.*;

import domain.body.NoblePhantasm;
import domain.body.obstacle.SimpleObstacle;
import domain.needForSpear.Statistics;
import org.junit.jupiter.api.Test;
import domain.body.EnchantedSphere;

public class ReflectTest {

    @Test
    //Testing if es reflects from left wall correctly.
    public void reflectFromLeftWall(){
        EnchantedSphere es = new EnchantedSphere(0,200,12,12,
                new NoblePhantasm(450,570,100,8));
        es.setVx(30);
        double prevVx = es.getVx();
        es.reflect();
        assertTrue(es.getVx() == -prevVx);
    }

    @Test
    //Testing if es reflects from upper wall correctly
    public void reflectFromUpperWall(){
        EnchantedSphere es = new EnchantedSphere(100,0,12,12,
                new NoblePhantasm(450,570,100,8));
        es.setVy(-20);
        double prevVy = es.getVy();
        es.reflect();
        assertTrue(es.getVy() == -prevVy);
    }

    @Test
    //Testing if es reflects from rotated NP (-45 degrees specifically) correctly.
    public void reflectFromRotatedNP(){
        NoblePhantasm np = new NoblePhantasm(450,570,100,8);
        EnchantedSphere es = new EnchantedSphere(494,558,12,12, np);
        es.setVx(0);
        es.setVy(30);
        double prevVy = es.getVy();
        for(int i=0; i<25; i++){
            np.rotateLeft();
        }
        es.reflect();
        assertTrue(es.getVx() == -prevVy && es.getVy() == 0);
    }

    @Test
    //Testing if es reflects from the corner of game screen correctly.
    public void reflectFromCorner(){
        EnchantedSphere es = new EnchantedSphere(0,0,12,12,
                new NoblePhantasm(450,570,100,8));
        es.setVx(-30);
        es.setVy(-30);
        double prevVx = es.getVx();
        double prevVy = es.getVy();
        es.reflect();
        assertTrue(es.getVx() == -prevVx && es.getVy() == -prevVy);
    }

    @Test
    //Testing if es reflects from an obstacle correctly.
    public void reflectFromObstacle(){
        EnchantedSphere es = new EnchantedSphere(100,92,12,12,
                new NoblePhantasm(450,570,100,8));
        Statistics.addObstacle(new SimpleObstacle(100,100,80,8,1));
        es.setVx(-30);
        es.setVy(-30);
        double prevVy = es.getVy();
        es.reflect();
        assertTrue(es.getVy() == -prevVy);
    }

}
