package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.lataverne.towerdefense.TowerDefense;
import com.lataverne.towerdefense.data.TowerData;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
        btn.setBackground(null);
        btn.setOnAction((ActionEvent e) ->  {
            System.out.println(towerData.name());
        });
        entity.getViewComponent().addChild(btn);
    }

    public ToggleGroup getTowerBtnGroup() { return toggleGroup; }

}
