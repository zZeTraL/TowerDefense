package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.lataverne.towerdefense.data.LevelData;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    // Design pattern : SINGLETON
    private static LevelManager instance;

    /*
     * List qui va contenir que des LevelData
     * Cette liste que l'on crée va être important elle va nous permettre ici
     * de venir charger en mémoire toutes les données associés à tous les niveaux
     */
    private final List<LevelData> levelDataList;
    private final int lastLevel;
    // Nombre d'ennemis apparu actuellement
    private int amountOfEnemySpawned;

    // Constructor
    private LevelManager(){
        this.levelDataList = new ArrayList<>();
        this.lastLevel = 2;
        this.amountOfEnemySpawned = 0;
        // On vient charger les fichiers levelI.json qui contiennent les données de chaque niveau
        for (int i = 0; i < lastLevel; i++) {
            LevelData data;
            data = FXGL.getAssetLoader().loadJSON("levels/level" + i + ".json", LevelData.class).get();
            levelDataList.add(data);
        }
        // DEBUG
        // System.out.println(levelDataList);
    }

    // Getters

    /**
     * <p>
     *     Fonction qui retourne l'instance de la classe <br>
     *     <b>(si l'instance n'existe pas on l'a créé)</b>
     * </p>
     * @return LevelManager
     */
    public static LevelManager getInstance(){
        if(instance == null) instance = new LevelManager();
        return instance;
    }

    /**
     * <p>
     *     Fonction qui permet de savoir si le joueur a atteint le niveau max
     * </p>
     * @return Boolean
     */
    public boolean isMaxLevelReached(){ return FXGL.geti("level") == lastLevel; }

    /**
     * <p>
     *     Fonction qui retourne le jeu de données d'un niveau indiqué
     * </p>
     * @param index Le niveau souhaité
     * @return LevelData
     */
    public LevelData getLevelData(int index){
        if(index < 0 || index > levelDataList.size()) return null;
        else return levelDataList.get(index);
    }


    /**
     * <p>
     *     Fonction qui retourne le jeu de données du niveau actuel
     * </p>
     * @return LevelData
     */
    public LevelData getCurrentLevelData(){
        return levelDataList.get(FXGL.geti("level"));
    }

    /**
     * <p>
     *     Fonction qui retourne le nombre d'ennemis actuellement apparu
     * </p>
     * @return Integer
     */
    public int getAmountOfEnemySpawned(){ return amountOfEnemySpawned; }

    // Setters

    /**
     * <p>
     *     Fonction qui va charger le level {@code index} depuis notre liste de niveau
     * </p>
     * @param index index du level à charger
     */
    public void loadLevel(int index){
        // Si notre index est >= et inférieur ou égale à la taille de la liste levelDataList
        // Tout simplement on ne peut pas chargé un level négatif ou supérieur au niveau max
        if(index >= 0 && index <= levelDataList.size()) {
            // On vient nécessairement clear nos caches
            GameManager gameManager = GameManager.getInstance();
            gameManager.getEnemyCache().clear();
            gameManager.getTowerCache().clear();
            // On set à 0 le nombre d'ennemis qui sont actuellement apparu
            amountOfEnemySpawned = 0;
            // On récupère le jeu de données associé au niveau index
            LevelData levelData = levelDataList.get(index);
            // On set les variables globales en fonction du jeu de données
            FXGL.set("levelComplete", false);
            FXGL.set("levelName", levelData.name());
            FXGL.set("level", index);
            FXGL.set("kill", 0);
            FXGL.set("money", levelData.money());
            // On charge notre map → reset les entités sur le terrain, mais pas le cache
            FXGL.setLevelFromMap(levelData.map());
            // On fait spawn les entités qui par défaut sont nécessairement comme les boutons etc...
            spawnEntities();
        }
    }

    // Methods

    /**
     * <p>
     *     Fonction qui permet à chaque chargement de niveau de faire réapparaître notre rangeIndicator
     * </p>
     */
    public void spawnEntities(){
        Entity rangeIndicator = FXGL.spawn("rangeIndicator");
        GameManager.getInstance().setRangeIndicatorEntity(rangeIndicator);
    }

    /**
     * <p>
     *     Fonction qui permet de faire spawn les ennemis du niveau
     * </p>
     * @param data Jeu de données de notre niveau
     */
    public void spawnEnemy(LevelData data){
        FXGL.runOnce(() -> {
            FXGL.run(() -> {
                FXGL.spawn("Enemy", -64, 320);
                this.amountOfEnemySpawned += 1;
            }, Duration.seconds(data.enemySpawnRate()), data.amountOfEnemy());
        }, Duration.seconds(3));
    }

    /**
     * <p>
     *     Fonction qui permet de charger le prochain level tout en vérifiant si je n'ai pas atteint le niveau max
     * </p>
     */
    public void nextLevel() {
        if(isMaxLevelReached()) {
            System.out.println("Vous avez atteint le niveau maximum !");
            loadLevel(0);
        } else {
            System.out.println("Chargement du prochain niveau...");
            loadLevel(FXGL.geti("level"));
        }
    }

}
