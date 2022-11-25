package com.lataverne.towerdefense.data;

public record LevelData(
        // System data
        String name,
        String map,
        int money,

        // Wave data
        int amountOfEnemy,
        int scorePerKill,

        // Enemy data
        int enemyHealth,
        double enemySpeed,
        double enemySpawnRate,

        // First direction indication
        String startDirection,
        PathData[] pathData
) {}
