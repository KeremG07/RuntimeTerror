package domain.body;

public class MagicalHex extends Body {

    public MagicalHex(double x_coordinates,
                      double y_coordinates,
                      double length,
                      double width,
                      double vx,
                      double vy) {
        super(x_coordinates, y_coordinates, length, width, vx, vy);
    }


    public void updateLocation() {

    }

    public boolean compareFrame() {
        return false;
    }

    public void move() {

    }

    public void reflect() {

    }
}
