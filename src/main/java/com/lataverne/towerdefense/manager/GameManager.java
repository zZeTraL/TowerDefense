package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
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



    // Design pattern : SINGLETON
    private static GameManager instance;
    // Manager
    private final LevelManager levelManager;
    private final TowerManager towerManager;
    private final FileManager fileManager;
    // Cache
    private final EnemyCache enemyCache;
    private final TowerCache towerCache;
    // Important variables
    private boolean canBuild;
    private boolean isWaveStarted;
    private boolean isGamePaused;
    // Range Indicator
    private Entity rangeIndicatorEntity;
    private RangeIndicatorComponent rangeIndicatorComponent;
    // Mouse position
    private Point2D mousePosition;
    private final Rectangle2D validPlace;

    // Constructor
    private GameManager() {
        // On vient instancier nos deux caches
        this.enemyCache = EnemyCache.getInstance();
        this.towerCache = TowerCache.getInstance();
        // On instancie ensuite les manager
        this.levelManager = LevelManager.getInstance();
        this.towerManager = TowerManager.getInstance();
        this.fileManager = FileManager.getInstance();

        /* Par défaut :
         * canBuild = true → le joueur peut poser des tours sur la map
         * isWaveStarted = false → le joueur peut lancer la vague d'ennemi
         * isGamePaused = false → le joueur peut mettre en pause le jeu (engine paused/resumed)
         */
        this.canBuild = true;
        this.isWaveStarted = false;
        this.isGamePaused = false;

        // Fenêtre de jeu (taille de la map (sans le HUD à droite))
        this.validPlace = new Rectangle2D(40, 0, 890, 600);
    }

    // Getters

    /**
     * <p>
     *     Fonction qui retourne l'instance de la classe <br>
     *     <b>(si l'instance n'existe pas on l'a créé)</b>
     * </p>
     * @return GameManager
     */
    public static GameManager getInstance(){
        if(instance == null) instance = new GameManager();
        return instance;
    }


    public LevelManager getLevelManager(){ return levelManager; }
    public TowerManager getTowerManager(){ return towerManager; }
    public FileManager getFileManager(){ return fileManager; }
    public EnemyCache getEnemyCache(){ return enemyCache; }
    public TowerCache getTowerCache(){ return towerCache; }

    /**
     * <p>
     *     Fonction qui permet de savoir si une vague est cours ou non
     * </p>
     * @return Boolean
     */
    public boolean isWaveStarted(){ return isWaveStarted; }
    /**
     * <p>
     *     Fonction qui permet de savoir si on peut construire une tour ou non
     * </p>
     * @return Boolean
     */
    public boolean isCanBuild(){ return canBuild; }

    /**
     * <p>
     *     Fonction qui retourne l'entité associé à notre range indicator
     * </p>
     * @return Entity
     */
    public Entity getRangeIndicatorEntity(){ return rangeIndicatorEntity; }

    // Setters

    /**
     * <p>
     *     Fonction qui permet de set l'entité du range indicator
     *     Utile lors d'un changement de niveau qui vient reset toutes les entités
     * </p>
     * @param entity Entité
     */
    public void setRangeIndicatorEntity(Entity entity){
        this.rangeIndicatorEntity = entity;
        this.rangeIndicatorComponent = rangeIndicatorEntity.getComponent(RangeIndicatorComponent.class);
        // On cache notre rangeIndicator (l'entité) si on n'est pas en train de placer une tour (être dans la zone de jeux (dans isValidPlace = rectangle cf. constructeur))
        hideRangeIndicator();
    }

    // Methods

    /**
     * <p> Fonction qui permet de mettre l'engine en pause </p>
     */
    public void pause(){
        if(!isGamePaused){
            isGamePaused = true;
            FXGL.getGameController().pauseEngine();
        } else {
            isGamePaused = false;
            FXGL.getGameController().resumeEngine();
        }
    }

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
     * <b>
     *     Remarque : cette fonction se déclenche lors d'un appuie sur le bouton play à droite dans le HUD
     * </b>
     */
    public void start(){
        // Si aucune vague d'ennemi n'est en cours
        if(!isWaveStarted){
            // Si notre joueur n'a pas atteint le niveau max
            if(!levelManager.isMaxLevelReached()){
                // Si notre joueur n'a pas terminé un level
                if(!FXGL.getb("levelComplete")){
                    // On set notre booléen sur true → une vague d'ennemi est donc en cours
                    isWaveStarted = true;
                    // On vient charger le LevelData associé au niveau actuel
                    LevelData levelData = levelManager.getCurrentLevelData();
                    // On vient faire apparaître les ennemis
                    levelManager.spawnEnemy(levelData);
                } else {
                    // On indique au joueur que le niveau est complété
                    System.out.println("Level complété... CLEAR ALL");
                    // On incrémente la var global level de 1
                    FXGL.inc("level", 1);
                    // On set la var global levelComplete à false
                    FXGL.set("levelComplete", false);
                    // On vient load notre prochain niveau
                    levelManager.nextLevel();
                }
            } else {
                // On recommence le jeu de 0
                levelManager.loadLevel(0);
            }
        } else {
            System.out.println("Une vague d'ennemis est en cours !");
        }
    }

    /**
     * <p>
     *     Fonction qui vérifie que la vague d'ennemis en cours est terminée ou non
     * </p>
     * <ul>
     *      <li> Si la vague d'ennemis est toujours en cours, le check n'abouti pas (false)                         </li>
     *      <li> Sinon (vague terminée = tous les ennemis sont morts) on vient set notre boolean en false
     *           nous pouvons ainsi lancer une autre vague                                                          </li>
     * </ul>
     */
    public void check(){
        // On récupère la vie de notre joueur
        int playerHealth = FXGL.geti("hp");
        // Si une vague est en cours
        if(isWaveStarted){
            // Si la vie de notre joueur n'est pas inférieur ou égale à 0
            if(playerHealth > 0){
                // Si la wave est terminé on a gagné !
                // DEBUG
                // System.out.println(levelManager.getAmountOfEnemySpawned());
                // System.out.println(levelManager.getCurrentLevelData().amountOfEnemy());
                // System.out.println(enemyCache.getCache().size());
                if(enemyCache.getCache().size() == 0 && levelManager.getAmountOfEnemySpawned() == levelManager.getCurrentLevelData().amountOfEnemy()){
                    isWaveStarted = false;
                    FXGL.set("levelComplete", true);
                    System.out.println("Level terminé !");
                } else {
                    System.out.println("Ils restent encore des ennemis à tuer");
                }
            } else {
                // Le joueur a perdu, on reset les variables et on reset le niveau pour qu'il puisse retenter sa chance
                FXGL.set("hp", 10);
                isWaveStarted = false;
                levelManager.loadLevel(0);
                System.out.println("Vous avez perdu ! (Le niveau vient d'être rechargé)");
            }
        } else {
            System.out.println("Aucune vague d'ennemis en cours !");
        }
    }

    /**
     * <p>
     *     Fonction qui est exécutée à chaque mouvement de souris dans la zone de jeu
     * </p>
     */
    public void onMouseMove(){
        // On récupère la valeur de notre variable globale
        // Cette valeur est un int
        int selectedTower = FXGL.geti("selectedTower");
        // Si une tour a été sélectionnée par notre joueur
        if(selectedTower != -1){
            // On récupère l'argent du joueur (qui oui est une variable globale)
            int money = FXGL.geti("money");
            // on récupère donc nécessairement le jeu de données associé à la tour sélectionnée
            TowerData towerData = towerManager.getTowerData(selectedTower);

            // On crée une variable qui stocker la taille de l'image (WxH)
            int[] towerSize = { towerData.width(), towerData.height() };
            // on sauvegarde la position de notre pointeur
            this.mousePosition = FXGL.getInput().getMousePositionWorld();

            // On met à joueur la position de notre range indicator
            // Le range indicator est une entité qui va apparaître lorsque le joueur
            // va vouloir placer une tour (pour lui donner la portée de celle-ci)
            rangeIndicatorComponent.updateIndicator(towerData);
            rangeIndicatorEntity.setX(mousePosition.getX() - towerSize[0] / 2.0);
            rangeIndicatorEntity.setY(mousePosition.getY() - towerSize[1] / 2.0);

            // Si notre rangeIndicator i.e. une entité est à l'intérieur de notre zone de jeu
            if(rangeIndicatorEntity.isWithin(validPlace)){
                // Si je n'ai pas assez d'argent pour acheter la tour
                if(money < towerData.cost()) {
                    //System.out.println("Vous n'avez pas assez d'argent !");
                    canBuild = false;
                    rangeIndicatorComponent.canBuild(false);
                } else {
                    List<Entity> towerEntities = FXGL.getGameWorld().getEntitiesByType(EntityType.TOWER);
                    List<Entity> emptyEntities = FXGL.getGameWorld().getEntitiesByType(EntityType.EMPTY);

                    for (Entity towerEntity : towerEntities) {
                        if (towerEntity.isColliding(rangeIndicatorEntity)) {
                            //System.out.print("Collision detected!");
                            canBuild = false;
                            rangeIndicatorComponent.canBuild(false);
                            return;
                        }
                    }

                    for (Entity emptyEntity : emptyEntities) {
                        if (emptyEntity.isColliding(rangeIndicatorEntity)) {
                            //System.out.print("Collision detected!");
                            canBuild = false;
                            rangeIndicatorComponent.canBuild(false);
                            return;
                        }
                    }

                    canBuild = true;
                    rangeIndicatorComponent.canBuild(true);
                }
            } else {
                // On est en dehors de la zone de jeu valide donc le joueur ne peut pas poser de tour ici
                canBuild = false;
                rangeIndicatorComponent.canBuild(false);
                hideRangeIndicator();
            }
        }
    }

    /**
     * <p>
     *     Fonction qui permet de construire une tour
     * </p>
     */
    public void buildTower(){
        // Si le joueur rempli les conditions pour construire une tour (d'après onMouseMove() seule fonction qui va venir change la valeur de canBuild)
        if(canBuild){
            // On récupère la tour que le joueur souhaite construire
            TowerData towerData = towerManager.getTowerData(FXGL.geti("selectedTower"));
            int[] towerSize = { towerData.width(), towerData.height() };
            // Si le joueur a assez d'argent pour acheter la tour
            if(towerData.cost() <= FXGL.geti("money")){
                // On vient mettre à jour son argent (décrémentation)
                FXGL.inc("money", -towerData.cost());
                // On fait spawn l'entité aux coordonnées de la souris - les dimensions de notre tour
                FXGL.spawn("Tower", mousePosition.getX() - towerSize[0] / 2.0, mousePosition.getY() - towerSize[1] / 2.0);
                // Bien évidement une fois qu'une tour est placé le joueur ne peut plus la placer au même endroit
                canBuild = false;
                rangeIndicatorComponent.canBuild(false);
            }
        }
    }

    /**
     * <p>
     *     Fonction qui va cacher le range indicator
     * </p>
     */
    public void hideRangeIndicator(){ rangeIndicatorComponent.hide(); }

}
