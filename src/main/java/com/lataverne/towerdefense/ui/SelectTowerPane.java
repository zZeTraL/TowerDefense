package com.lataverne.towerdefense.ui;

import com.lataverne.towerdefense.manager.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SelectTowerPane extends VBox {

    public SelectTowerPane() {
        super(5);

        var playBtn = new Button("hexMenu/start", 97, 90, () -> GameManager.getInstance().start());
        playBtn.setTranslateX(-11);

        var pauseBtn = new Button("hexMenu/pause", 85, 71, () -> GameManager.getInstance().pause());
        pauseBtn.setTranslateX(-15);


        getChildren().addAll(playBtn, pauseBtn);
        setAlignment(Pos.BOTTOM_RIGHT);
        setPadding(new Insets(5, 0, 0, 0));
        setPrefWidth(20 * 48 + 115);
        setTranslateY(20 * 32 - 200);
    }
}
