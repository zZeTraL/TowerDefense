package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.lataverne.towerdefense.EntityType;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.RangeIndicatorComponent;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.data.TowerData;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.List;

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
    private boolean pause;

    // range Indicator
    private Entity rangeIndicatorEntity;
    private RangeIndicatorComponent rangeIndicatorComponent;
    // mouse position
    private Point2D mousePosition;
    private Rectangle2D validPlace;

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
        this.pause = false;

        this.validPlace = new Rectangle2D(0, 0, 975, 600);
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

    public Entity getRangeIndicatorEntity(){ return rangeIndicatorEntity; }

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
                if (FXGL.geti("level") == 0) {
                    isWaveStarted = true;
                    LevelData levelData = levelManager.getCurrentLevelData();
                    levelManager.spawnEnemy(levelData);
                } else {
                    System.out.println("clear all !!!");
                    FXGL.set("levelComplete", false);
                    isWaveStarted = true;
                    levelManager.nextLevel();
                    levelManager.spawnEnemy(levelManager.getCurrentLevelData());
                    /*if(FXGL.getb("levelComplete")){
                        System.out.println("clear all !!!");
                        FXGL.set("levelComplete", false);
                    } else {
                        isWaveStarted = true;
                        levelManager.nextLevel();
                    }*/
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
                System.out.println(levelManager.getAmountOfEnemySpawned());
                System.out.println(levelManager.getCurrentLevelData().amountOfEnemy());
                System.out.println(enemyCache.getCache().size());
                if(enemyCache.getCache().size() == 0 && levelManager.getAmountOfEnemySpawned() == levelManager.getCurrentLevelData().amountOfEnemy()){
                    isWaveStarted = false;
                    FXGL.inc("level", 1);
                    FXGL.set("levelComplete", true);
                    System.out.println("Level complete!");
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
    }

    public void onMouseMove(){
        int selectedTower = FXGL.geti("selectedTower");
        if(selectedTower != -1){
            int money = FXGL.geti("money");
            TowerData towerData = towerManager.getTowerData(selectedTower);
            int[] towerSize = { towerData.width(), towerData.height() };
            this.mousePosition = FXGL.getInput().getMousePositionWorld();

            rangeIndicatorComponent.updateIndicator(towerManager.getTowerData(selectedTower));
            rangeIndicatorEntity.setX(mousePosition.getX() - towerSize[0] / 2.0);
            rangeIndicatorEntity.setY(mousePosition.getY() - towerSize[1] / 2.0);

            if(rangeIndicatorEntity.isWithin(validPlace)){
                // Si je n'ai pas assez d'argent pour acheter la tour
                if(money < towerData.cost()) {
                    System.out.println("Not enought money");
                    canBuild = false;
                    rangeIndicatorComponent.canBuild(false);
                } else {
                    //System.out.println(FXGL.getGameWorld().getSingleton(EntityType.EMPTY));
                    List<Entity> towerEntities = FXGL.getGameWorld().getEntitiesByType(EntityType.TOWER);
                    //System.out.print(towerEntities);
                    Entity emptyEntity = FXGL.getGameWorld().getSingleton(EntityType.EMPTY);

                    for (Entity towerEntity : towerEntities) {
                        if (towerEntity.isColliding(rangeIndicatorEntity)) {
                            //System.out.print("Collision detected!");
                            canBuild = false;
                            rangeIndicatorComponent.canBuild(false);
                            return;
                        }
                    }

                    if (rangeIndicatorEntity.isColliding(emptyEntity)) {
                        canBuild = false;
                        rangeIndicatorComponent.canBuild(false);
                        return;
                    }

                    canBuild = true;
                    rangeIndicatorComponent.canBuild(true);

                }
            } else {
                canBuild = false;
                rangeIndicatorComponent.canBuild(false);
                hideRangeIndicator();
            }



        }
    }

    public void buildTower(){
        if(canBuild){
            TowerData towerData = towerManager.getTowerData(FXGL.geti("selectedTower"));
            int[] towerSize = { towerData.width(), towerData.height() };
            if(towerData.cost() <= FXGL.geti("money")){
                FXGL.inc("money", -towerData.cost());
                FXGL.spawn("Tower", mousePosition.getX() - towerSize[0] / 2.0, mousePosition.getY() - towerSize[1] / 2.0);
                canBuild = false;
                rangeIndicatorComponent.canBuild(false);
            }
        }
    }

    public void hideRangeIndicator(){
        rangeIndicatorEntity.setX(-1000);
        rangeIndicatorEntity.setY(-1000);
    }

    public void pause(){
        if(!pause){
            pause = true;
            FXGL.getGameController().pauseEngine();
        } else {
            pause = false;
            FXGL.getGameController().resumeEngine();
        }
    }


}
