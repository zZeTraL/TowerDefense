package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.lataverne.towerdefense.data.TowerData;

import java.util.HashMap;
import java.util.Map;

public class TowerManager {

    // Design pattern : SINGLETON
    private static TowerManager instance;

    /*
     * HashMap qui va contenir qui à un string va associer un TowerData
     * Cette hashmap que l'on crée va être important elle va nous permettre ici
     * de venir charger en mémoire toutes les données associés aux différentes tours
     * elles seront ainsi identifiable par le nom et/ou par leur id
     */
    private final HashMap<String, TowerData> towerDataMap;

    // Constructor
    private TowerManager(){
        this.towerDataMap = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            TowerData data = FXGL.getAssetLoader().loadJSON("data/tower" + i + ".json", TowerData.class).get();
            towerDataMap.put(data.name(), data);
        }
        System.out.println(towerDataMap);
    }

    /**
     * <p>
     *     Fonction qui retourne l'instance de la classe <br>
     *     <b>(si l'instance n'existe pas on l'a créé)</b>
     * </p>
     * @return TowerManager
     */
    public static TowerManager getInstance(){
        if(instance == null) instance = new TowerManager();
        return instance;
    }

    /**
     * <p>
     *     Fonction qui permet de récupérer le jeu de données d'une tour en fonction
     *     de son name
     * </p>
     * @param towerName nom de la tour
     * @return TowerData
     */
    public TowerData getTowerData(String towerName){ return towerDataMap.get(towerName); }

    /**
     * <p>
     *     Fonction qui permet de récupérer le jeu de données d'une tour en fonction
     *     d'un index 0 à n (si n tours)
     * </p>
     * @param index id de la tour souhaitée
     * @return TowerData
     */
    public TowerData getTowerData(int index){
        if(index < 0 || index > towerDataMap.size()) {
            return null;
        } else {
            TowerData result = null;
            int count = 0;
            for(Map.Entry<String, TowerData> entry : towerDataMap.entrySet()){
                if(count == index){
                    result = entry.getValue();
                }
                count++;
            }
            return result;
        }
    }

    // Methods

    /**
     * <p>
     *     Fonction qui crée les boutons pour pouvoir sélectionner les tours et de les placer
     * </p>
     */
    public static void createTowersBox() {
        FXGL.spawn("selectTower");
        FXGL.spawn("towerButton", new SpawnData(1016, 60));
        FXGL.spawn("towerButton", new SpawnData(1016, 160));
    }

}
