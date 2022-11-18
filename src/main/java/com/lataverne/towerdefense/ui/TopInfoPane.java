package com.lataverne.towerdefense.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TopInfoPane extends HBox {

    private final Label levelLabel;
    private final Label goldLabel;
    private final Label killLabel;
    private final Label healthLabel;

    public TopInfoPane() {
        super(5);
        levelLabel = creatInfoLabel("ui/top/lv.png");
        goldLabel = creatInfoLabel("ui/top/gold.png");
        killLabel = creatInfoLabel("ui/top/kill.png");
        healthLabel = creatInfoLabel("ui/top/hp.png");
        getChildren().addAll(levelLabel, goldLabel, killLabel, healthLabel);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(5, 0, 0, 0));
        setStyle("-fx-background-image:url('assets/textures/ui/top/bar.png');");
        setPrefSize(448, 48);
        setLayoutX(351);

        killLabel.textProperty().bind(FXGL.getip("kill").asString());
        goldLabel.textProperty().bind(FXGL.getip("money").asString());
        healthLabel.textProperty().bind(FXGL.getip("hp").asString());
        levelLabel.textProperty().bind(FXGL.getip("level").asString());
    }

    private Label creatInfoLabel(String imageName) {
        Label label = new Label("0", FXGL.texture(imageName));
        label.setTextFill(Color.WHITE);
        label.setPrefWidth(80);
        label.setMaxWidth(80);
        label.setFont(Font.font(null, FontWeight.BOLD, 13));
        return label;
    }

}
