package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.manager.LevelManager;

public class TowerComponent extends Component {

    private final TowerData towerData;

    public TowerComponent(int num){

        LevelData levelData = LevelManager.getInstance().getLevelData();
        towerData = FXGL.getAssetLoader().loadJSON("data/tower" + num + ".json", TowerData.class).get();

        System.out.println(towerData.radius());
    }

    // Method from Component abstract class
    @Override
    public void onUpdate(double tpf) {}
}