package domain.body;

import domain.needForSpear.*;

public class NoblePhantasm extends Body {

    public double normalAngle;
    public boolean hasMagicalHex;
    public int speedMultiplier;

    public NoblePhantasm(double x_coordinates,
                         double y_coordinates,
                         double width,
                         double height){
        super(x_coordinates, y_coordinates, width, height);
        normalAngle=0;
        hasMagicalHex = false;
        speedMultiplier = 3;
    }

    public void updateX(double x) {
        String frame = Controller.getInstance().hitFrame(x, this.y, this.width, this.height);
        if(frame.equals("None")){
            this.x = x;
        } else if (frame.equals("Right")){
            this.x = Controller.getInstance().getFrameBorders()[0] - this.width;
        } else {
            this.x = 0;
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
        double middleX = x + (width / 2) * Math.cos(Math.toRadians(normalAngle));
        double middleY = y + (width / 2) * Math.sin(Math.toRadians(normalAngle));
        normalAngle -= 60.0 / Controller.ticksPerSecond;
        if(normalAngle < -45) normalAngle = -45;
        x = middleX - (width/2) * Math.cos(Math.toRadians(normalAngle));
        y = middleY - (width/2) * Math.sin(Math.toRadians(normalAngle));
    }

    public void rotateRight() {
        double middleX = x + (width / 2) * Math.cos(Math.toRadians(normalAngle));
        double middleY = y + (width / 2) * Math.sin(Math.toRadians(normalAngle));
        normalAngle += 60.0 / Controller.ticksPerSecond;
        if(normalAngle > 45) normalAngle = 45;
        x = middleX - (width/2) * Math.cos(Math.toRadians(normalAngle));
        y = middleY - (width/2) * Math.sin(Math.toRadians(normalAngle));
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
