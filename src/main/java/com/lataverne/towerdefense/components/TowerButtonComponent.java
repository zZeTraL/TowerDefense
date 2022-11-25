package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.TowerDefense;
import com.lataverne.towerdefense.data.TowerData;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class TowerButtonComponent extends Component {

    private final TowerData towerData;
    private final ToggleButton btn;
    private final ToggleGroup toggleGroup;

    public TowerButtonComponent(TowerData towerData){
        this.towerData = towerData;
        this.toggleGroup = new ToggleGroup();
        this.btn = new ToggleButton();
        btn.setToggleGroup(toggleGroup);
        btn.setPrefSize(80, 80);
    }

    @Override
    public void onAdded(){
        btn.setTranslateX(-40);
        btn.setBackground(null);
        btn.setOnAction((ActionEvent e) ->  {
            FXGL.set("selectedTower", towerData.id());
            System.out.println(FXGL.geti("selectedTower"));
        });

        Label towerCostLabel = new Label(towerData.cost() + "");
        towerCostLabel.setAlignment(Pos.CENTER);
        towerCostLabel.setPrefSize(50, 15);
        towerCostLabel.setTranslateX(-25);
        towerCostLabel.setTranslateY(63);
        towerCostLabel.setStyle("-fx-background-color: #f2f2f2;-fx-background-radius: 12;-fx-text-fill: #1E8CB9;");

        entity.getViewComponent().addChild(towerCostLabel);
        entity.getViewComponent().addChild(btn);
    }

    public ToggleGroup getTowerBtnGroup() { return toggleGroup; }

}
