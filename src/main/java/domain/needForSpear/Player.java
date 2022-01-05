package domain.needForSpear;

import domain.body.BodyFactory;
import domain.body.EnchantedSphere;
import domain.body.MagicalHex;
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
    private Statistics statistics;
    private ILoadAndSaveAdapter iLoadAndSaveAdapter;
    private LocalLoadAndSave localLoadAndSave;
    private Inventory inventory;
    private NoblePhantasm noblePhantasm;
    private EnchantedSphere enchantedSphere;
    private boolean abilityActivated = false;
    private int ticksPerSecond = Controller.ticksPerSecond;
    private int abilityDuration = 30*ticksPerSecond;
    private String activeAbility;

    private String save;

    public Player() {
        statistics = new Statistics();
        localLoadAndSave = new LocalLoadAndSave();
        inventory = new Inventory();
        noblePhantasm = BodyFactory.createNP();
        enchantedSphere = BodyFactory.createES(noblePhantasm);
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
        saveList.add(Double.toString(statistics.getTimeElapsed()));
        saveList.add(Integer.toString(statistics.getChances()));
        saveList.add(Double.toString(statistics.getScore()));
        //saveList.add(statistics.obstacleTypeList());
        //saveList.add(statistics.giftList())

        saveList.add("" + noblePhantasm.getCoordinates()[0] + "/" + noblePhantasm.getNormalAngle());
        saveList.add("" + enchantedSphere.isNotShot() + "/" + enchantedSphere.getCoordinates()[0]
                + "/" + enchantedSphere.getCoordinates()[1] + "/" + enchantedSphere.getVx() + "/" + enchantedSphere.getVy());
        for (Obstacle obs : statistics.getObstacleList()) {
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
                //
                statistics.setTimeElapsed(Long.parseLong(loadList.get(0)));
                statistics.setChances(Integer.parseInt(loadList.get(1)));
                statistics.setScore(Double.parseDouble(loadList.get(2)));

                String noblePhantasmInfo = loadList.get(3);
                StringTokenizer stNP = new StringTokenizer(noblePhantasmInfo, "/");
                noblePhantasm.updateX(Double.parseDouble(stNP.nextToken()));
                //
                noblePhantasm.setNormalAngle(Double.parseDouble(stNP.nextToken()));

                String enchantedSphereInfo = loadList.get(4);
                StringTokenizer stES = new StringTokenizer(enchantedSphereInfo, "/");

                boolean ESisNotShot = Boolean.parseBoolean(stES.nextToken());
                enchantedSphere.setNotShot(ESisNotShot);
                if (ESisNotShot) {
                    enchantedSphere.setCoordinates(noblePhantasm.getCoordinates()[0] + 34, noblePhantasm.getCoordinates()[1] - noblePhantasm.getHeight());
                } else {
                    enchantedSphere.setCoordinates(Double.parseDouble(stES.nextToken()), Double.parseDouble(stES.nextToken()));
                }

                enchantedSphere.setVx(Double.parseDouble(stES.nextToken()));
                enchantedSphere.setVy(Double.parseDouble(stES.nextToken()));

                statistics.getObstacleList().clear();
                for (int i = 5; i < loadList.size(); i++) {
                    StringTokenizer st = new StringTokenizer(loadList.get(i), "/");
                    //saveList.add("" + obstacleNameMap.get(obs.getName()) + "/" +
                    // obs.getCoordinates()[0] + "/" + obs.getCoordinates()[1] + "/" + obs.getVx());

                    int obsType = Integer.parseInt(st.nextToken());
                    double coordX = Double.parseDouble(st.nextToken());
                    double coordY = Double.parseDouble(st.nextToken());


                    // we need to have different and consistent create obstacle methods for each obstacle type
                    if (obsType == 0) {
                        Obstacle obstacle = BodyFactory.createObstacle("Simple", coordX, coordY, 1);
                        double vx = Double.parseDouble(st.nextToken());
                        obstacle.setVx(vx);
                        statistics.addObstacle(obstacle);
                    }
                    if (obsType == 1) {
                        int chances = Integer.parseInt(st.nextToken());
                        Obstacle obstacle = BodyFactory.createObstacle("Firm", coordX, coordY, chances);
                        double vx = Double.parseDouble(st.nextToken());
                        obstacle.setVx(vx);
                        statistics.addObstacle(obstacle);
                    }
                    if (obsType == 2) {
                        Obstacle obstacle = BodyFactory.createObstacle("Explosive", coordX, coordY, 1);
                        double degree = Double.parseDouble(st.nextToken());
                        ((ExplosiveObstacle) obstacle).setDegree(degree);
                        statistics.addObstacle(obstacle);
                    }
                    if (obsType == 3) {
                        Obstacle obstacle = BodyFactory.createObstacle("Gift", coordX, coordY, 1);
                        double vx = Double.parseDouble(st.nextToken());
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

    public void shootEnchantedSphere() {
        enchantedSphere.shootEnchantedSphere();
    }

    public void updateEnchantedSphere() {
        enchantedSphere.updateWithNoblePhantasm();
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

    public void playerAbilityLifeCycle(){
        if(abilityActivated) {
            if (abilityDuration == 0) {
                abilityActivated = false;
                abilityDuration = 30*ticksPerSecond;
                switch (activeAbility){
                    case "DoubleNP":
                        noblePhantasm.setWidth(100);
                        break;
                    case "Unstoppable":
                        enchantedSphere.setUnstoppableES(false);
                        break;
                    case "MagicalHex":
                        noblePhantasm.setHasMagicalHex(false);
                        break;
                }
            } else {
                if(activeAbility.equals("MagicalHex")){
                    fireMagicalHex();
                }
                abilityDuration--;
            }
        }
    }

    public void fireMagicalHex() {
        if(abilityDuration % 30 == 0){
            double xCord = noblePhantasm.getCoordinates()[0];
            double yCord = noblePhantasm.getCoordinates()[1];
            double xCordRight = xCord + noblePhantasm.getWidth() * Math.cos(Math.toRadians(noblePhantasm.getNormalAngle()));
            double yCordRight = yCord - noblePhantasm.getWidth() * Math.sin(Math.toRadians(noblePhantasm.getNormalAngle()));
            MagicalHex leftHex = new MagicalHex(xCord, yCord, 4,12, noblePhantasm);
            MagicalHex rightHex = new MagicalHex(xCordRight, yCordRight, 4,12, noblePhantasm);
            statistics.addMagicalHex(leftHex);
            statistics.addMagicalHex(rightHex);
        }
    }

    public void useAbility(String abilityType) {
        if(inventory.getAbilityList().contains(abilityType) && !abilityActivated) {
            switch (abilityType) {
                case "DoubleNP":
                    noblePhantasm.doubleNP();
                    break;
                case "Unstoppable":
                    enchantedSphere.setUnstoppableES(true);
                    break;
                case "MagicalHex":
                    noblePhantasm.setHasMagicalHex(true);
                    break;
                case "ChanceGiving":
                    increaseChance();
                    break;
            }
            activeAbility = abilityType;
            abilityActivated = true;
            inventory.getAbilityList().remove(abilityType);
        }
    }

    public void increaseChance() {
        statistics.setChances(statistics.getChances() + 1);
    }

    public void loseChance() {
        statistics.setChances(statistics.getChances() - 1);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public NoblePhantasm getNoblePhantasm() {
        return noblePhantasm;
    }

    public EnchantedSphere getEnchantedSphere() {
        return enchantedSphere;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

}
