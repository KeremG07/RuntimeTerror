package domain.needForSpear;

public class StartGame {
    private BuildGame buildGame;
    public StartGame() {
    }

    public BuildGame buildNewGame(String[] numOfObstaclesReq){
        buildGame = new BuildGame(numOfObstaclesReq);
        return buildGame;
    }
}
