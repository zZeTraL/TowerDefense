package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.enums.EntityType;

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
    public List<Entity> getEntitiesByType(EntityType type){ return entityCache.get(type); }
    public Entity getEntity(EntityType type, int index){
        List<Entity> entityList = getEntitiesByType(type);
        if(index > entityList.size() || index < 0) return null;
        return entityList.get(index);
    }
    public <T extends Component> T getComponent(Entity entity, Class<T> type){
        Component component = entity.getComponent(type);
        /*EntityType entityType = (EntityType) entity.getType();
        var componentsList = entity.getComponents();
        for(Component component : componentsList){
            if(type == )

        }*/
        if (component == null) {
            throw new IllegalArgumentException("Component " + type.getSimpleName() + " not found!");
        }

        return type.cast(component);
    }

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
