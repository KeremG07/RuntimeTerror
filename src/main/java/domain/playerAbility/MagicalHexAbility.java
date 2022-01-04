package domain.playerAbility;

public class MagicalHexAbility {

    private String name;
    private int duration;
    private int timeLeft;
    private boolean activated;

    public MagicalHexAbility(){
        activated = true;
        duration = 30;
        timeLeft = duration;
        name = "MagicalHex";
    }
}
