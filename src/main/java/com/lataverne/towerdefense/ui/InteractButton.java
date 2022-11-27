package com.lataverne.towerdefense.ui;

import javafx.scene.control.Label;

public class InteractButton extends Label {

    public InteractButton(String imgName, int w, int h, Runnable action) {
        setPrefSize(w, h);
        setStyle("-fx-background-image: url('assets/textures/ui/" + imgName + ".png')");
        hoverProperty().addListener((ob, ov, nv) -> {
            String imageName = imgName + (nv ? "Hover.png')" : ".png')");
            setStyle("-fx-background-image: url('assets/textures/ui/" + imageName);
        });
        setOnMouseClicked(e -> action.run());
        managedProperty().bind(visibleProperty());
    }
}