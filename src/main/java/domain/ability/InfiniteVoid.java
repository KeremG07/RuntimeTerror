package domain.ability;

public class InfiniteVoid extends Ability {

    private static InfiniteVoid instance;

    private InfiniteVoid() {
        super();
        duration = 15;  // Given in the project desc.
        timeLeft = duration;
    }

    public static InfiniteVoid getInstance() {
        if(instance == null) {
            instance = new InfiniteVoid();
        }
        return instance;
    }
}
