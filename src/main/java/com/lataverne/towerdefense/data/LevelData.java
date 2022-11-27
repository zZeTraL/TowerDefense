package com.lataverne.towerdefense.data;

public record LevelData(
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Informations :
         * LevelData est un record i.e contient/définit un schéma de donnée
         * Le principal objectif d'un record est de proposer une manière de représenter
         * ces données directement sans créer comme d'habitude la classe avec ses getters setters
         * Ici → on ne dispose que de getters (On ne peut pas set des attributs d'un record !!)
         *
         * Concernant les variables :
         *
         * name : Nom du niveau
         * map : URL vers la map
         * money : argent du joueur lors du lancement du niveau
         * amountOfEnemy : nombre d'ennemis que le level va faire spawn
         * enemyHealth : Vie d'un ennemi
         * enemySpeed : Vitesse d'un ennemi
         * enemySpawnRate : Fréquence d'apparition des ennemis
         * startDirection : (Sert si nous avions eu le temps de faire les animations pour set le bon sprite)
         *                  Mais cette variable sert exclusivement à attribuer une direction de départ à notre ennemi
         *                  (gauche / droite / bas / haut)
         * pathData : Array qui va contenir des objets de type : PathData → qui permettent de définir le chemin
         *                                                                    pour les collisions
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        // Important variables
        String name,
        String map,
        int money,
        // Wave data
        int amountOfEnemy,
            // Enemy data
        int enemyHealth,
        double enemySpeed,
        double enemySpawnRate,
        // Enemy start direction (where does the enemy goes?)
        String startDirection,
        // pathData is an array which contains PathData objects (with those object we can define our path and collision)
        PathData[] pathData
) {}
