package domain.body;

import domain.needForSpear.*;

public class NoblePhantasm extends Body {

    public double normalAngle;
    public boolean hasMagicalHex;
    public int speedMultiplier;

    public NoblePhantasm(int x_coordinates,
                         int y_coordinates,
                         int width,
                         int height){
        super(x_coordinates, y_coordinates, width, height);
        normalAngle=0;
        hasMagicalHex = false;
        speedMultiplier = 3;
    }

    public void updateX(int x) {
        String frame = Controller.getInstance().hitFrame(x, this.y, this.width, this.height);
        if(frame.equals("None")){
            this.x = x;
        }
    }

    //This is called ticksPerSecond times per second.
    public void moveRight() { updateX(x + (speedMultiplier*width / Controller.ticksPerSecond)); }

    public void moveLeft() { updateX(x - (speedMultiplier*width / Controller.ticksPerSecond)); }

    public void slideRight() { updateX(x + (2*speedMultiplier*width / Controller.ticksPerSecond)); }

    public void slideLeft() {
        updateX(x - (2*speedMultiplier*width / Controller.ticksPerSecond));
    }

    public void rotateLeft() {
        normalAngle -= 60.0 / Controller.ticksPerSecond;
        if(normalAngle < -45) normalAngle = -45;
    }

    public void rotateRight() {
        normalAngle += 60.0 / Controller.ticksPerSecond;
        if(normalAngle > 45) normalAngle = 45;
    }
    
    public void activateMagicalHex(){
        
    }
    
    public void doubleNP(){
        
    }

    public double getNormalAngle() {
        return normalAngle;
    }

    public void setNormalAngle(double normalAngle) {
        this.normalAngle = normalAngle;
    }

    public void doubleSpeed() {
        speedMultiplier = speedMultiplier*2;
    }

    public void halveSpeed() { speedMultiplier = speedMultiplier/2; }
}
