package com.lataverne.towerdefense.data;

public record TowerData(
        String name,
        String img,
        int width,
        int height,

        int health,
        int radius,
        int cost,
        double attackRate,
        double damage
) {}
