package domain.body;

public class NoblePhantasm extends Body {

    public double normalAngle;
    public boolean magicalHex;

    public NoblePhantasm(double x_coordinates,
                         double y_coordinates,
                         double length,
                         double width,
                         double vx,
                         double vy){
        super(x_coordinates, y_coordinates, length, width,vx,vy);
        normalAngle=90;
        magicalHex=false;
    }


    public void updateLocation(double x) {
        this.x = x;
    }
    

    public void moveRight() {
        updateLocation(x + length/2);
    }

    public void moveLeft() {
        updateLocation(x - length/2);
    }

    public void slideRight() {
        updateLocation(x + 2*length);
    }

    public void slideLeft() {
        updateLocation(x - 2*length);
    }

    public void rotateRight() {
        normalAngle -= 20;
        if(normalAngle < 45) normalAngle = 45;
    }

    public void rotateLeft() {
        normalAngle += 20;
        if(normalAngle > 135) normalAngle = 135;
    }
    
    public void updateRotation(){
        
    }
    
    public void activateMagicalHex(){
        
    }
    
    public void doubleNP(){
        
    }
    
    public void moveNoblePhantasm(){
        
    }
}
