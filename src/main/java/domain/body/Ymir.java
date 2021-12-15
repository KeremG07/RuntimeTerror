package domain.body;

import domain.ability.Ability;

public class Ymir extends Body {

    public String state;    // active, passive
    public Ability currentAbility;  // InfiniteVoid, DoubleAccel, HollowPurple

    public Ymir(int x_coordinates,
                int y_coordinates,
                int width,
                int height) {
        super(x_coordinates, y_coordinates, width, height);
        this.state = "passive";
        this.currentAbility = null;
    }

    public void setState(String state) { this.state = state; }
    public String getState() { return this.state; }

    public void setAbility(Ability ability) { this.currentAbility = ability; }
    public Ability getAbility() { return this.currentAbility; }

}
