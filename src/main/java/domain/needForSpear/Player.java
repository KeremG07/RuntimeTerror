package domain.needForSpear;

import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;
import domain.body.obstacle.*;
import domain.loadAndSave.DatabaseLoadAndSave;
import domain.loadAndSave.ILoadAndSaveAdapter;
import domain.loadAndSave.LocalLoadAndSave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Player {

    Statistics statistics;
    ILoadAndSaveAdapter iLoadAndSaveAdapter;
    LocalLoadAndSave localLoadAndSave;
    public Inventory inventory;
    private NoblePhantasm noblePhantasm;
    private EnchantedSphere enchantedSphere;
    public static final double screenWidth = Controller.getInstance().getFrameBorders()[0];
    public static final double screenHeight = Controller.getInstance().getFrameBorders()[1];
    public String save;

    public Player() {
        statistics = new Statistics();
        localLoadAndSave = new LocalLoadAndSave();
        inventory = new Inventory();
        noblePhantasm = new NoblePhantasm(450, 570, (int) screenWidth / 10, 8);
        enchantedSphere = new EnchantedSphere(494, 558, 12, 12, noblePhantasm);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public NoblePhantasm getNoblePhantasm() {
        return noblePhantasm;
    }

    public EnchantedSphere getEnchantedSphere() {
        return enchantedSphere;
    }

    public void saveGame(String username) {
        if(save.equals("Database")){
            iLoadAndSaveAdapter= new DatabaseLoadAndSave();
        }else if(save.equals("Local")){
            iLoadAndSaveAdapter= new LocalLoadAndSave();
        }
        HashMap<String, Integer> obstacleNameMap = new HashMap<>();
        obstacleNameMap.put("Simple", 0);
        obstacleNameMap.put("Firm", 1);
        obstacleNameMap.put("Explosive", 2);
        obstacleNameMap.put("Gift", 3);
        ArrayList<String> saveList = new ArrayList<>();
        /* components of the list:
         elapsed time
         remaining chances
         current score
         currently unavailableobstacleTypeList
         currently unavailable giftList
         noble phantasm location rotation
         enchanted sphere isshot coordinates and speed
         obstacles on the screen (type location speed)
        */
        saveList.add(Double.toString(statistics.timeElapsed));
        saveList.add(Integer.toString(statistics.chances));
        saveList.add(Double.toString(statistics.score));
        //saveList.add(statistics.obstacleTypeList());
        //saveList.add(statistics.giftList())

        saveList.add("" + noblePhantasm.getCoordinates()[0] + "/" + noblePhantasm.normalAngle);
        saveList.add("" + enchantedSphere.isNotShot() + "/" + enchantedSphere.getCoordinates()[0]
                + "/" + enchantedSphere.getCoordinates()[1] + "/" + enchantedSphere.getVx() + "/" + enchantedSphere.getVy());
        for (Obstacle obs : statistics.obstacleList) {
            String obsType = obs.getName();
            if (obsType.equals("Explosive")) {
                saveList.add("" + obstacleNameMap.get(obs.getName()) + "/" + obs.getCoordinates()[0] + "/" + obs.getCoordinates()[1] + "/" + ((ExplosiveObstacle) obs).getDegree());
            }
            else if (obsType.equals("Firm")) {
                saveList.add("" + obstacleNameMap.get(obs.getName()) + "/" + obs.getCoordinates()[0] + "/" + obs.getCoordinates()[1] + "/" + obs.getNumberOfHits()+ "/" + obs.getVx());
            }
            else {
                saveList.add("" + obstacleNameMap.get(obs.getName()) + "/" + obs.getCoordinates()[0] + "/" + obs.getCoordinates()[1] + "/" + obs.getVx());
            }
        }
        try {
            iLoadAndSaveAdapter.saveGame(saveList, username);
        } catch (IOException io) {
            io.printStackTrace();
        }


    }

    public boolean loadGame(String username) {
        if(save.equals("Database")){
            iLoadAndSaveAdapter= new DatabaseLoadAndSave();
        }else if(save.equals("Local")){
            iLoadAndSaveAdapter= new LocalLoadAndSave();
        }
        HashMap<Integer, String> obstacleNameMap = new HashMap<>();
        obstacleNameMap.put(0, "Simple");
        obstacleNameMap.put(1, "Firm");
        obstacleNameMap.put(2, "Explosive");
        obstacleNameMap.put(3, "Gift");
        ArrayList<String> loadList = new ArrayList<>();
        try {
            loadList = iLoadAndSaveAdapter.loadGame(username);
            if (loadList.size() == 0) {
                System.out.println("No saved file found for "+ username+" in runtimeterror database.");
                return false;

            } else {
                statistics.timeElapsed = Double.parseDouble(loadList.get(0));
                statistics.chances = Integer.parseInt(loadList.get(1));
                statistics.score = Double.parseDouble(loadList.get(2));

                String noblePhantasmInfo = loadList.get(3);
                StringTokenizer stNP = new StringTokenizer(noblePhantasmInfo, "/");
                noblePhantasm.updateX(Integer.parseInt(stNP.nextToken()));
                noblePhantasm.normalAngle = Double.parseDouble(stNP.nextToken());

                String enchantedSphereInfo = loadList.get(4);
                StringTokenizer stES = new StringTokenizer(enchantedSphereInfo, "/");

                boolean ESisNotShot = Boolean.parseBoolean(stES.nextToken());
                enchantedSphere.setNotShot(ESisNotShot);
                if (ESisNotShot) {
                    enchantedSphere.setCoordinates(noblePhantasm.getCoordinates()[0] + 44, noblePhantasm.getCoordinates()[1] - noblePhantasm.height);
                } else {
                    enchantedSphere.setCoordinates(Integer.parseInt(stES.nextToken()), Integer.parseInt(stES.nextToken()));
                }

                enchantedSphere.setVx(Integer.parseInt(stES.nextToken()));
                enchantedSphere.setVy(Integer.parseInt(stES.nextToken()));

                statistics.obstacleList.clear();
                for (int i = 5; i < loadList.size(); i++) {
                    StringTokenizer st = new StringTokenizer(loadList.get(i), "/");
                    //saveList.add("" + obstacleNameMap.get(obs.getName()) + "/" +
                    // obs.getCoordinates()[0] + "/" + obs.getCoordinates()[1] + "/" + obs.getVx());

                    int obsType = Integer.parseInt(st.nextToken());
                    int coordX = Integer.parseInt(st.nextToken());
                    int coordY = Integer.parseInt(st.nextToken());


                    // we need to have different and consistent create obstacle methods for each obstacle type
                    if (obsType == 0) {
                        Obstacle obstacle = new SimpleObstacle(coordX, coordY, 80, 8, 1);
                        int vx = Integer.parseInt(st.nextToken());
                        obstacle.setVx(vx);
                        statistics.addObstacle(obstacle);
                    }
                    if (obsType == 1) {
                        int chances = Integer.parseInt(st.nextToken());
                        Obstacle obstacle = new FirmObstacle(coordX, coordY, 80, 8, chances);
                        int vx = Integer.parseInt(st.nextToken());
                        obstacle.setVx(vx);
                        statistics.addObstacle(obstacle);
                    }
                    if (obsType == 2) {
                        Obstacle obstacle = new ExplosiveObstacle(coordX, coordY, 32, 32, 1);
                        double degree = Double.parseDouble(st.nextToken());
                        ((ExplosiveObstacle) obstacle).setDegree(degree);
                        statistics.addObstacle(obstacle);
                    }
                    if (obsType == 3) {
                        Obstacle obstacle = new GiftObstacle(coordX, coordY, 80, 8, 1, "chance");
                        int vx = Integer.parseInt(st.nextToken());
                        obstacle.setVx(vx);
                        statistics.addObstacle(obstacle);
                    }

                }


            }

        } catch (IOException e) {
            System.out.println("There is no saved game associated with the given username");
            return false;
        }return true;

    }

    public void pauseGame() {

    }

    public void resumeGame() {

    }

    public void shootEnchantedSphere() {
        enchantedSphere.shootEnchantedSphere();
    }

    public void updateEnchantedSphere() {
        enchantedSphere.updateWithNP();
    }

    public void moveEnchantedSphere() {
        enchantedSphere.move();
    }

    public void moveNoblePhantasm(String action) {
        switch (action) {
            case "HeldRight":
                noblePhantasm.slideRight();
                break;
            case "HeldLeft":
                noblePhantasm.slideLeft();
                break;
            case "PressedRight":
                noblePhantasm.moveRight();
                break;
            default:
                noblePhantasm.moveLeft();
                break;
        }

    }

    public void rotateNoblePhantasm(String action) {
        if (action.equals("rotateLeft")) noblePhantasm.rotateLeft();
        if (action.equals("rotateRight")) noblePhantasm.rotateRight();
    }

    public void fireMagicalHex() {

    }

    public void useAbility(String abilityType) {

    }

    public void increaseChance() {
        statistics.setChances(statistics.chances + 1);
    }

    public void loseChance() {
        statistics.setChances(statistics.chances - 1);
    }
}
