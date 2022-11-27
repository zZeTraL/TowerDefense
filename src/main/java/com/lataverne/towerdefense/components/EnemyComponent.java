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

    private final EnemyData enemyData;
    private String direction;
    private boolean update;

    private int health;
    private boolean isDead = false;

    public EnemyComponent(){
        // On get le jeu de données du niveau actuel
        LevelData levelData = LevelManager.getInstance().getCurrentLevelData();
        enemyData = new EnemyData(levelData.enemyHealth(), levelData.enemySpeed());

        this.direction = levelData.startDirection();
        this.health = levelData.enemyHealth();
        this.update = true;
    }

    // Getters
    public String getDirection(){ return this.direction; }
    public EnemyData getEnemyData(){ return this.enemyData; }
    public boolean isDead(){ return this.isDead; }

    // Setters
    public void setDirection(String direction){ this.direction = direction; }
    public void setUpdate(boolean update){ this.update = update; }
    public void removeHealth(BulletData bulletData){
        int damage = bulletData.damage();
        if(this.health - damage < 0) isDead = true;
        else this.health -= damage;
    }

    @Override
    public void onUpdate(double tpf) {
        if(update){
            double speed = tpf * 50 * enemyData.moveSpeed();
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
