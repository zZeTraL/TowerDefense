package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.EnemyData;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.enums.EntityType;
import com.lataverne.towerdefense.manager.LevelManager;
import javafx.geometry.Point2D;

public class EnemyComponent extends Component {

    private EnemyData enemyData;
    private String direction;

    public EnemyComponent(){
        // On get les données du niveau associé
        LevelData levelData = LevelManager.getInstance().getLevelData();
        enemyData = new EnemyData(levelData.enemyHealth(), levelData.enemySpeed());
        this.direction = levelData.startDirection();
    }

    // Getters
    public String getDirection(){ return direction; }
    public EnemyData getEnemyData(){ return enemyData; }

    // Setters
    public void setDirection(String direction){
        this.direction = direction;
    }

    @Override
    public void onUpdate(double tpf) {
        double speed = enemyData.moveSpeed();
        switch (direction) {
            case "up" -> entity.translateY(-speed);
            case "down" -> entity.translateY(speed);
            case "right" -> entity.translateX(speed);
            case "left" -> entity.translateX(-speed);
            default -> {}
        }
    }

}
