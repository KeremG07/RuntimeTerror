package domain.ability;

public class InfiniteVoid extends Ability {

    public InfiniteVoid() {
        super();
        duration = 15;  // Given in the project desc.
        timeLeft = duration;
    }
}
