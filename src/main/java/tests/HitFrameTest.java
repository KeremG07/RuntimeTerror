package tests;
import static org.junit.jupiter.api.Assertions.*;

import domain.body.obstacle.Obstacle;
import org.junit.jupiter.api.Test;
import domain.needForSpear.*;
import domain.body.*;

public class HitFrameTest {

    @Test
    public void checkRightWall() {
        EnchantedSphere es = new EnchantedSphere(988,200,12,12,
                new NoblePhantasm(450,570,100,8));
        assertSame(Controller.getInstance().hitFrame(es.getCoordinates()[0], es.getCoordinates()[1], es.width, es.height), "Right");
    }

    @Test
    public void checkLeftWall() {
        EnchantedSphere es = new EnchantedSphere(0,200,12,12,
                new NoblePhantasm(450,570,100,8));
        assertSame(Controller.getInstance().hitFrame(es.getCoordinates()[0], es.getCoordinates()[1], es.width, es.height), "Left");
    }

    @Test
    public void checkUpperRightWall() {
        EnchantedSphere es = new EnchantedSphere(988,0,12,12,
                new NoblePhantasm(450,570,100,8));
        assertSame(Controller.getInstance().hitFrame(es.getCoordinates()[0], es.getCoordinates()[1], es.width, es.height), "UpperRight");
    }

    @Test
    public void checkUpperLeftWall() {
        EnchantedSphere es = new EnchantedSphere(0,0,12,12,
                new NoblePhantasm(450,570,100,8));
        assertSame(Controller.getInstance().hitFrame(es.getCoordinates()[0], es.getCoordinates()[1], es.width, es.height), "UpperLeft");
    }

    @Test
    public void checkFall() {
        EnchantedSphere es = new EnchantedSphere(100,588,12,12,
                new NoblePhantasm(450,570,100,8));
        assertSame(Controller.getInstance().hitFrame(es.getCoordinates()[0], es.getCoordinates()[1], es.width, es.height), "Down");
    }
}
