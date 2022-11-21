package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.lataverne.towerdefense.data.TowerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TowerManager {

    private static TowerManager instance;
    private Map<String, TowerData> towerDataMap;
    private TowerManager(){
        this.towerDataMap = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            TowerData data = FXGL.getAssetLoader().loadJSON("data/tower" + i + ".json", TowerData.class).get();
            towerDataMap.put(data.name(), data);
        }
        System.out.println(towerDataMap);
    }

    public static TowerManager getInstance(){
        if(instance == null) instance = new TowerManager();
        return instance;
    }

    public TowerData getTowerData(String towerName){ return towerDataMap.get(towerName); }
    public TowerData getTowerData(int index){
        if(index < 0 || index > towerDataMap.size()) {
            return null;
        } else {
            TowerData result = null;
            int count = 0;
            for(Map.Entry<String, TowerData> entry : towerDataMap.entrySet()){
                if(count == index){
                    result = entry.getValue();
                }
                count++;
            }
            return result;
        }
    }

    // Methods
    public static void createTowersBox() {
        FXGL.spawn("selectTower");
        FXGL.spawn("towerButton", new SpawnData(1016, 60));
        FXGL.spawn("towerButton", new SpawnData(1016, 160));
        /*FXGL.spawn("towerButton", new SpawnData(1016, 260));
        FXGL.spawn("towerButton", new SpawnData(1016, 360));
        FXGL.spawn("towerButton", new SpawnData(1016, 460));
        FXGL.spawn("towerButton", new SpawnData(1016, 560));*/
        //FXGL.spawn("pauseButton", new SpawnData(1016, 660));
    }

}
