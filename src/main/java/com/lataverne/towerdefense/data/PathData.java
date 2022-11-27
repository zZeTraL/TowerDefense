package com.lataverne.towerdefense.data;

public record PathData(
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Informations :
         * PathData est un record (schéma) qui va contenir les principales
         * données associées à une entité de type EMPTY
         * Cette entité n'est pas affichée sur notre map mais va jouer un rôle
         * important, en effet, ici elle va nous aider à faire en sorte que
         * le joueur ne puisse pas poser des tours sur le chemin (ce qui sera
         * assez moche quand même) → pour remédier à ça cette entité va prendre
         * la forme d'un rectangle et on va donc checker si lorsque que l'on souhaite
         * placer une tour savoir si oui ou non on est en collision avec cette entité
         * avec la méthode ENTITY.isColliding(ENTITY)
         *
         * Concernant les variables :
         *
         * width : Largeur de la texture
         * height : Hauteur de la texture
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        int width,
        int height
) {}
