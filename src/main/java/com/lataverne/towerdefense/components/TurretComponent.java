package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.enums.EntityType;

public class TurretComponent extends Component implements EntityInterface {

    private final EntityType type;
    private int radius;
    private int health;

    public TurretComponent(){
        this.type = EntityType.TOWER;
        this.radius = 100;
        this.health = 100;
    }

    // Getters
    public int getRadius(){ return this.radius; }
    public int getHealth(){ return this.health; }

    // Methods from EntityInterface
    @Override
    public void attack(){
        System.out.println("Turret with ID (" + this + ") attack");
    }

    @Override
    public void print() {
        System.out.println(
                "\nType: " + type + "\nRadius: " + radius + "\nHealth: " + health + "\n"
        );
    }

    // Method from Component abstract class
    @Override
    public void onUpdate(double tpf) {
        if(!update) return;
        attack();
    }
}