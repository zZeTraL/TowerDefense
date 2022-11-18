package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.data.EnemyData;
import com.lataverne.towerdefense.EntityType;

import java.util.HashMap;

public class EnemyCache {

    private static EnemyCache instance;
    private HashMap<Entity, EnemyData> cache;
    private EnemyCache(){
        cache = new HashMap<>();
    }

    // Getters
    public static EnemyCache getInstance(){
        if(instance == null) instance = new EnemyCache();
        return instance;
    }
    public HashMap<Entity, EnemyData> getCache(){ return cache; }

    // Setters
    public void add(Entity entity, EnemyData enemyData){
        if(entity.getType() == EntityType.ENEMY){
            cache.put(entity, enemyData);
        }
    }

    // Methods
    public void print(){ System.out.println(cache); }

}
