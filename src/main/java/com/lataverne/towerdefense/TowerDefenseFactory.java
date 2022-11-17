package com.lataverne.towerdefense;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.lataverne.towerdefense.cache.EntityCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.TurretComponent;
import com.lataverne.towerdefense.enums.EntityType;

public class TowerDefenseFactory implements EntityFactory {

    private final EntityCache entityCache = TowerDefense.getEntityCache();

    @Spawns("Tower")
    public Entity newTower(SpawnData data){
        Entity tower = FXGL.entityBuilder()
                .at(data.getX(), data.getY())
                .type(EntityType.TOWER)
                .view("droplet.png")
                .with(new TurretComponent())
                .build();
        entityCache.add(tower);
        return tower;
    }

    @Spawns("Enemy")
    public Entity newEnemy(SpawnData data){
        Entity enemy = FXGL.entityBuilder(data)
                .at(data.getX(), data.getY())
                .type(EntityType.TOWER)
                .view("enemy.png")
                .with(new EnemyComponent())
                .build();
        entityCache.add(enemy);
        return enemy;
    }


}
