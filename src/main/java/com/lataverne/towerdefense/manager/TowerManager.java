package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.lataverne.towerdefense.data.TowerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TowerManager {

    // Design pattern : SINGLETON
    private static TowerManager instance;

    /*
     * List qui va contenir le jeu de données de chaque tour
     * Cette liste que l'on crée va être important elle va nous permettre ici
     * de venir charger en mémoire toutes les données associés aux différentes tours
     * elles seront ainsi identifiable par un id
     */
    private final List<TowerData> towerDataList;

    // Constructor
    private TowerManager(){
        this.towerDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TowerData data = FXGL.getAssetLoader().loadJSON("data/tower" + i + ".json", TowerData.class).get();
            towerDataList.add(data);
        }
        //System.out.println(towerDataList);
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
     *     d'un index 0 à n (si n tours)
     * </p>
     * @param index id de la tour souhaitée
     * @return TowerData
     */
    public TowerData getTowerData(int index){
        if(index < 0 || index > towerDataList.size()) return null;
        return towerDataList.get(index);
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
        FXGL.spawn("towerButton", new SpawnData(1016, 260));
    }

}
