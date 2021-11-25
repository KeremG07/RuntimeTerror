package domain.body;

import domain.needForSpear.Controller;

public class EnchantedSphere extends Body {
    public boolean unstoppable;

    public EnchantedSphere(double x_coordinates,
                           double y_coordinates,
                           double length,
                           double width,
                           double vx,
                           double vy) {
        super(x_coordinates, y_coordinates, length, width, vx, vy);
        unstoppable=false;
    }


    public void move() {

    }

    public void reflect() {

    }
    
    public void shootEnchantedSphere(){
        
    }
    
    public void unstoppableES(){
        
    }
}
