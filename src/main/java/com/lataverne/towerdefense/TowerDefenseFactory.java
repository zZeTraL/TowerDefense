package com.lataverne.towerdefense;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.Texture;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.*;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.manager.TowerManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class TowerDefenseFactory implements EntityFactory {

    private final TowerCache towerCache = TowerCache.getInstance();
    private final EnemyCache enemyCache = EnemyCache.getInstance();
    private int amountOfButtonCreated = 0;

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
        enemyCache.add(enemy, component);
        return enemy;
    }


    @Spawns("selectTower")
    public Entity newTowersBox(SpawnData data) {
        return FXGL.entityBuilder(data)
                .at(1000, 0)
                .with(new IrremovableComponent())
                .view("ui/right/chooseBg.png")
                .neverUpdated()
                .build();
    }

    @Spawns("towerButton")
    public Entity newTowerButton(SpawnData data) {
        TowerData towerData = TowerManager.getInstance().getTowerData(amountOfButtonCreated);
        amountOfButtonCreated += 1;

        Texture mainTexture = FXGL.texture(towerData.img());
        mainTexture.setTranslateX((80 - towerData.width()) / 2.0);
        mainTexture.setTranslateY((80 - towerData.height()) / 2.0);
        Texture backgroundTexture = FXGL.texture("ui/right/btnBg.png", 105, 105);
        backgroundTexture.setTranslateX((80 - backgroundTexture.getWidth()) / 2);
        backgroundTexture.setTranslateY((80 - backgroundTexture.getHeight()) / 2);

        return entityBuilder(data)
                .with(new IrremovableComponent())
                .view(new Rectangle(80, 80, Color.web("#D5D5D511")))
                .view(backgroundTexture)
                .view(mainTexture)
                .with(new TowerButtonComponent(towerData))
                .build();
    }

    @Spawns("rangeIndicator")
    public Entity newRangeIndicator(SpawnData data) {
        return entityBuilder(data)
                .with(new RangeIndicatorComponent())
                .zIndex(Integer.MAX_VALUE)
                .build();
    }

}
