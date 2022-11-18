package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.TowerData;

public class TowerComponent extends Component implements EntityInterface {

    private final TowerData towerData;

    public TowerComponent(int health, int radius){
        towerData = new TowerData(health, radius);
    }

    // Methods from EntityInterface
    @Override
    public void attack(){
        System.out.println("Turret with ID (" + this + ") attack");
    }

    @Override
    public void print() {
        System.out.println(
                "\nRadius: " + towerData.radius() + "\nHealth: " + towerData.health() + "\n"
        );
    }

    // Method from Component abstract class
    @Override
    public void onUpdate(double tpf) {
        if(!update) return;
        attack();
    }
}