package domain.body;

import domain.body.obstacle.*;
import domain.needForSpear.Controller;

public class BodyFactory {
    public static final int gameScreenWidth = Controller.getInstance().getFrameBorders()[0],
            gameScreenHeight = Controller.getInstance().getFrameBorders()[1],
            nPhantasmWidth = gameScreenWidth/10,
            nPhantasmHeight = 8,
            eSphereWidth = 12,
            eSphereHeight = 12,
            obsWidth = 80,
            obsHeight = 8,
            expWidth = 32,
            expHeight = 32;


    public static NoblePhantasm createNP(){
        return new NoblePhantasm((gameScreenWidth-nPhantasmWidth)/2, 570, nPhantasmWidth, nPhantasmHeight);
    }
    public static EnchantedSphere createES(NoblePhantasm np){
        return new EnchantedSphere(np.x+((nPhantasmWidth-eSphereWidth)/2), np.y-eSphereHeight, eSphereWidth, eSphereHeight, np);
    }
    public static Obstacle createObstacle(String typeOfObstacle, int x, int y){
        Obstacle newCreatedObs;
        if(typeOfObstacle.equals("Simple")){
            newCreatedObs = new SimpleObstacle(x, y, obsWidth, obsHeight, 1);
        }
        else if(typeOfObstacle.equals("Firm")){
            newCreatedObs = new FirmObstacle(x, y, obsWidth, obsHeight, 3);
        }
        else if(typeOfObstacle.equals("Explosive")){
            newCreatedObs = new ExplosiveObstacle(x, y, expWidth, expHeight, 1);
        }
        else if(typeOfObstacle.equals("Gift")){
            newCreatedObs = new GiftObstacle(x, y, obsWidth, obsHeight, 1, "chance");
        }
        else {
            newCreatedObs = new HollowPurpleObs(x, y, obsWidth, obsHeight, 1);
        }
        return newCreatedObs;
    }
}
