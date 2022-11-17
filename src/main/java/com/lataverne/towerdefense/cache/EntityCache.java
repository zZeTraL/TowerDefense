package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.enums.EntityType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EntityCache {

    private static EntityCache instance;
    // Thread safe hashMap
    ConcurrentHashMap<EntityType, List<Entity>> entityCache;

    private EntityCache(){
        entityCache = new ConcurrentHashMap<>();
        // Initialization of the hashmap
        for (EntityType type : EntityType.values()){
            entityCache.put(type, new ArrayList<>());
        }
    }

    // Getters
    public static EntityCache getInstance(){
        if(instance == null) instance = new EntityCache();
        return instance;
    }
    public ConcurrentHashMap<EntityType, List<Entity>> getEntityCache(){ return entityCache; }

    // Setters

    public void add(Entity entity){
        if(entity.getType() instanceof EntityType type){
            List<Entity> currentListOfEntity = entityCache.get(type);
            currentListOfEntity.add(entity);
            entityCache.put(type, currentListOfEntity);
        }
    }

    // Methods
    public void print(){ System.out.println(entityCache); }

}
