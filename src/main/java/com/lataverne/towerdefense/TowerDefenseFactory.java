package com.lataverne.towerdefense;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.lataverne.towerdefense.cache.EntityCache;
import com.lataverne.towerdefense.components.ArcherComponent;
import com.lataverne.towerdefense.components.TurretComponent;
import com.lataverne.towerdefense.enums.EntityType;

public class TowerDefenseFactory implements EntityFactory {

    private final EntityCache entityCache = TowerDefense.getEntityCache();

    @Spawns("Turret")
    public Entity newTurret(SpawnData data){
        Entity turret = FXGL.entityBuilder()
                .at(data.getX(), data.getY())
                .type(EntityType.TURRET)
                .view("droplet.png")
                .with(new TurretComponent())
                .build();
        entityCache.add(turret);
        return turret;
    }

    @Spawns("Archer")
    public Entity newArcher(SpawnData data){
        Entity archer = FXGL.entityBuilder()
                .at(data.getX(), data.getY())
                .type(EntityType.ARCHER)
                .view("bucket.png")
                .with(new ArcherComponent())
                .build();
        entityCache.add(archer);
        return archer;
    }


}
