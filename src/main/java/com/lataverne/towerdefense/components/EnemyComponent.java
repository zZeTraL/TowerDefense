package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.EnemyData;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.manager.LevelManager;

public class EnemyComponent extends Component {

    private EnemyData enemyData;
    private String direction;
    private boolean update;

    public EnemyComponent(){
        // On get les données du niveau actuel
        LevelData levelData = LevelManager.getInstance().getCurrentLevelData();
        enemyData = new EnemyData(levelData.enemyHealth(), levelData.enemySpeed());

        this.direction = levelData.startDirection();
        this.update = true;
    }

    // Getters
    public String getDirection(){ return direction; }
    public EnemyData getEnemyData(){ return enemyData; }

    // Setters
    public void setDirection(String direction){ this.direction = direction; }
    public void setUpdate(boolean update){ this.update = update; }

    @Override
    public void onUpdate(double tpf) {
        if(update){
            double speed = enemyData.moveSpeed();
            //System.out.println(speed);
            switch (direction) {
                case "up" -> entity.translateY(-speed);
                case "down" -> entity.translateY(speed);
                case "right" -> entity.translateX(speed);
                case "left" -> entity.translateX(-speed);
                default -> {}
            }
        }
    }

}
