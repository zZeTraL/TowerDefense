package com.lataverne.towerdefense.manager;

import com.almasb.fxgl.dsl.FXGL;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    private static FileManager instance;
    private String path;
    private File dataFile;

    private FileManager(){
        this.path = new File("src/main/resources/assets/data").getAbsolutePath();
        System.out.println(path);
        this.dataFile = new File(path + "/save.txt");
        try {
            if(!dataFile.exists()) {
                if(dataFile.createNewFile()) {
                    System.out.println("Le fichier de sauvegarde vient d'être crée");
                } else {
                    System.out.println("Erreur lors de la création du fichier de sauvegarde !");
                }
            } else {
                System.out.println("Le fichier existe déjà !");
            }
        } catch (IOException ignored){
            System.out.println("IOEXCEPTION");
        }
    }

    // Getters
    public static FileManager getInstance(){
        if(instance == null) instance = new FileManager();
        return instance;
    }

    // Methods
    public void save(){
        int level = FXGL.geti("level");
        try {
            FileWriter writer = new FileWriter(path + "/save.txt");
            writer.write(String.valueOf(level));
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException ignored) {
            System.out.println("IOEXCEPTION");
        }

    }

    public void read(){
        try {
            Scanner reader = new Scanner(dataFile);
            int level = Integer.parseInt(reader.nextLine());
            FXGL.set("level", level);
            LevelManager.getInstance().loadLevel(level);
            //GameManager.getInstance().setWaveStarted(false);
            reader.close();
            System.out.println("Successfully read the file.");
        } catch (IOException ignored) {
            System.out.println("IOEXCEPTION");
        }
    }

}
