package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import com.lataverne.towerdefense.data.TowerData;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class RangeIndicatorComponent extends Component {

    // Différents gradients si on peut build (cercle vert) sinon (cercle rouge)
    private final RadialGradient canBuildFill;
    private final RadialGradient cantBuildFill;
    // Texture de la tour au centre du cercle
    private Texture texture;
    // Cercle avec notre gradient radial
    private Circle circle;

    // Constructor
    public RangeIndicatorComponent(){
        canBuildFill = new RadialGradient(
                0.0, 0.0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.9, new Color(1.0, 0.0, 0.0, 0.0)),
                new Stop(1.0, new Color(0.0, 1.0, 0.39, 0.4)));
        cantBuildFill = new RadialGradient(
                0.0, 0.0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.9, new Color(1.0, 0.0, 0.0, 0.0)),
                new Stop(1.0, new Color(1.0, 0.0, 0.0, 0.38)));
    }

    /**
     * <p>
     *     Fonction qui s'exécute lorsque l'entité apparait
     * </p>
     */
    @Override
    public void onAdded() {
        // Je set une texture par défaut à notre entité sinon ERREUR
        texture = FXGL.texture("tower/flame/icon.png");
        // je définis ensuite une hitbox pour notre entité qui va être la largeur et hauteur de la texture
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(texture.getWidth(), texture.getHeight())));
        // Je crée notre cercle avec notre gradient radial
        circle = new Circle(100, cantBuildFill);
        circle.setTranslateX(texture.getWidth() / 2.0);
        circle.setTranslateY(texture.getHeight() / 2.0);
        // J'ajoute tout cela au viewComponent de notre entité
        entity.getViewComponent().addChild(texture);
        entity.getViewComponent().addChild(circle);
    }

    /**
     * <p>
     *     Fonction qui set le gradient radial du cercle en vert ou rouge en fonction du booléen {@code canBuild}
     * </p>
     * @param canBuild Booléen
     */
    public void canBuild(boolean canBuild) {
        circle.setFill(canBuild ? canBuildFill : cantBuildFill);
    }

    /**
     * <p>
     *     Fonction qui va mettre à jour les coordonnées de notre entité en fonction d'un jeu de données TowerData fournit
     *     en paramètre
     * </p>
     * @param towerData Jeu de données d'une tour
     */
    public void updateIndicator(TowerData towerData) {
        // On rédinit la texture (l'image) de notre entité (celle au centre du cercle)
        texture.setImage(FXGL.image(towerData.img()));
        circle.setTranslateX(texture.getWidth() / 2.0);
        circle.setTranslateY(texture.getHeight() / 2.0);
        // On set le radius du cercle car chaque balle n'a pas nécessairement la même portée
        circle.setRadius(towerData.bulletData().range());
    }

    /**
     * <p>
     *     Fonction qui permet de "cacher" notre entité
     * </p>
     */
    public void hide(){
        entity.setX(-1000);
        entity.setY(-1000);
    }
}
