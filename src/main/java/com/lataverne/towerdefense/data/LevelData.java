package com.lataverne.towerdefense.data;

public record LevelData(
        String name,
        String map,
        int money,
        int spawnRate,
        int amountOfEnemy,
        double enemySpeed,
        int enemyHealth,
        String startDirection
) {}
