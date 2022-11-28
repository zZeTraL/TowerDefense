package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.data.TowerData;
import com.lataverne.towerdefense.manager.GameManager;
import com.lataverne.towerdefense.manager.TowerManager;
import javafx.geometry.Point2D;

public class BulletComponent extends Component {

    private final TowerManager towerManager;
    // position = la position de la balle (qui est une entité)
    private Point2D position;
    // range = la portée de la balle
    private int range;

    // Constructor
    public BulletComponent(){
        // On récupère l'instance de GameManager
        this.towerManager = GameManager.getInstance().getTowerManager();
    }

    /**
     * <p>
     *     Fonction qui s'exécute lorsque l'entité apparait
     * </p>
     */
    @Override
    public void onAdded() {
        int selectedTower = FXGL.geti("selectedTower");
        if(selectedTower == -1) selectedTower = 0;
        TowerData towerData = towerManager.getTowerData(selectedTower);
        this.position = entity.getPosition();
        this.range = towerData.bulletData().range();
    }


    /**
     * <p>
     *     Fonction qui s'exécute à chaque frame
     * </p>
     * @param tpf Time per Frame
     */
    @Override
    public void onUpdate(double tpf) {
        // On récupère les coordonnées de notre balle (entité)
        Point2D newPosition = entity.getPosition();
        // Si la distance entre la nouvelle position de l'entité et son ancienne
        // est supérieure à la range de la balle
        if (newPosition.distance(position) > range) {
            // Si l'entité est active donc existe dans le monde
            if (entity.isActive()) {
                // On vient la supprimer car on est out of range
                entity.removeFromWorld();
            }
        }
    }


}
