package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.manager.LevelManager;

public class TowerComponent extends Component {

    private TowerCache towerCache = TowerCache.getInstance();
    // Data schem
    private final TowerData towerData;

    private int health;
    private int radius;
    private int cost;
    private double attackRate;
    private double damage;

    public TowerComponent(int num){
        // je récupère les données qu'une tour au level n possède (vie/radius/etc...)
        LevelData levelData = LevelManager.getInstance().getLevelData();
        // Le param num va changer en fonction du click sur le hud
        towerData = FXGL.getAssetLoader().loadJSON("data/tower" + num + ".json", TowerData.class).get();

        this.health = towerData.health();
        this.radius = towerData.radius();
        this.cost = towerData.cost();
        this.attackRate = towerData.attackRate();
        this.damage = towerData.damage();
    }

    // Getters
    public int getHealth(){ return health; }

    // Setters
    public void addHealth(int number){
        if(number < 0) this.health += number;
    }
    public void removeHealth(int number){
        if(number > 0) this.health -= number;
    }

    // Method from Component abstract class
    @Override
    public void onUpdate(double tpf) {
        /*towerCache.getCache().forEach((key, value) -> {
            System.out.println(key);
        });*/
    }
}