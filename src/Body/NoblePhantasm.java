package Body;

public class NoblePhantasm extends Body {

    public double angle;

    public NoblePhantasm(double x_coordinates,
                         double y_coordinates,
                         int length,
                         int width){
        super(x_coordinates, y_coordinates, length, width);
    }

    public void updateLocation(double x) {
        this.x = x;
    }

    public void moveRight() {
        updateLocation(x + (double) length/2);
    }

    public void moveLeft() {
        updateLocation(x - (double) length/2);
    }

    public void slideRight() {
        updateLocation(x + 2*length);
    }

    public void slideLeft() {
        updateLocation(x - 2*length);
    }

    public void rotateRight() {
        angle -= 20;
        if(angle < 45) angle = 45;
    }

    public void rotateLeft() {
        angle += 20;
        if(angle > 135) angle = 135;
    }
}
