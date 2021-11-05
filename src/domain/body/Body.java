package domain.body;

public class Body {
    public double x;
    public double y;
    public int length;
    public int width;
    public Body (double x_coordinates,
                 double y_coordinates,
                 int length,
                 int width){
        x = x_coordinates;
        y = y_coordinates;
        this.length = length;
        this.width = width;
    }
}
