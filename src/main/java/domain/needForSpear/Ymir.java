package domain.needForSpear;

import domain.ymirAbility.*;

import java.util.concurrent.ThreadLocalRandom;

public class Ymir {

    private boolean active;    // an ability is active or not
    private int cooldown;    // cooldown of 30 sec. before another coin toss
    private YmirAbility activeAbility; // InfiniteVoid, DoubleAccel, HollowPurple
    private int ticksPerSecond = Controller.ticksPerSecond;

    public Ymir() {
        active = false;
        cooldown = 30 * ticksPerSecond;
    }

    public void lifecycle() {
        if (cooldown == 0) {
            setActive(false);
            tossCoinAndSetAbility();
            cooldown = 30 * ticksPerSecond;
        } else {
            cooldown--;
            if (cooldown == 15 * ticksPerSecond) {
                setActive(false);
                if (activeAbility != null) {
                    activeAbility.endDuration();
                }
                activeAbility = null;
            }
        }
    }

    public void tossCoinAndSetAbility() {
        int result = ThreadLocalRandom.current().nextInt(0, 2);
        if (result == 0) {
            setActive(true);
            result = ThreadLocalRandom.current().nextInt(0, 3);
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

    public YmirAbility getCurrentAbility() {
        return this.activeAbility;
    }

    public void setActiveAbility(YmirAbility activeAbility) {
        this.activeAbility = activeAbility;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
