package com.lataverne.towerdefense;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.ui.UIFactoryService;
import com.lataverne.towerdefense.cache.EntityCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.WayPointComponent;
import com.lataverne.towerdefense.enums.EntityType;
import com.lataverne.towerdefense.manager.LevelManager;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class TowerDefense extends GameApplication {

    private static EntityCache entityCache;
    private static LevelManager levelManager;
    private static Boolean update = false;

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
        vars.put("level", 0);
        vars.put("levelName", "");
        vars.put("money", 0);
    }


    @Override
    protected void initGame(){
        // Initialization of EntityCache and levelManager
        entityCache = EntityCache.getInstance();
        levelManager = LevelManager.getInstance();

        // We load our Factory to spawn entity
        getGameWorld().addEntityFactory(new TowerDefenseFactory());
        Level level = levelManager.setLevel(0);

        //FXGL.spawn("Enemy", 32, 285);

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
            enemy.removeFromWorld();
        });
    }

    @Override
    protected void onUpdate(double tpf){}

    @Override
    protected void initUI(){
        UIFactoryService ui = FXGL.getUIFactoryService();
        Text levelName = ui.newText("", Color.BLACK, 22);
        Text hp = ui.newText("", Color.BLACK, 20);
        Text money = ui.newText("", Color.BLACK, 20);
        levelName.textProperty().bind(getsp("levelName"));
        hp.textProperty().bind(getip("hp").asString());
        money.textProperty().bind(getip("money").asString());

        FXGL.addUINode(levelName, 0, 20);
        FXGL.addUINode(hp, 0, 40);
        FXGL.addUINode(money, 0, 60);
    }

    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.A, "nextLevel", () -> LevelManager.getInstance().nextLevel());
        FXGL.onKey(KeyCode.R, "resetLevel", () -> LevelManager.getInstance().setLevel(0));

        /*FXGL.onKey(KeyCode.S, "moveDown", () -> {
            Entity enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
            enemy.translateY(2);
        });

        FXGL.onKey(KeyCode.D, "moveRight", () -> {
            Entity enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
            enemy.translateX(2);
        });

        FXGL.onKey(KeyCode.Q, "moveLeft", () -> {
            Entity enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
            enemy.translateX(-2);
        });

        FXGL.onKey(KeyCode.W, "moveForward", () -> {
            Entity enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
            enemy.translateY(-2);
        });*/
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static EntityCache getEntityCache(){ return entityCache; }
    public static Boolean getUpdate(){ return update; }

}
