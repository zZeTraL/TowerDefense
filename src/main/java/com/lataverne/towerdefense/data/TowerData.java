package com.lataverne.towerdefense.data;

public record TowerData(
        int id,
        String name,
        String img,
        int width,
        int height,

        int health,
        int cost,
        BulletData bulletData
) {}
