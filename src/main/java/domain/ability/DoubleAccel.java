package domain.ability;

public class DoubleAccel extends Ability {

    private static DoubleAccel instance;

    private DoubleAccel() {
        super();
        duration = 15;  // Given in the project desc.
        timeLeft = duration;
    }

    public static DoubleAccel getInstance() {
        if(instance == null) {
            instance = new DoubleAccel();
        }
        return instance;
    }
}
