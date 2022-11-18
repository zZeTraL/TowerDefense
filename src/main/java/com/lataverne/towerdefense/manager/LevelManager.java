package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.level.Level;
import com.lataverne.towerdefense.cache.EntityCache;
import com.lataverne.towerdefense.data.LevelData;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    // Design pattern based on Singleton style
    // Instance
    private static LevelManager instance;

    // Attributes
    private List<LevelData> levelDataList;
    private int currentLevel;
    private int lastLevel;

    private LevelManager(){
        this.levelDataList = new ArrayList<>();
        this.currentLevel = 0;
        this.lastLevel = 1;

        for (int i = 0; i <= lastLevel; i++) {
            LevelData data;
            data = FXGL.getAssetLoader().loadJSON("levels/level" + i + ".json", LevelData.class).get();
            levelDataList.add(data);
        }

        System.out.println(levelDataList);

    }

    // Getters
    public static LevelManager getInstance(){
        if(instance == null) instance = new LevelManager();
        return instance;
    }
    public int getCurrentLevel(){ return currentLevel; }
    public LevelData getLevelData(){ return levelDataList.get(currentLevel); }

    // Setters

    // Methods
    public void spawnEnemy(LevelData data){
        FXGL.runOnce(() -> {
            FXGL.run(() -> {
                FXGL.spawn("Enemy", 32, 288);
            }, Duration.seconds(data.spawnRate()), data.amountOfEnemy());
        }, Duration.seconds(2));
    }

    public Level setLevel(int level){
        if(level < 0 || level > lastLevel) return null;
        else {
            currentLevel = level;
            LevelData data = levelDataList.get(currentLevel);
            FXGL.set("money", data.money());
            FXGL.set("levelName", data.name());
            spawnEnemy(data);
            Level tmp = FXGL.setLevelFromMap("tmx/level" + level + ".tmx");
            System.out.println(EntityCache.getInstance().getEntityCache());
            return tmp;
        }
    }

    public Level nextLevel() {
        /*if(currentLevel + 1 > 10) System.out.println("Max level reached!");
        else return FXGL.setLevelFromMap("tmx/level" + currentLevel + ".tmx");
        return null;*/
        if(currentLevel + 1 > lastLevel) {
            System.out.println("Max level reached!");
        } else {
            currentLevel += 1;
            LevelData data = levelDataList.get(currentLevel);
            FXGL.set("levelName", data.name());
            FXGL.set("money", data.money());
            return FXGL.setLevelFromMap(data.map());
        }
        return null;
    }

}
