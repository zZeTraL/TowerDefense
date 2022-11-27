package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.components.TowerComponent;
import com.lataverne.towerdefense.EntityType;

import java.util.HashMap;
import java.util.Map;

public class TowerCache {

    // Design pattern : SINGLETON
    private static TowerCache instance;

    /*
     * HashMap qui va associer à une clé de type Entity une valeur TowerComponent
     * Ce cache que l'on crée va être important il va nous permettre ici
     * de venir identifier clairement notre tour parmi toutes les autres
     */
    private HashMap<Entity, TowerComponent> cache;

    private TowerCache(){ cache = new HashMap<>(); }

    // Getters

    /**
     * <p>
     *     Fonction qui retourne l'instance de la classe <br>
     *     <b>(si l'instance n'existe pas on l'a créé)</b>
     * </p>
     * @return TowerCache
     */
    public static TowerCache getInstance(){
        if(instance == null) instance = new TowerCache();
        return instance;
    }

    /**
     * <p>
     *     Fonction qui retourne le cache
     * </p>
     * @return HashMap<Entity, TowerComponent>
     */
    public HashMap<Entity, TowerComponent> getCache(){ return cache; }

    /**
     * <p>
     *     Fonction qui va chercher le TowerComponent d'une entité précise
     * </p>
     * @param entity Entité que l'on souhaite savoir son component
     * @return TowerComponent
     */
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

    /**
     * <p>
     *     Fonction qui va ajouter une entité de type ENEMY et son component associé à notre cache
     * </p>
     * @param entity Entité que l'on souhaite ajouter (ENEMY type required)
     * @param entityComponent Component de l'entité
     */
    public void add(Entity entity, TowerComponent entityComponent){
        if(entity.getType() == EntityType.TOWER){
            cache.put(entity, entityComponent);
        }
    }

    public void clear(){
        if(cache.size() > 0){
            cache.forEach((key, value) -> {
                key.removeFromWorld();
            });
            cache.clear();
        }
    }

    // Methods
    /**
     * <p>
     *     Fonction qui affiche le cache dans la console
     * </p>
     */
    public void print(){ System.out.println(cache); }

}
