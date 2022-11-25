package com.lataverne.towerdefense;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.RangeIndicatorComponent;
import com.lataverne.towerdefense.components.WayPointComponent;
import com.lataverne.towerdefense.manager.FileManager;
import com.lataverne.towerdefense.manager.GameManager;
import com.lataverne.towerdefense.manager.LevelManager;
import com.lataverne.towerdefense.manager.TowerManager;
import com.lataverne.towerdefense.ui.SelectTowerPane;
import com.lataverne.towerdefense.ui.TopInfoPane;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class TowerDefense extends GameApplication {

    private static GameManager gameManager;
    private EnemyCache enemyCache;
    private TowerCache towerCache;
    private LevelManager levelManager;

    @Override
    protected void initSettings(GameSettings settings){
        settings.setTitle("Tower Defense");
        settings.setVersion("build_0.1");
        settings.setWidth(20 * 48 + 115);
        settings.setHeight(20 * 32);
        settings.setAppIcon("logo.jpg");
        settings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));
        settings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        //vars.put("score", 0);
        vars.put("hp", 1);
        vars.put("money", 0);
        vars.put("kill", 0);
        vars.put("level", 0);
        vars.put("selectedTower", -1);
        vars.put("levelComplete", false);
    }


    @Override
    protected void initGame(){
        // We load our Factory to spawn entity
        FXGL.getGameWorld().addEntityFactory(new TowerDefenseFactory());

        // Initialization of GameManager
        gameManager = GameManager.getInstance();
        levelManager = gameManager.getLevelManager();
        enemyCache = gameManager.getEnemyCache();
        towerCache = gameManager.getTowerCache();

        levelManager.loadLevel(0);
    }

    @Override
    protected void initPhysics(){
        FXGL.onCollision(EntityType.WAYPOINT, EntityType.ENEMY, (point, enemy) -> {
            String direction = point.getComponent(WayPointComponent.class).getDirection();
            enemy.getComponent(EnemyComponent.class).setDirection(direction);
        });

        FXGL.onCollision(EntityType.ENEMY, EntityType.FINISH_POINT, (enemy, end) -> {
            FXGL.inc("hp", -1);
            enemyCache.remove(enemy);
            enemy.removeFromWorld();
            gameManager.check();
        });

        FXGL.onCollision(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);
            enemyComponent.removeHealth(bullet.getObject("bulletData"));
            if (enemyComponent.isDead()) {
                FXGL.inc("kill", 1);
                enemyCache.getCache().remove(enemy);
                enemy.removeFromWorld();
                gameManager.check();
                return;
            }
            bullet.removeFromWorld();
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
        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_MOVED, ignored -> {
            if (FXGL.geti("selectedTower") == -1) return;
            gameManager.onMouseMove();
        });

        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_CLICKED, action -> {
            // Si le joueur fait un clique droit, on enlève la selection en cours
            if(action.getButton() == MouseButton.SECONDARY){
                FXGL.set("selectedTower", -1);
                gameManager.hideRangeIndicator();
            } else {
                if(FXGL.geti("selectedTower") != -1) gameManager.buildTower();
            }

        });

        // DEBUG KEYS
        FXGL.onKey(KeyCode.R, "restartLevel", () -> LevelManager.getInstance().loadLevel(0));
        FXGL.onKey(KeyCode.L, "loadSave", () -> FileManager.getInstance().read());
        FXGL.onKey(KeyCode.S, "save", () -> FileManager.getInstance().save());

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

    /*public static void spawnEntities(){
        Entity rangeIndicator = FXGL.spawn("rangeIndicator");
        gameManager.setRangeIndicatorEntity(rangeIndicator);
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
