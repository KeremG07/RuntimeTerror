package domain.needForSpear;

import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;

public class Player {

    public Inventory inventory;
    public NoblePhantasm noblePhantasm;
    public EnchantedSphere enchantedSphere;

    public Player() {
        inventory = new Inventory();
        noblePhantasm = new NoblePhantasm(450, 590, 100, 8);
        enchantedSphere = new EnchantedSphere(494, 578, 12, 12, noblePhantasm);
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

    }
    public void moveNoblePhantasm(){

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
