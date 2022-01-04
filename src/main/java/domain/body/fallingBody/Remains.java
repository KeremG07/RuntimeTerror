package domain.body.fallingBody;

import domain.body.NoblePhantasm;

public class Remains extends FallingBody {

    private int remainNumber;
    public Remains(double x_coordinates,
                   double y_coordinates,
                   double width,
                   double height,
                   NoblePhantasm np,
                   int remainNumber) {
        super(x_coordinates, y_coordinates, width, height, np);
        this.remainNumber=remainNumber;
    }
}
