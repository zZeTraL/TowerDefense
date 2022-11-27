package com.lataverne.towerdefense.data;

public record BulletData(
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Informations :
         * BulletData est un record (schéma) qui va contenir les principales
         * données associées à une entité de type BULLET
         *
         * Concernant les variables :
         *
         * img : URL vers l'image de notre balle
         * width : Largeur de la texture
         * height : Hauteur de la texture
         * damage : Dégât de notre balle à l'impact
         * range : Porté de notre balle
         * speed : Vitesse de la balle
         * attackRate : Fréquence de tir associée à notre tour en fonction de son type de balle
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        String img,
        int width,
        int height,
        int damage,
        int range,
        double speed,
        int attackRate
) {}
