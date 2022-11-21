package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.lataverne.towerdefense.TowerDefense;
import com.lataverne.towerdefense.data.TowerData;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class TowerButtonComponent extends Component {

    private TowerData towerData;
    private final ToggleButton btn;

    public TowerButtonComponent(TowerData towerData){
        this.towerData = towerData;
        btn = new ToggleButton();
        btn.setPrefSize(80, 80);
    }

    @Override
    public void onAdded(){
        btn.setOnAction((ActionEvent e) ->  {
            System.out.println("he");
        });
        btn.setBackground(null);
        entity.getViewComponent().addChild(btn);



    }

}
