package domain.ymirAbility;

public abstract class YmirAbility {

    protected int duration;
    protected int timeLeft;
    protected String name;

    public YmirAbility() {
        duration = 15;  //in seconds
        timeLeft = duration;    //in seconds
    }

    public String getName() {
        return name;
    }
    public abstract void endDuration();
}
