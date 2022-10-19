package com.lataverne.towerdefense.components;

import com.lataverne.towerdefense.TowerDefense;
import com.lataverne.towerdefense.enums.EntityType;

public interface EntityInterface {

    Boolean update = TowerDefense.getUpdate();

    void print();
    void attack();

}