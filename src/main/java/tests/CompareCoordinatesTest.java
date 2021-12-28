package tests;
import static org.junit.jupiter.api.Assertions.*;

import domain.body.Body;
import org.junit.jupiter.api.Test;

public class CompareCoordinatesTest {

    @Test
    // Makes sure compareCoordinates works for objects colliding at the primary object's Top Left
    public void compareFromTopLeft() {
        Body body1 = new Body(100, 100, 100, 200);
        Body body2 = new Body(50, 50, 100, 200);

        assertTrue(body1.compareCoordinates(body2.getCoordinates()[0],
                body2.getCoordinates()[1],
                body2.getWidth(),
                body2.getHeight()));
    }

    @Test
    // Makes sure compareCoordinates works for objects colliding at the primary object's Bottom Left
    public void compareFromBottomLeft() {
        Body body1 = new Body(100, 100, 100, 200);
        Body body2 = new Body(50, 150, 100, 200);

        assertTrue(body1.compareCoordinates(body2.getCoordinates()[0],
                body2.getCoordinates()[1],
                body2.getWidth(),
                body2.getHeight()));
    }

    @Test
    // Makes sure compareCoordinates works for objects colliding at the primary object's Bottom Right
    public void compareFromBottomRight() {
        Body body1 = new Body(100, 100, 100, 200);
        Body body2 = new Body(150, 150, 100, 200);

        assertTrue(body1.compareCoordinates(body2.getCoordinates()[0],
                body2.getCoordinates()[1],
                body2.getWidth(),
                body2.getHeight()));
    }

    @Test
    // Makes sure compareCoordinates works for objects colliding at the primary object's Top Right
    public void compareFromTopRight() {
        Body body1 = new Body(100, 100, 100, 200);
        Body body2 = new Body(150, 50, 100, 200);

        assertTrue(body1.compareCoordinates(body2.getCoordinates()[0],
                body2.getCoordinates()[1],
                body2.getWidth(),
                body2.getHeight()));
    }

    @Test
    // Makes sure compareCoordinates works for objects colliding at the primary object's Top
    public void compareFromTop() {
        Body body1 = new Body(100, 100, 100, 200);
        Body body2 = new Body(100, 150, 100, 200);

        assertTrue(body1.compareCoordinates(body2.getCoordinates()[0],
                body2.getCoordinates()[1],
                body2.getWidth(),
                body2.getHeight()));
    }
}
