package domain.body.fallingBody;

import domain.body.NoblePhantasm;

public class Gift extends FallingBody{
    private String giftName;
    private int duration;
    public Gift(double x_coordinates,
                double y_coordinates,
                double width,
                double height,
                NoblePhantasm np,
                String giftName,
                int duration) {
        super(x_coordinates, y_coordinates, width, height, np);
        this.giftName=giftName;
        this.duration=duration;
    }
}
