package com.lataverne.towerdefense.components;

import com.almasb.fxgl.entity.component.Component;

public class WayPointComponent extends Component {

    private String direction;

    public WayPointComponent(String direction){
        this.direction = direction;
    }

    public String getDirection(){ return direction; }

}
