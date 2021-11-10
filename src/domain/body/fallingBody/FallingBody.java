package domain.body.fallingBody;

import domain.body.Body;

public abstract class FallingBody extends Body {


    public FallingBody(double x_coordinates,
                       double y_coordinates,
                       double length,
                       double width,
                       double vx,
                       double vy) {
        super(x_coordinates, y_coordinates, length, width, vx, vy);
    }
    public abstract void fall();

}
