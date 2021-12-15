package domain.body.fallingBody;

public class Gift extends FallingBody{
    public String giftName;
    public int duration;

    public Gift(int x_coordinates,
                int y_coordinates,
                int length,
                int width,
                String giftName,
                int duration) {
        super(x_coordinates, y_coordinates, length, width);
        this.giftName=giftName;
        this.duration=duration;
    }
}
