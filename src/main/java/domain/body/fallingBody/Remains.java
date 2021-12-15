package domain.body.fallingBody;

public class Remains extends FallingBody {

    int remainNumber;

    public Remains(int x_coordinates,
                   int y_coordinates,
                   int length,
                   int width,
                   int remainNumber) {
        super(x_coordinates, y_coordinates, length, width);
        this.remainNumber=remainNumber;
    }
}
