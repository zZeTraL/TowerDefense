package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.lataverne.towerdefense.EntityType;
import com.lataverne.towerdefense.data.TowerData;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class TowerComponent extends Component {

    // Jeu de données d'une tour
    private final TowerData towerData;
    // Timer pour savoir si je peux retirer une balle
    private LocalTimer bulletTimer;

    // Constructor
    public TowerComponent(){
        // On récupère la tour sélectionnée par le joueur
        int selectedTowerId = FXGL.geti("selectedTower");
        // Si aucune tour sélectionnée par le joueur (cas techniquement impossible, mais pour éviter l'erreur on le traite quand même)
        if(selectedTowerId == -1) selectedTowerId = 0;
        // On vient ainsi charger le json (données de la tour contenues dans ce fichier)
        towerData = FXGL.getAssetLoader().loadJSON("data/tower" + selectedTowerId + ".json", TowerData.class).get();
    }

    // Method from Component abstract class
    /**
     * <p>
     *     Fonction qui s'exécute lorsque l'entité apparait
     * </p>
     */
    @Override
    public void onAdded(){
        this.bulletTimer = FXGL.newLocalTimer();
        bulletTimer.capture();
    }

    /**
     * <p>
     *     Fonction qui s'exécute à chaque frame
     * </p>
     * @param tpf Time per Frame
     */
    @Override
    public void onUpdate(double tpf) {
        // On convertit le attackRate du fichier json de la tour en Duration pour pouvoir utiliser notre timer
        Duration attackRate = Duration.seconds(towerData.bulletData().attackRate());
        // Si la différence de temps quand la tour attaque et maintenant a été dépassé alors je peux attaquer sinon non
        if (!bulletTimer.elapsed(attackRate)) return;
        attack();
    }

    /**
     * <p>
     *     Fonction qui permet à notre de tour de tirer une balle
     * </p>
     */
    public void attack(){
        // Par souci de temps et (pour la facilité) on va tout simplement target l'entité (ENEMY) la plus proche
        FXGL.getGameWorld().getClosestEntity(entity, closestEntity ->
                // Si l'entité la plus proche est de type ENEMY et quelle et dans la range de notre tour
                        closestEntity.isType(EntityType.ENEMY)
                                && closestEntity.getPosition().distance(entity.getPosition()) < towerData.bulletData().range())
                // Si une telle entité existe alors je peux exécuter différentes actions
                .ifPresent(enemy -> {
                    // On récupère la position de la target
                    Point2D direction = enemy.getPosition().subtract(entity.getPosition());
                    // Je fais ainsi spawn ma balle qui est un ProjectileComponent cette balle se dirige
                    FXGL.spawn("Bullet", new SpawnData(
                            entity.getCenter().subtract(towerData.width() / 2.0, towerData.height() / 2.0))
                            .put("bulletData", towerData.bulletData())
                            .put("dir", direction)
                    );
                    // on get le temps actuel
                    bulletTimer.capture();
                });
    }
}