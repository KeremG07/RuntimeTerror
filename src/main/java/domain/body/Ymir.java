package domain.body;

import domain.ability.Ability;
import domain.ability.DoubleAccel;
import domain.ability.HollowPurple;
import domain.ability.InfiniteVoid;

import java.util.concurrent.ThreadLocalRandom;

public class Ymir extends Body {

    public String state;    // active, passive
    public int cooldown;    // cooldown of 30 sec. before another coin toss
    public Ability currentAbility;  // InfiniteVoid, DoubleAccel, HollowPurple

    // Get Instances
    public DoubleAccel doubleAccel = DoubleAccel.getInstance();
    public HollowPurple hollowPurple = HollowPurple.getInstance();
    public InfiniteVoid infiniteVoid = InfiniteVoid.getInstance();

    public Ymir(double x_coordinates,
                double y_coordinates,
                double width,
                double height) {
        super(x_coordinates, y_coordinates, width, height);
        state = "passive";
        currentAbility = null;
        cooldown = 30;
    }

    public void lifecycle() {
        if(cooldown == 0) {
            tossCoinAndSetAbility();
            cooldown = 30;
        } else {
            cooldown--;
        }
    }

    public void tossCoinAndSetAbility() {
        int result = ThreadLocalRandom.current().nextInt(0,2);
        if(result == 0 && cooldown == 0 && getState().equals("passive")) {
            setState("active");
            result = ThreadLocalRandom.current().nextInt(0,3);
            switch (result) {
                case 0:
                    setAbility(doubleAccel);
                    break;
                case 1:
                    setAbility(hollowPurple);
                    break;
                case 2:
                    setAbility(infiniteVoid);
                    break;
            }
        } else {
            setState("passive");
        }
    }

    public void setState(String state) { this.state = state; }
    public String getState() { return this.state; }

    public void setAbility(Ability ability) { this.currentAbility = ability; }
    public Ability getAbility() { return this.currentAbility; }

}
