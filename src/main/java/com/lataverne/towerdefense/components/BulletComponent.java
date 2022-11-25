package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.manager.GameManager;
import com.lataverne.towerdefense.manager.TowerManager;
import javafx.geometry.Point2D;

public class BulletComponent extends Component {

    private TowerManager towerManager;
    private Point2D position;
    private double damage;
    private int range;

    public BulletComponent(){
        this.towerManager = GameManager.getInstance().getTowerManager();
    }

    @Override
    public void onAdded() {
        int selectedTower = FXGL.geti("selectedTower");
        if(selectedTower == -1) selectedTower = 0;
        TowerData towerData = towerManager.getTowerData(selectedTower);

        this.position = entity.getPosition();
        this.damage = towerData.bulletData().damage();
        this.range = towerData.bulletData().range();
    }


    @Override
    public void onUpdate(double tpf) {
        Point2D newPosition = entity.getPosition();
        if (newPosition.distance(position) > range) {
            if (entity.isActive()) {
                entity.removeFromWorld();
            }
        }
    }


}
