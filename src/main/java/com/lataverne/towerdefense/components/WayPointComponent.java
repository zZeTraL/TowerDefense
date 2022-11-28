package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;

public class WayPointComponent extends Component {

    // direction dans laquelle le waypoint va orienter notre ennemi quand il va entrer en contact
    // avec lui (EHHH ouii WayPoint est une entité c'est une ligne qui va orienter l'ennemi dans une direction)
    private final String direction;

    // Constructor
    public WayPointComponent(String direction){
        this.direction = direction;
    }

    /**
     * <p>
     *     Fonction qui permet de récupère l'orientation du waypoint
     * </p>
     * @return String
     */
    public String getDirection(){ return direction; }

}
