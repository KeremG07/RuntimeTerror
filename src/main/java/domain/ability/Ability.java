package domain.ability;

public class Ability {

    private boolean activated;
    protected int duration;
    protected int timeLeft;

    public Ability() {
        activated = false;
    }

    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getTimeLeft() { return timeLeft; }
    public void setTimeLeft(int timeLeft) { this.timeLeft = timeLeft; }

}
