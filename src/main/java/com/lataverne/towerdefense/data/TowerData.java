package com.lataverne.towerdefense.data;

public record TowerData(
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Informations :
         * BulletData est un record (schéma) qui va contenir les principales
         * données associées à une entité de type TOWER
         *
         * Concernant les variables :
         *
         * id : id de la tour
         * name : Nom de la tour
         * img : URL vers la texture qui va être affichée à l'écran
         * width : Largeur de la texture
         * height : Hauteur de la texture
         * cost : Prix pour pouvoir utiliser cette entité
         * bulletData : Données associées aux projectiles que la tour va tirer
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        int id,
        String name,
        String img,
        int width,
        int height,
        int cost,
        BulletData bulletData
) {}
