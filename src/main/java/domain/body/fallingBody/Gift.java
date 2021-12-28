package domain.body.fallingBody;

public class Gift extends FallingBody{
    private String giftName;
    private int duration;

    public Gift(double x_coordinates,
                double y_coordinates,
                double length,
                double width,
                String giftName,
                int duration) {
        super(x_coordinates, y_coordinates, length, width);
        this.giftName=giftName;
        this.duration=duration;
    }
}
