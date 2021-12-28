package domain.needForSpear;

public class StartGame {
    private BuildGame buildGame;
    public StartGame() {
        //Controller.getInstance().player = new Player();
        //Controller.getInstance().timeLeft= 600*1000;
    }

    public BuildGame buildNewGame(String[] numOfObstaclesReq){
        buildGame = new BuildGame(numOfObstaclesReq);
        return buildGame;
    }
}
