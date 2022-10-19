package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.level.Level;

public class LevelManager {

    private int currentLevel;
    private int lastLevel;

    public LevelManager(){
        this.currentLevel = 0;
        this.lastLevel = 10;
    }

    // Getters

    // Setters

    // Methods
    public Level nextLevel() {
        if(currentLevel + 1 > 10) System.out.println("Max level reached!");
        else return FXGL.setLevelFromMap("tmx/level" + currentLevel + ".tmx");
        return null;
    }

}
