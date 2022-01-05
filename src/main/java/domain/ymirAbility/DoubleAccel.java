package domain.ymirAbility;

import domain.body.EnchantedSphere;
import domain.needForSpear.Controller;

public class DoubleAccel extends YmirAbility {
    private EnchantedSphere sphere = Controller.getInstance().getPlayer().getEnchantedSphere();
    public DoubleAccel() {
        super();
        name = "Double Accel";
        slowDownEnchantedSphere();
    }

    public void slowDownEnchantedSphere(){
        double newVx = sphere.getVx()/2;
        double newVy = sphere.getVy()/2;
        sphere.setVx(newVx);
        sphere.setVy(newVy);
    }

    @Override
    public void endDuration(){
        double newVx = sphere.getVx()*2;
        double newVy = sphere.getVy()*2;
        sphere.setVx(newVx);
        sphere.setVy(newVy);
    }
}
