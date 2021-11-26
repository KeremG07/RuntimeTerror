package domain.body;

import ui.swing.PlayModeFrame;

public class NoblePhantasm extends Body {

    public double normalAngle;
    public boolean hasMagicalHex;

    public NoblePhantasm(double x_coordinates,
                         double y_coordinates,
                         double length,
                         double width,
                         double vx,
                         double vy){
        super(x_coordinates, y_coordinates, length, width,vx,vy);
        normalAngle=90;
        hasMagicalHex =false;
    }




    public void updateX(double x) {
        if(x < 0) x = 0;
        if(x > PlayModeFrame.getInstance().getWidth()) x = PlayModeFrame.getInstance().getWidth();
        this.x = x;
    }

    public void moveRight() { updateX(x + 0.1);
    }

    public void moveLeft() { updateX(x - 0.1);
    }

    public void slideRight() { updateX(x + 0.2);
    }

    public void slideLeft() {
        updateX(x - 0.2);
    }

    public void rotateRight() {
        normalAngle -= 0.1;
        if(normalAngle < 45) normalAngle = 45;
    }

    public void rotateLeft() {
        normalAngle += 0.1;
        if(normalAngle > 135) normalAngle = 135;
    }
    
    public void activateMagicalHex(){
        
    }
    
    public void doubleNP(){
        
    }
    
    public void moveNoblePhantasm(int input){
        if(input == 37) {
            moveLeft();
        }
        else if(input == 39) {
            moveRight();
        }
    }
}
