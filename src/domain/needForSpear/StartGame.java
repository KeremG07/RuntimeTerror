package domain.needForSpear;

public class StartGame {
    BuildGame buildGame;
    Controller controller;
    public StartGame(String [] obstacles, String savePlace) {

        BuildGame buildGame = BuildGame.getInstance();
        buildGame.setObstacles(obstacles);
        Controller.getInstance().buildGame=buildGame;
        Controller.getInstance().player = new Player();
        Controller.getInstance().timeLeft= 600*1000;
    }
}
