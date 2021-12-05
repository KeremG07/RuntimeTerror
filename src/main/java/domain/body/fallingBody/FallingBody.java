package domain.body.fallingBody;

import domain.body.Body;

public abstract class FallingBody extends Body {


    public FallingBody(int x_coordinates,
                       int y_coordinates,
                       int length,
                       int width) {
        super(x_coordinates, y_coordinates, length, width);
    }
    public abstract void fall();

}
