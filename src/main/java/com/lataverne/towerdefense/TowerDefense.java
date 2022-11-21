package com.lataverne.towerdefense;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.WayPointComponent;
import com.lataverne.towerdefense.manager.GameManager;
import com.lataverne.towerdefense.manager.LevelManager;
import com.lataverne.towerdefense.manager.TowerManager;
import com.lataverne.towerdefense.ui.SelectTowerPane;
import com.lataverne.towerdefense.ui.TopInfoPane;
import javafx.scene.input.KeyCode;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class TowerDefense extends GameApplication {

    private static GameManager gameManager;

    @Override
    protected void initSettings(GameSettings settings){
        settings.setTitle("Tower Defense");
        settings.setVersion("build_0.1");
        settings.setWidth(20 * 50 + 115);
        settings.setHeight(15 * 50);
        settings.setAppIcon("logo.jpg");
        settings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));
        settings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("score", 0);
        vars.put("hp", 100);
        vars.put("money", 0);
        vars.put("kill", 0);
        vars.put("level", 0);
        vars.put("selectedTower", 0);
    }


    @Override
    protected void initGame(){
        // Initialization of GameManager
        gameManager = GameManager.getInstance();

        // We load our Factory to spawn entity
        getGameWorld().addEntityFactory(new TowerDefenseFactory());
        Level level = gameManager.getLevelManager().loadLevel(0);

        FXGL.spawn("Tower", 50, 200);
        /*
        TowerComponent searchTowerByEntity = towerCache.getTowerComponentByEntity(entity);
        System.out.println(searchTowerByEntity.getHealth());
        System.out.println(towerCache.getCache().get(entity).getHealth());
        towerCache.getCache().get(entity).removeHealth(5);
        System.out.println(towerCache.getCache().get(entity).getHealth());
        **/
    }

    @Override
    protected void initPhysics(){
        FXGL.onCollision(EntityType.WAYPOINT, EntityType.ENEMY, (point, enemy) -> {
            String direction = point.getComponent(WayPointComponent.class).getDirection();
            enemy.getComponent(EnemyComponent.class).setDirection(direction);
        });

        FXGL.onCollision(EntityType.ENEMY, EntityType.FINISH_POINT, (enemy, end) -> {
            FXGL.inc("hp", -2);
            System.out.println(FXGL.getGameScene().getUINodes());
            // TODO
            //  - CHECK HP OF THE PLAYER
            //  - REMOVE ENTITIES FROM ENEMY CACHE
            gameManager.getEnemyCache().remove(enemy);
            enemy.removeFromWorld();

            gameManager.check();
        });
    }

    @Override
    protected void onUpdate(double tpf){}

    @Override
    protected void initUI(){
        TowerManager.createTowersBox();
        FXGL.addUINode(new TopInfoPane());
        FXGL.addUINode(new SelectTowerPane());
    }

    @Override
    protected void initInput(){
        // DEBUG KEYS
        FXGL.onKey(KeyCode.A, "nextLevel", () -> LevelManager.getInstance().nextLevel());
        FXGL.onKey(KeyCode.R, "resetLevel", () -> LevelManager.getInstance().loadLevel(0));

        FXGL.onKeyDown(KeyCode.C, "printCache", () -> {
            TowerCache.getInstance().print();
            EnemyCache.getInstance().print();
        });

        FXGL.onKeyDown(KeyCode.K, "killAllEnemies", () -> {
            gameManager.check();
            if(gameManager.isWaveStarted()){
                EnemyCache enemyCache = gameManager.getEnemyCache();
                enemyCache.getCache().forEach((key, value) ->  {
                    key.removeFromWorld();
                });
                enemyCache.getCache().clear();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
