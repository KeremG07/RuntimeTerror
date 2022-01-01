package domain.body;

import domain.ability.Ability;
import domain.ability.DoubleAccel;
import domain.ability.HollowPurple;
import domain.ability.InfiniteVoid;

import java.util.concurrent.ThreadLocalRandom;

public class Ymir extends Body {

    private boolean active;    // an ability is active or not
    private int cooldown;    // cooldown of 30 sec. before another coin toss
    private Ability activeAbility; // InfiniteVoid, DoubleAccel, HollowPurple

    public Ymir(double x_coordinates,
                double y_coordinates,
                double width,
                double height) {
        super(x_coordinates, y_coordinates, width, height);
        active = false;
        cooldown = 30;
    }

    public void lifecycle() {
        if(cooldown == 0) {
            tossCoinAndSetAbility();
            cooldown = 30;
        } else {
            cooldown--;
            if(cooldown == 15){
                activeAbility = null;
                setActive(false);
            }
        }
    }

    public void tossCoinAndSetAbility() {
        assert (!active);
        int result = ThreadLocalRandom.current().nextInt(0,2);
        if(result == 0) {
            setActive(true);
            result = ThreadLocalRandom.current().nextInt(0,3);
            switch (result) {
                case 0:
                    activeAbility = new DoubleAccel();
                    break;
                case 1:
                    activeAbility = new HollowPurple();
                    break;
                case 2:
                    activeAbility = new InfiniteVoid();
                    break;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Ability getCurrentAbility() { return this.activeAbility; }

}
