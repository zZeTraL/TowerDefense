package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.enums.EntityType;

public class EnemyComponent extends Component {

    private final EntityType type;
    private int health;

    public EnemyComponent(){
        this.type = EntityType.ENEMY;
        this.health = 100;
    }

    // Getters
    public int getHealth(){ return this.health; }


}
