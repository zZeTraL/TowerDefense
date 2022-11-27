package com.lataverne.towerdefense.data;

public record EnemyData(
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Informations :
         * EnemyData est un record (schéma) qui va contenir les principales
         * données associées à une entité de type ENEMY
         *
         * Concernant les variables :
         *
         * health : Vie d'un ennemi
         * moveSpeed : Vitesse d'un ennemi
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        int health,
        double moveSpeed
) {}
