package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.lataverne.towerdefense.EntityType;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.manager.LevelManager;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class TowerComponent extends Component {

    private final TowerData towerData;
    private LocalTimer bulletTimer;

    public TowerComponent(){
        int selectedTowerId = FXGL.geti("selectedTower");
        if(selectedTowerId == -1) selectedTowerId = 0;
        // je récupère les données qu'une tour au level n possède (vie/radius/etc...)
        //LevelData levelData = LevelManager.getInstance().getCurrentLevelData();
        // Le param num va changer en fonction du click sur le hud

        towerData = FXGL.getAssetLoader().loadJSON("data/tower" + selectedTowerId + ".json", TowerData.class).get();
    }

    // Getters
    /*public int getHealth(){ return health; }

    // Setters
    public void addHealth(int number){
        if(number < 0) this.health += number;
    }
    public void removeHealth(int number){
        if(number > 0) this.health -= number;
    }*/

    // Method from Component abstract class
    @Override
    public void onAdded(){
        this.bulletTimer = FXGL.newLocalTimer();
        bulletTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        Duration attackRate = Duration.seconds(towerData.bulletData().attackRate());
        if (!bulletTimer.elapsed(attackRate)) return;
        attack();
    }

    public void attack(){
        FXGL.getGameWorld().getClosestEntity(entity, closestEntity ->
                        closestEntity.isType(EntityType.ENEMY)
                                && closestEntity.getPosition().distance(entity.getPosition()) < towerData.bulletData().range())
                .ifPresent(enemy -> {
                    Point2D direction = enemy.getPosition().subtract(entity.getPosition());
                    FXGL.spawn("Bullet", new SpawnData(
                            entity.getCenter().subtract(towerData.width() / 2.0, towerData.height() / 2.0))
                            .put("bulletData", towerData.bulletData())
                            .put("dir", direction)
                    );
                    bulletTimer.capture();
                });
    }
}