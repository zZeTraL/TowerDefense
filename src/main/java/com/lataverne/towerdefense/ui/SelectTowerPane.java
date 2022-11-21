package com.lataverne.towerdefense.ui;

import com.lataverne.towerdefense.manager.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class SelectTowerPane extends VBox {

    private static final int paneWidth = 501;
    private static final int paneHeight = 341;
    private List<Label> towerLabelList;

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
