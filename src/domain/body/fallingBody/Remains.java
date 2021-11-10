package domain.body.fallingBody;

public class Remains extends FallingBody {

    int remainNumber;

    public Remains(double x_coordinates,
                   double y_coordinates,
                   double length,
                   double width,
                   double vx,
                   double vy,
                   int remainNumber) {
        super(x_coordinates, y_coordinates, length, width, vx, vy);
        this.remainNumber=remainNumber;
    }

    @Override
    public void fall() {

    }
    public void loseChance(){

    }
}
