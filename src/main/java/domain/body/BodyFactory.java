package domain.body;

import domain.body.fallingBody.Gift;
import domain.body.fallingBody.Remains;
import domain.body.obstacle.*;
import domain.needForSpear.Controller;

public class BodyFactory {
    public static final double gameScreenWidth = Controller.getInstance().getFrameBorders()[0],
            nPhantasmWidth = gameScreenWidth / 10,
            nPhantasmHeight = 8,
            eSphereWidth = 12,
            eSphereHeight = 12,
            obsWidth = 80,
            obsHeight = 8,
            expWidth = 32,
            expHeight = 32;

    public static NoblePhantasm createNP() {
        return new NoblePhantasm((gameScreenWidth - nPhantasmWidth) / 2, 570, nPhantasmWidth, nPhantasmHeight);
    }

    public static EnchantedSphere createES(NoblePhantasm np) {
        return new EnchantedSphere(np.x + ((nPhantasmWidth - eSphereWidth) / 2), np.y - eSphereHeight, eSphereWidth, eSphereHeight, np);
    }

    public static Obstacle createObstacle(String typeOfObstacle, double x, double y, int hitNum) {
        //@REQUIRES: a string to specify the obstacle type, 2 doubles for the coordinates of the obstacle
        //and an integer to specify the number of hits required to destroy obstacle
        //@MODIFIES: no modifications
        //@EFFECTS:creates obstacles that will be put into the game screen.
        //Used for all obstacle creations.
        Obstacle newCreatedObs;
        if (typeOfObstacle.equals("Simple")) {
            newCreatedObs = new SimpleObstacle(x, y, obsWidth, obsHeight, hitNum);
        } else if (typeOfObstacle.equals("Firm")) {
            newCreatedObs = new FirmObstacle(x, y, obsWidth, obsHeight, hitNum);
        } else if (typeOfObstacle.equals("Explosive")) {
            newCreatedObs = new ExplosiveObstacle(x, y, expWidth, expHeight, hitNum);
        } else if (typeOfObstacle.equals("Gift")) {
            newCreatedObs = new GiftObstacle(x, y, obsWidth, obsHeight, hitNum);
        } else {
            newCreatedObs = new HollowPurpleObs(x, y, obsWidth, obsHeight, hitNum);
        }
        return newCreatedObs;
    }

    public static Gift createGift(double x, double y) {
        Gift newCreatedGift = new Gift(x, y, 32, 32, Controller.getInstance().getPlayer().getNoblePhantasm(), "Gift", 30);
        return newCreatedGift;
    }
    public static Remains createRemains(double x, double y) {
        Remains newCreatedRemains = new Remains(x, y, 32, 32, Controller.getInstance().getPlayer().getNoblePhantasm());
        return newCreatedRemains;
    }
}
