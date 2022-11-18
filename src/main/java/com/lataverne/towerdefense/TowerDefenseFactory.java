package com.lataverne.towerdefense;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.lataverne.towerdefense.cache.EntityCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.TowerComponent;
import com.lataverne.towerdefense.components.WayPointComponent;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.enums.EntityType;
import com.lataverne.towerdefense.manager.LevelManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class TowerDefenseFactory implements EntityFactory {

    private final EntityCache entityCache = TowerDefense.getEntityCache();

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

        Entity waypoint = FXGL.entityBuilder(data)
                .type(EntityType.WAYPOINT)
                .viewWithBBox(rectangle)
                .bbox(BoundingShape.box(32, 1))
                .with(new WayPointComponent(direction))
                .collidable()
                .build();
        entityCache.add(waypoint);
        return waypoint;
    }

    @Spawns("startPoint")
    public Entity newStartPoint(SpawnData data) {
        Entity waypoint = FXGL.entityBuilder(data)
                .type(EntityType.START_POINT)
                .build();
        entityCache.add(waypoint);
        return waypoint;
    }

    @Spawns("finishPoint")
    public Entity newEndPoint(SpawnData data) {
        Entity waypoint = FXGL.entityBuilder(data)
                .type(EntityType.FINISH_POINT)
                .viewWithBBox(new Rectangle(1, 32))
                .collidable()
                .build();
        entityCache.add(waypoint);
        return waypoint;
    }

    @Spawns("Tower")
    public Entity newTower(SpawnData data){
        Entity tower = FXGL.entityBuilder()
                .at(data.getX(), data.getY())
                .type(EntityType.TOWER)
                .view("droplet.png")
                .with(new TowerComponent(100, 100))
                .build();
        entityCache.add(tower);
        return tower;
    }

    @Spawns("Enemy")
    public Entity newEnemy(SpawnData data){
        LevelData levelData = LevelManager.getInstance().getLevelData();

        Entity enemy = FXGL.entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Rectangle(32, 32))
                .with(new EnemyComponent(levelData.enemyHealth(), levelData.enemySpeed(), levelData.startDirection()))
                .bbox(BoundingShape.box(32, 32))
                .collidable()
                .build();
        entityCache.add(enemy);
        return enemy;
    }


}
