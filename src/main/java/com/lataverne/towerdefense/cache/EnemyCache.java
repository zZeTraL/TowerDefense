package com.lataverne.towerdefense.cache;

import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.EntityType;

import java.util.HashMap;
import java.util.Map;

public class EnemyCache {

    // Design pattern : SINGLETON
    private static EnemyCache instance;

    /*
     * HashMap qui va associer à une clé de type Entity une valeur EnemyComponent
     * Ce cache que l'on crée va être important il va nous permettre ici
     * de venir supprimer des entités, mais également de venir les update,
     * c'est-à-dire lorsqu'une tour par exemple tir sur un ennemi je dois venir
     * mettre à jour sa vie, le cache me permet de venir faire cela assez rapidement,
     * car lors de la collision avec le projectile de la tour, on peut venir récupérer
     * quelle entité a été touchée par le projectile donc venir facilement actualiser
     * la vie de l'ennemi ou venir la supprimer (entité associée au component) du monde
     */
    private HashMap<Entity, EnemyComponent> cache;
    private EnemyCache(){ cache = new HashMap<>(); }

    // Getters

    /**
     * <p>
     *     Fonction qui retourne l'instance de la classe <br>
     *     <b>(si l'instance n'existe pas on l'a créé)</b>
     * </p>
     * @return EnemyCache
     */
    public static EnemyCache getInstance(){
        if(instance == null) instance = new EnemyCache();
        return instance;
    }

    /**
     * <p>
     *     Fonction qui retourne le cache
     * </p>
     * @return HashMap<Entity, EnemyComponent>
     */
    public HashMap<Entity, EnemyComponent> getCache(){ return cache; }

    /**
     * <p>
     *     Fonction qui va chercher l'EnemyComponent d'une entité précise
     * </p>
     * @param entity Entité que l'on souhaite savoir son component
     * @return EnemyComponent
     */
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

    /**
     * <p>
     *     Fonction qui va ajouter une entité de type ENEMY et son component associé à notre cache
     * </p>
     * @param entity Entité que l'on souhaite ajouter (ENEMY type required)
     * @param entityComponent Component de l'entité
     */
    public void add(Entity entity, EnemyComponent entityComponent){
        if(entity.getType() == EntityType.ENEMY) cache.put(entity, entityComponent);
    }

    /**
     * <p>
     *     Permet de supprimer une entité de notre cache
     * </p>
     * @param entity Entité que l'on souhaite supprimer
     */
    public void remove(Entity entity){ cache.remove(entity); }

    // Methods

    /**
     * <p>
     *     Fonction qui affiche le cache dans la console
     * </p>
     */
    public void print(){ System.out.println(cache); }


}
