package com.lataverne.towerdefense.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.lataverne.towerdefense.TowerDefense;
import com.lataverne.towerdefense.data.TowerData;
import javafx.scene.control.ToggleButton;

public class TowerButtonComponent extends Component {
    private static AnimationChannel selectedBorder;
    private AnimatedTexture texture;
    private TowerData towerData;
    private final ToggleButton btn;

    public TowerButtonComponent() {
        selectedBorder = new AnimationChannel(FXGL.image("ui/right/selected_border.png"), 5, 80, 80, Duration.seconds(0.5), 0, 14);
        btn = new ToggleButton();
        btn.setToggleGroup(FXGL.<TowerDefense>getAppCast().getTowerBtnGroup());
        btn.setPrefSize(80, 80);
    }

}
