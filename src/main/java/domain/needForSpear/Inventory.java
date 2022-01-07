package domain.needForSpear;

import java.util.ArrayList;
import java.util.HashMap;

import domain.ymirAbility.*;

public class Inventory {
    private ArrayList<String> abilityList;
    public Inventory(){
        abilityList = new ArrayList<String>();
    }

    public void addAbility(String ability) {
        if(!abilityList.contains(ability)){
            abilityList.add(ability);
        }
    }
    public ArrayList<String> getAbilityList() {
        return abilityList;
    }
}
