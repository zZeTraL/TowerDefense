package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.data.LevelData;

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
        if(isWaveStarted){
            // Si la wave est terminé on a gagné !
            if(enemyCache.getCache().size() == 0){
                isWaveStarted = false;
                FXGL.inc("level", 1);
            } else {
                System.out.println("There are few enemies remaining to complete this level");
            }
        } else {
            System.out.println("No wave has been started yet");
        }
        //return enemyCache.getCache().size() == 0;
    }


}
