package com.lataverne.towerdefense;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.level.Level;
import com.lataverne.towerdefense.cache.EntityCache;
import com.lataverne.towerdefense.components.TurretComponent;
import com.lataverne.towerdefense.manager.LevelManager;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class TowerDefense extends GameApplication {

    private static EntityCache entityCache;
    private static LevelManager levelManager;
    private static Boolean update = false;

    @Override
    protected void initSettings(GameSettings settings){
        settings.setTitle("Tower Defense");
        settings.setVersion("build_0.1");
        settings.setWidth(500);
        settings.setHeight(500);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("score", 0);
    }


    @Override
    protected void initGame(){
        // Initialization of EntityCache
        entityCache = new EntityCache();
        levelManager = new LevelManager();

        // We load our Factory to spawn entity
        getGameWorld().addEntityFactory(new TowerDefenseFactory());
        Level level = levelManager.nextLevel();
        FXGL.spawn("Turret", 300, 150);
        FXGL.spawn("Turret", 150, 150);
        entityCache.print();

    }

    @Override
    protected void initPhysics(){}

    @Override
    protected void onUpdate(double tpf){
        if(!update) return;
    }

    @Override
    protected void initUI(){
        Text text = new Text(100, 100, "text");
        FXGL.getGameScene().addUINode(text);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static EntityCache getEntityCache(){ return entityCache; }
    public static LevelManager getLevelManager(){ return levelManager; }
    public static Boolean getUpdate(){ return update; }

}
