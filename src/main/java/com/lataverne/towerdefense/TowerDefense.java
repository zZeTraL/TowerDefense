package com.lataverne.towerdefense;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class TowerDefense extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Tower Defense");
        settings.setVersion("build_0.1");
        settings.setWidth(480);
        settings.setHeight(800);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new TowerDefenseFactory());
        var level = FXGL.setLevelFromMap("tmx/level0.tmx");
    }

    @Override
    protected void initPhysics() {}

    @Override
    protected void onUpdate(double tpf) {}

    @Override
    protected void initUI(){
        Text text = new Text(100, 100, "text");
        FXGL.getGameScene().addUINode(text);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
