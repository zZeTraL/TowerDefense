package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.components.TowerComponent;
import com.lataverne.towerdefense.EntityType;

import java.util.HashMap;
import java.util.Map;

public class TowerCache {

    private static TowerCache instance;
    private HashMap<Entity, TowerComponent> cache;
    private TowerCache(){
        cache = new HashMap<>();
    }

    // Getters
    public static TowerCache getInstance(){
        if(instance == null) instance = new TowerCache();
        return instance;
    }
    public HashMap<Entity, TowerComponent> getCache(){ return cache; }
    public TowerComponent getTowerComponent(Entity entity){
        TowerComponent result = null;
        for (Map.Entry<Entity, TowerComponent> entry : cache.entrySet()){
            Entity key = entry.getKey();
            TowerComponent value = entry.getValue();
            if(entity.equals(key)){
                result = value;
            }
        }
        return result;
    }

    // Setters
    public void add(Entity entity, TowerComponent towerData){
        if(entity.getType() == EntityType.TOWER){
            cache.put(entity, towerData);
        }
    }

    // Methods
    public void print(){ System.out.println(cache); }

}
