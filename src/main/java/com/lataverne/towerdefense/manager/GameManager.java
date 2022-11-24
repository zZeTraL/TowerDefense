package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.RangeIndicatorComponent;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.data.TowerData;
import javafx.geometry.Point2D;

public class GameManager {

    // Manager
    private static GameManager instance;
    private LevelManager levelManager;
    private TowerManager towerManager;

    // Cache
    private EnemyCache enemyCache;
    private TowerCache towerCache;

    // Important variables
    private boolean canBuild;
    private boolean isWaveStarted;

    private Entity rangeIndicatorEntity;
    private RangeIndicatorComponent rangeIndicatorComponent;

    // Constructor
    private GameManager(){
        // Instantiate each cache
        this.enemyCache = EnemyCache.getInstance();
        this.towerCache = TowerCache.getInstance();
        // Instantiate each useful manager
        this.levelManager = LevelManager.getInstance();
        this.towerManager = TowerManager.getInstance();
        // Attributes
        this.canBuild = true;
        this.isWaveStarted = false;
    }

    // Getters
    public static GameManager getInstance(){
        if(instance == null) instance = new GameManager();
        return instance;
    }

    public LevelManager getLevelManager(){ return levelManager; }
    public TowerManager getTowerManager(){ return towerManager; }
    public EnemyCache getEnemyCache(){ return enemyCache; }
    public TowerCache getTowerCache(){ return towerCache; }

    public boolean isWaveStarted(){ return isWaveStarted; }
    public boolean isCanBuild(){ return canBuild; }

    // Setters
    public void setRangeIndicatorEntity(Entity entity){
        this.rangeIndicatorEntity = entity;
        this.rangeIndicatorComponent = rangeIndicatorEntity.getComponent(RangeIndicatorComponent.class);
        hideRangeIndicator();
    }

    // Methods

    /**
     * <p>
     *     Fonction qui permet lorsque l'on clique sur le bouton play de lancer la wave
     *     suivant certaines conditions :
     * </p>
     * <ul>
     *      <li> Si la vague d'ennemis est en cours, on check si tous les enemies de la wave sont morts </li>
     *      <li> Sinon la condition est rempli, car aucune vague en cours                            </li>
     *      <li> On check si le joueur n'a pas atteint le niveau maximal                             </li>
     * </ul>
     */
    public void start(){
        if(!isWaveStarted){
            if(!levelManager.isMaxLevelReached()){
                isWaveStarted = true;
                if (FXGL.geti("level") == 0) {
                    LevelData levelData = levelManager.getCurrentLevelData();
                    levelManager.spawnEnemy(levelData);
                } else {
                    levelManager.nextLevel();
                }
            } else {
                System.out.println("Max Level Reached");
            }
        } else {
            System.out.println("Wave is currently ongoing");
        }
    }

    /**
     * <p>
     *     Fonction qui vérifie que la vague d'ennemis en cours est terminée ou non
     * </p>
     * <ul>
     *      <li> Si la vague d'ennemis est toujours en cours, le check n'abouti pas                         </li>
     *      <li> Sinon (vague terminée = tous les ennemis sont morts) on vient set notre boolean en false
     *           nous pouvons ainsi lancer une autre vague                                                  </li>
     * </ul>
     */
    public void check(){
        int playerHealth = FXGL.geti("hp");
        if(isWaveStarted){
            if(playerHealth != 0){
                // Si la wave est terminé on a gagné !
                if(enemyCache.getCache().size() == 0){
                    isWaveStarted = false;
                    FXGL.inc("level", 1);
                } else {
                    System.out.println("There are few enemies remaining to complete this level");
                }
            } else {
                isWaveStarted = false;
                enemyCache.getCache().forEach((key, value) -> key.removeFromWorld());
                enemyCache.getCache().clear();
                System.out.println("PLAYER HAS 0 HP !!!");
            }
        } else {
            System.out.println("No wave has been started yet");
        }
        //return enemyCache.getCache().size() == 0;
    }

    public void onMouseMove(){
        int selectedTower = FXGL.geti("selectedTower");
        if(selectedTower != -1){
            int money = FXGL.geti("money");
            TowerData towerData = towerManager.getTowerData(selectedTower);

            // Si je n'ai pas assez d'argent pour acheter la tour
            if(money < towerData.cost()) {
                System.out.println("Not enought money");
            }

            int[] towerSize = { towerData.width(), towerData.height() };
            Point2D position = FXGL.getInput().getMousePositionWorld();
            double[] coordinates = { position.getX(), position.getY() };

            rangeIndicatorComponent.updateIndicator(towerManager.getTowerData(selectedTower));

            rangeIndicatorEntity.setX(coordinates[0] - towerSize[0] / 2.0);
            rangeIndicatorEntity.setY(coordinates[1] - towerSize[1] / 2.0);

        }

    }

    public void buildTower(){
        if(canBuild){

        }
    }

    public void hideRangeIndicator(){
        rangeIndicatorEntity.setX(-1000);
        rangeIndicatorEntity.setY(-1000);
    }


}
