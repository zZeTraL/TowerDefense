package com.lataverne.towerdefense;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.lataverne.towerdefense.cache.EnemyCache;
import com.lataverne.towerdefense.cache.TowerCache;
import com.lataverne.towerdefense.components.EnemyComponent;
import com.lataverne.towerdefense.components.WayPointComponent;
import com.lataverne.towerdefense.manager.FileManager;
import com.lataverne.towerdefense.manager.GameManager;
import com.lataverne.towerdefense.manager.LevelManager;
import com.lataverne.towerdefense.manager.TowerManager;
import com.lataverne.towerdefense.ui.SelectTowerPane;
import com.lataverne.towerdefense.ui.TopInfoPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class TowerDefense extends GameApplication {

    private static GameManager gameManager;
    private EnemyCache enemyCache;

    @Override
    protected void initSettings(GameSettings settings){
        // Titre de la fenêtre
        settings.setTitle("Tower Defense");
        // Version du build
        settings.setVersion("build_0.1");
        // Largeur et hauteur de la fenêtre
        settings.setWidth(20 * 48 + 115);
        settings.setHeight(20 * 32);
        settings.setAppIcon("logo.jpg");
        // Curseur
        settings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));
        // Main menu
        settings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("hp", 10);
        vars.put("money", 0);
        vars.put("kill", 0);
        vars.put("level", 0);
        vars.put("selectedTower", -1);
        vars.put("levelComplete", false);
    }


    @Override
    protected void initGame(){
        // On définit un factory pour notre moteur FXGL
        FXGL.getGameWorld().addEntityFactory(new TowerDefenseFactory());
        // On instancie GameManager
        gameManager = GameManager.getInstance();
        enemyCache = gameManager.getEnemyCache();
        // On charge notre premier niveau
        gameManager.getLevelManager().loadLevel(0);
    }

    @Override
    protected void initPhysics(){
        // Si un ennemi entre en collision avec un WAYPOINT (entité qui est une ligne verticale ou horizontale)
        // Cette ligne va permettre tout simplement à notre ennemi de changer sa direction (up/down/right/left)
        FXGL.onCollision(EntityType.WAYPOINT, EntityType.ENEMY, (point, enemy) -> {
            // On récupère la variable associé au component du waypoint ici, c'est le component WayPointComponent
            String direction = point.getComponent(WayPointComponent.class).getDirection();
            // on change ainsi la variable direction de notre EnemyComponent
            enemy.getComponent(EnemyComponent.class).setDirection(direction);
        });

        // Si un ennemi entre en collision avec le point d'arrivé
        FXGL.onCollision(EntityType.ENEMY, EntityType.FINISH_POINT, (enemy, end) -> {
            // On retire 1hp au joueur
            FXGL.inc("hp", -1);
            // On retire l'entité arrivée du cache puis on la supprime du monde
            enemyCache.remove(enemy);
            enemy.removeFromWorld();
            // On check si la game n'est pas terminée
            gameManager.check();
        });

        // Si une balle entre en collision avec un ennemi
        FXGL.onCollision(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            // On get le component de l'ennemi (pour avoir accès à la vie de l'ennemi)
            EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);
            // On retire X vie à l'ennemi en fonction des dégâts de la balle
            enemyComponent.removeHealth(bullet.getObject("bulletData"));
            // lorsque je remove de la vie de l'ennemi (le booléen isDead du component sera soit false ou true si pas mort ou mort)
            // Si l'ennemi est mort (i.e vie <= 0)
            if (enemyComponent.isDead()) {
                // On incrémente le nombre de kill du joueur
                FXGL.inc("kill", 1);
                // On enlève l'ennemi du cache puis du monde
                enemyCache.remove(enemy);
                enemy.removeFromWorld();
                // On check si la game n'est pas terminée
                gameManager.check();
                return;
            }
            // Dans tous les cas, on retire la balle tirée du monde
            bullet.removeFromWorld();
        });
    }

    @Override
    protected void initUI(){
        TowerManager.createTowersBox();
        FXGL.addUINode(new TopInfoPane());
        FXGL.addUINode(new SelectTowerPane());
    }

    @Override
    protected void initInput(){
        // Listener qui s'activera quand on va bouger la souris
        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_MOVED, ignored -> {
            // Si je n'ai pas sélectionné une tour
            if (FXGL.geti("selectedTower") == -1) return;
            // Sinon on update notre range indicator
            gameManager.onMouseMove();
        });

        // Listener qui s'activera lors d'un clique du joueur
        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_CLICKED, action -> {
            // Si le joueur fait un clique droit, on enlève la selection en cours (selectedTower = -1)
            // selectedTower correspond à la tour sélectionnée par le joueur 0=flameTower 1=iceTower -1=UNDEFINED
            if(action.getButton() == MouseButton.SECONDARY){
                FXGL.set("selectedTower", -1);
                // On "cache" notre range indicator
                gameManager.hideRangeIndicator();
            } else {
                // Si j'ai sélectionné une tour je la build
                if(FXGL.geti("selectedTower") != -1) gameManager.buildTower();
            }
        });

        // DEBUG KEYS
        FXGL.onKeyDown(KeyCode.R, "restartLevel", () -> LevelManager.getInstance().loadLevel(0));
        FXGL.onKeyDown(KeyCode.L, "loadSave", () -> FileManager.getInstance().read());
        FXGL.onKeyDown(KeyCode.S, "save", () -> FileManager.getInstance().save());

        FXGL.onKeyDown(KeyCode.C, "printCache", () -> {
            TowerCache.getInstance().print();
            EnemyCache.getInstance().print();
        });

        /*FXGL.onKeyDown(KeyCode.K, "killAllEnemies", () -> {
            gameManager.check();
            if(gameManager.isWaveStarted()) enemyCache.clear();
        });*/
    }

    public static void main(String[] args) { launch(args); }
}
