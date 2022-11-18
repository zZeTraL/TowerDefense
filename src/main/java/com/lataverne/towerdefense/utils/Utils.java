package com.lataverne.towerdefense.utils;

import java.util.Random;

public class Utils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double getRandomDouble(double min, double max){
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }


    public static double getFactor(double tpf){
        if(tpf > 0.0144){
            return 35.0;
        } else if(tpf >= 0.0100 && tpf <= 0.0120){
            return 45.0;
        } else if(tpf < 0.0100) {
            return 50.0;
        }
        return 50.0;
    }

}
