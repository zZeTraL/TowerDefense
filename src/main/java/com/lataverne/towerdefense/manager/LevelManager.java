package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.lataverne.towerdefense.TowerDefense;
import com.lataverne.towerdefense.TowerDefenseFactory;
import com.lataverne.towerdefense.components.RangeIndicatorComponent;
import com.lataverne.towerdefense.components.TowerButtonComponent;
import com.lataverne.towerdefense.data.LevelData;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    // Design pattern based on Singleton style
    private static LevelManager instance;

    // Attributes
    private List<LevelData> levelDataList;
    private int lastLevel;
    private int amountOfEnemySpawned;

    // Constructor
    private LevelManager(){
        this.levelDataList = new ArrayList<>();
        this.lastLevel = 2;
        this.amountOfEnemySpawned = 0;

        for (int i = 0; i < lastLevel; i++) {
            LevelData data;
            data = FXGL.getAssetLoader().loadJSON("levels/level" + i + ".json", LevelData.class).get();
            levelDataList.add(data);
        }

        System.out.println(levelDataList);

    }

    // Getters

    /**
     * Fonction qui retourne l'instance de LevelManager
     * @return LevelManager
     */
    public static LevelManager getInstance(){
        if(instance == null) instance = new LevelManager();
        return instance;
    }

    public boolean isMaxLevelReached(){ return FXGL.geti("level") == lastLevel; }

    /**
     * Fonction qui retourne le jeu de données d'un niveau indiqué
     * @param index Le niveau souhaité
     * @return LevelData
     */
    public LevelData getLevelData(int index){
        if(index < 0 || index > levelDataList.size()) return null;
        else return levelDataList.get(index);
    }


    /**
     * Fonction qui retourne le jeu de données du niveau actuel
     * @return LevelData
     */
    public LevelData getCurrentLevelData(){
        return levelDataList.get(FXGL.geti("level"));
    }

    public int getAmountOfEnemySpawned(){ return amountOfEnemySpawned; }

    // Setters

    /**
     * Fonction qui va charger le level {@code index} depuis notre liste de niveaux
     * @param index index du level à charger
     * @return Level
     */
    public void loadLevel(int index){
        if(index >= 0 && index <= levelDataList.size()) {
            /*if(index + 1 > lastLevel){
                FXGL.set("levelComplete", false);
                System.out.println("Vous venez de terminer le jeu !");
                loadLevel(0);
                return;
            } /*else if(FXGL.getb("levelComplete")){
                FXGL.inc("level", 1);
                index += 1;
            }*/

            amountOfEnemySpawned = 0;
            LevelData levelData = levelDataList.get(index);
            FXGL.set("levelComplete", false);
            FXGL.set("levelName", levelData.name());
            FXGL.set("level", index);
            FXGL.set("kill", 0);
            FXGL.set("money", levelData.money());

            FXGL.setLevelFromMap(levelData.map());
            spawnEntities();
        }
    }

    // Methods
    public void spawnEntities(){
        Entity rangeIndicator = FXGL.spawn("rangeIndicator");
        GameManager.getInstance().setRangeIndicatorEntity(rangeIndicator);
    }

    public void spawnEnemy(LevelData data){
        FXGL.runOnce(() -> {
            FXGL.run(() -> {
                FXGL.spawn("Enemy", -64, 320);
                this.amountOfEnemySpawned += 1;
            }, Duration.seconds(data.enemySpawnRate()), data.amountOfEnemy());
        }, Duration.seconds(3));
    }

    public void nextLevel() {
        if(isMaxLevelReached()) {
            System.out.println("Max level reached!");
            loadLevel(0);
        } else {
            loadLevel(FXGL.geti("level"));
        }
    }

}
