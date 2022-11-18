package com.lataverne.towerdefense.data;

public record TowerData(
        int health,
        int radius,
        int cost,
        double attackRate,
        double damage
) {}
