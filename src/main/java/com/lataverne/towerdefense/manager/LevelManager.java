package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.level.Level;

public class LevelManager {

    // Design pattern based on Singleton style
    // Instance
    private static LevelManager instance;

    // Attributes
    private int currentLevel;
    private int lastLevel;

    private LevelManager(){
        this.currentLevel = 0;
        this.lastLevel = 10;
    }

    // Getters
    public static LevelManager getInstance(){
        if(instance == null) instance = new LevelManager();
        return instance;
    }
    public int getCurrentLevel(){ return currentLevel; }

    // Setters

    // Methods
    public Level nextLevel() {
        if(currentLevel + 1 > 10) System.out.println("Max level reached!");
        else return FXGL.setLevelFromMap("tmx/level" + currentLevel + ".tmx");
        return null;
    }

}
