package com.lataverne.towerdefense.data;

public record BulletData(
        String img,
        int width,
        int height,
        int damage,
        int range,
        double speed,
        int attackRate
) {}
