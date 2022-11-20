package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.EntityType;

import java.util.HashMap;
import java.util.Map;

public class EnemyCache {

    // Design pattern singleton
    private static EnemyCache instance;

    private HashMap<Entity, EnemyComponent> cache;
    private EnemyCache(){
        cache = new HashMap<>();
    }

    // Getters
    public static EnemyCache getInstance(){
        if(instance == null) instance = new EnemyCache();
        return instance;
    }

    public HashMap<Entity, EnemyComponent> getCache(){ return cache; }
    public EnemyComponent getEnemyComponent(Entity entity){
        EnemyComponent result = null;
        for (Map.Entry<Entity, EnemyComponent> entry : cache.entrySet()){
            Entity key = entry.getKey();
            EnemyComponent value = entry.getValue();
            if(entity.equals(key)){
                result = value;
            }
        }
        return result;
    }

    // Setters
    public void add(Entity entity, EnemyComponent enemyData){
        if(entity.getType() == EntityType.ENEMY){
            cache.put(entity, enemyData);
        }
    }

    // Methods
    public void print(){ System.out.println(cache); }


}
