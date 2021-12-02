package domain.needForSpear;

import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;

public class Player {

    public Inventory inventory;
    private NoblePhantasm noblePhantasm;
    private EnchantedSphere enchantedSphere;
    public static final double screenWidth = Controller.getInstance().getFrameBorders()[0];
    public static final double screenHeight = Controller.getInstance().getFrameBorders()[1];
    public Player() {
        inventory = new Inventory();
        noblePhantasm = new NoblePhantasm(450, 570, (int) screenWidth/10,  8);
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

    public void saveGame(){

    }
    public void loadGame(){

    }
    public void pauseGame(){

    }
    public void resumeGame(){

    }
    public void shootEnchantedSphere(){
        enchantedSphere.shootEnchantedSphere();
    }
    public void updateEnchantedSphere(){
        enchantedSphere.updateWithNP();
    }
    public void moveEnchantedSphere(){
        enchantedSphere.move();
    }
    public void moveNoblePhantasm(String action){
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

    public void rotateNoblePhantasm(String action){
        if(action.equals("rotateLeft")) noblePhantasm.rotateLeft();
        if(action.equals("rotateRight")) noblePhantasm.rotateRight();
    }
    public void fireMagicalHex(){

    }
    
    public void useAbility(String abilityType){
        
    }
    public void increaseChance(){
        
    }
    public void loseChance(){

    }
}
