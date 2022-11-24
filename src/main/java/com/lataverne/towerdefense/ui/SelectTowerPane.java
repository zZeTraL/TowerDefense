package com.lataverne.towerdefense.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.lataverne.towerdefense.manager.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SelectTowerPane extends VBox {

    public SelectTowerPane() {
        super(5);
        var playBtn = new PlayButton("hexMenu/start", 97, 90, () -> GameManager.getInstance().start());
        playBtn.setPrefSize(97, 90);
        playBtn.setTranslateX(-11);
        getChildren().addAll(playBtn);
        setAlignment(Pos.BOTTOM_RIGHT);
        setPadding(new Insets(5, 0, 0, 0));
        setPrefWidth(20 * 50 + 115);
        setTranslateY(15 * 50 - 125);
    }
}
