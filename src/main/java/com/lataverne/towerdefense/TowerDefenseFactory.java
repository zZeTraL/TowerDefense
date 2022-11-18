package com.lataverne.towerdefense;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.TowerComponent;
import com.lataverne.towerdefense.components.WayPointComponent;
import javafx.scene.shape.Rectangle;

public class TowerDefenseFactory implements EntityFactory {

    private final TowerCache towerCache = TowerCache.getInstance();
    private final EnemyCache enemyCache = EnemyCache.getInstance();

    @Spawns("")
    public Entity newEmpty(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.EMPTY)
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("Waypoint")
    public Entity newDirection(SpawnData data) {
        String direction = (String) data.getData().get("direction");

        Rectangle rectangle = new Rectangle();
        switch (direction) {
            case "right", "left" -> {
                rectangle.setHeight(1);
                rectangle.setWidth(32);
            }
            case "up", "down" -> {
                rectangle.setHeight(32);
                rectangle.setWidth(1);
            }
            default -> {
            }
        }

        return FXGL.entityBuilder(data)
                .type(EntityType.WAYPOINT)
                .viewWithBBox(rectangle)
                .bbox(BoundingShape.box(32, 1))
                .with(new WayPointComponent(direction))
                .collidable()
                .build();
    }

    @Spawns("startPoint")
    public Entity newStartPoint(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.START_POINT)
                .build();
    }

    @Spawns("finishPoint")
    public Entity newEndPoint(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.FINISH_POINT)
                .viewWithBBox(new Rectangle(1, 32))
                .collidable()
                .build();
    }

    @Spawns("Tower")
    public Entity newTower(SpawnData data){
        // Le 0 va change en cliquant sur la tour dans le HUD
        TowerComponent component = new TowerComponent(0);
        Entity tower = FXGL.entityBuilder()
                .at(data.getX(), data.getY())
                .type(EntityType.TOWER)
                .view("droplet.png")
                .with(component)
                .build();
        towerCache.add(tower, component);
        return tower;
    }

    @Spawns("Enemy")
    public Entity newEnemy(SpawnData data){
        EnemyComponent component = new EnemyComponent();
        Entity enemy = FXGL.entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Rectangle(32, 32))
                .with(component)
                .bbox(BoundingShape.box(32, 32))
                .collidable()
                .build();
        enemyCache.add(enemy, component.getEnemyData());
        return enemy;
    }


}
