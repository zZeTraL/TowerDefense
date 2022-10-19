package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.enums.EntityType;

public class ArcherComponent extends Component implements EntityInterface {

    private final EntityType type;
    private int radius;
    private int health;

    public ArcherComponent(){
        this.type = EntityType.TURRET;
        this.radius = 100;
        this.health = 100;
    }

    // Getters
    public int getRadius(){ return this.radius; }
    public int getHealth(){ return this.health; }

    // Methods from EntityInterface
    @Override
    public void attack(){
        System.out.println("Archer with ID (" + this + ") attack");
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
