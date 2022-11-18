package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.lataverne.towerdefense.data.TowerData;

import java.util.ArrayList;
import java.util.List;

public class TowerManager {

    private static TowerManager instance;
    private List<TowerData> towerDataList;
    private TowerManager(){
        this.towerDataList = new ArrayList<>();
        TowerData data = FXGL.getAssetLoader().loadJSON("data/tower0.json", TowerData.class).get();
        towerDataList.add(data);
    }

    public static TowerManager getInstance(){
        if(instance == null) instance = new TowerManager();
        return instance;
    }

}
