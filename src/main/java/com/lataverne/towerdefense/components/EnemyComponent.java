package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.BulletData;
import com.lataverne.towerdefense.data.EnemyData;
import com.lataverne.towerdefense.data.LevelData;
import com.lataverne.towerdefense.manager.LevelManager;

public class EnemyComponent extends Component {

    /*
     * Un component est une sorte de modèle/schéma qui va définir comment
     * mon entité lors de sa création se comportera sur le terrain lors de son spawn
     * comment va-t-elle agir/réagir ?
     * Ici EnemyComponent est une classe qui hérite de Component une classe de FXGL
     *      Un ennemi possède nécessairement de la vie et une vitesse
     *      Comme notre classe hérite de Component cela va nous permettre d'implémenter des fonctions comme onAdded/onUpdate
     *          Ainsi lors d'une frame en jeu EnemyComponent exécutera la fonction onUpdate
     *          onAdded s'exécute lors du spawn de l'entité
     * Ces fonctions que je vais pouvoir redéfinir dans notre classe vont nous être ultra méga important pour la suite
     */

    // Jeu de données d'un ennemi (définit en fonction du niveau)
    private final EnemyData enemyData;
    // Direction de l'entité (vers laquelle elle se dirige à chaque tpf)
    private String direction;
    // Variable qui nous permettre d'arrêter le déplacement de notre entité si on le souhaite (pause)
    private boolean update;
    // Vie de l'entité
    private int health;
    // Booléen indiquant si l'entité est morte ou no,
    private boolean isDead = false;

    // Constructor
    public EnemyComponent(){
        // On get le jeu de données du niveau actuel
        LevelData levelData = LevelManager.getInstance().getCurrentLevelData();
        // On crée un objet EnemyData en fonction des données prodiguée par le levelData
        enemyData = new EnemyData(levelData.enemyHealth(), levelData.enemySpeed());
        // On set en fonction de ces données les attributs
        this.direction = levelData.startDirection();
        this.health = levelData.enemyHealth();
        // Nécessairement l'entité va être update à chaque tpf (si false alors l'entité n'est pas update à chaque tpf)
        this.update = true;
    }

    // Getters

    /**
     * <p>
     *     Fonction qui retourne la direction de l'entité
     * </p>
     * @return String
     */
    public String getDirection(){ return this.direction; }

    /**
     * <p>
     *     Fonction qui retourne les données de l'ennemi
     * </p>
     * @return EnemyData
     */
    public EnemyData getEnemyData(){ return this.enemyData; }

    /**
     * <p>
     *     Fonction qui retourne si l'entité est morte ou non
     * </p>
     * @return
     */
    public boolean isDead(){ return this.isDead; }

    // Setters

    /**
     * <p>
     *     Fonction qui permet de set la direction de l'entité
     * </p>
     * @param direction
     */
    public void setDirection(String direction){ this.direction = direction; }

    /**
     * <p>
     *     Fonction qui set l'update de l'entité
     * </p>
     * @param update
     */
    public void setUpdate(boolean update){ this.update = update; }

    /**
     * <p>
     *     Fonction qui enlève de la vie à l'entité
     * </p>
     * @param bulletData Données de la balle qui touche notre entité (pour ainsi récupère les dégâts d'une balle)
     */
    public void removeHealth(BulletData bulletData){
        // On récupèrel les dégâts que provoque une balle
        int damage = bulletData.damage();
        // Si la vie de l'ennemi est inférieur à 0 (une fois la vie retirée) alors l'entité est morte isDead = true
        if(this.health - damage < 0) isDead = true;
        // Sinon on set bêtement la vie (décrémentation de damage)
        else this.health -= damage;
    }

    /**
     * <p>
     *     Fonction qui s'exécute à chaque frame
     * </p>
     * @param tpf Time per Frame
     */
    @Override
    public void onUpdate(double tpf) {
        // Si la variable update associée à l'entité est true i.e. l'entité est active et se déplace
        if(update){
            // On récupère sa vitesse couple avec le tpf par un facteur 50 (sinon ça serait trop lent)
            double speed = tpf * 50 * enemyData.moveSpeed();
            // Petit switch en fonction de la direction de l'entité
            switch (direction) {
                case "up" -> entity.translateY(-speed);
                case "down" -> entity.translateY(speed);
                case "right" -> entity.translateX(speed);
                case "left" -> entity.translateX(-speed);
                default -> {}
            }
        }
    }

}
