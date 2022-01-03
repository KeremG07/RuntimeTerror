package domain.ymirAbility;

public class YmirAbility {

    private boolean activated;
    protected int duration;
    protected int timeLeft;
    protected String name;

    public YmirAbility() {
        activated = false;
        duration = 15;  //in seconds
        timeLeft = duration;    //in seconds
    }

    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }

    public int getDuration() { return duration; }

    public int getTimeLeft() { return timeLeft; }

    public String getName() {
        return name;
    }

}
