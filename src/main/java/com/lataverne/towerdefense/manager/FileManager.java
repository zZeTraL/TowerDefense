package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    // Design pattern : SINGLETON
    private static FileManager instance;

    // Variables importantes pour l'usage des fichiers
    // Path correspond à l'URL de destination de notre fichier
    private final String path;
    // dataFile correspond tout simplement à notre sauvegarde
    private final File dataFile;

    // Constructor

    private FileManager(){
        // On stocke le chemin d'accès i.e. l'endroit où on souhaite sauvegarder notre fichier
        this.path = new File("src/main/resources/assets/data").getAbsolutePath();
        // On crée une instance de File qui va être créé (après) au path voulu
        this.dataFile = new File(path + "/save.txt");

        // Attention lors de l'utilisation des fichiers, on peut avoir des erreurs IOException
        try {
            // Si notre fichier dataFile n'existe pas au path (chemin/URL indiqué)
            // (Retourne un booléen true = le fichier existe déjà sinon il n'existe pas)
            if(!dataFile.exists()) {
                // On va donc créer notre fichier le résultat de cette fonction nous retourne un booléen
                if(dataFile.createNewFile()) {
                    // La création du fichier a réussi
                    System.out.println("Le fichier de sauvegarde vient d'être crée");
                    // On sauvegarde, car sinon rien n'est écrit dans le fichier et que l'utilisateur charge la sauvegarde = ERREUR
                    save();
                } else {
                    // Erreur lors de la création du fichier de sauvegarde
                    System.out.println("Erreur lors de la création du fichier de sauvegarde !");
                }
            } else {
                // Fichier existe déjà
                System.out.println("Le fichier existe déjà !");
            }
        } catch (IOException e){
            // Résidu de l'erreur (informations concernant l'erreur)
            e.printStackTrace();
        }

        // DEBUG
        //System.out.println(path);
        //System.out.println(dataFile);

    }

    // Getters

    /**
     * <p>
     *     Fonction qui retourne l'instance de la classe <br>
     *     <b>(si l'instance n'existe pas on l'a créé)</b>
     * </p>
     * @return EnemyCache
     */
    public static FileManager getInstance(){
        if(instance == null) instance = new FileManager();
        return instance;
    }

    // Methods

    /**
     * <p>
     *     Fonction qui va sauvegarder le niveau actuel dans le fichier de sauvegarde
     *     Le joueur peut ainsi sauvegarder sa partie en appuyant sur le bouton S
     * </p>
     */
    public void save(){
        // On récupère le level actuel (du jeu/level auquel le joueur est actuellement)
        int level = FXGL.geti("level");
        // IOException → toujours quand on manipule les fichiers
        try {
            // On instancie un FileWriter qui va écrire à l'adresse indiquée
            FileWriter writer = new FileWriter(path + "/save.txt");
            // On écrit dans notre fichier
            writer.write(String.valueOf(level));
            // IMPORTANT on close le writer
            writer.close();
            System.out.println("Sauvegarde effectuée");
        } catch (IOException e) {
            // Résidu de l'erreur (informations concernant l'erreur)
            e.printStackTrace();
        }

    }

    /**
     * <p>
     *     Fonction qui va charger le fichier de sauvegarde
     *     Le joueur peut ainsi lancer sa sauvegarde en appuyant sur le bouton L
     * </p>
     */
    public void read(){
        // Si aucune vague d'ennemi est en cours, on peut charger notre sauvegarde
        if(!GameManager.getInstance().isWaveStarted()){
            try {
                // On instancie un Scanner qui va lire les entrées de notre fichier de sauvegarde ligne par ligne
                Scanner reader = new Scanner(dataFile);
                // On va set la variable level par ce que notre scanner va lire dans le fichier
                // Remarque : notre fichier ne contient une ligne donc pas besoin d'un while pour
                // lire toutes les entrées de notre fichier
                int savedLevel = Integer.parseInt(reader.nextLine());
                // On load le niveau sauvegardé
                LevelManager.getInstance().loadLevel(savedLevel);
                // On n'oublie pas de CLOSE le reader comme pour le writer dans l'autre fonction
                reader.close();
                System.out.println("Votre sauvegarde vient d'être chargée");
            } catch (IOException e) {
                // Résidu de l'erreur (informations concernant l'erreur)
                e.printStackTrace();
            }
        } else {
            System.out.println("Impossible de charger une sauvegarde lorsque une vague est en cours !");
        }
    }

}
