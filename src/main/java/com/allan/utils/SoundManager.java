package com.allan.utils;

import com.allan.dao.ConfigDao;
import com.allan.domain.Config;

import java.io.IOException;

public class SoundManager {

    public static final String HALF = "half";
    public static final String SHARP = "sharp";
    private static Config config = new ConfigDao().findAll();

    public static void playSound(String word){

        if("half".equals(word)){
            if("Y".equals(config.getMusicOn())) {
                try {
                    MusicPlay.playOnce("2 minutes.wav");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if("sharp".equals(word)){
            if("Y".equals(config.getMusicOn())) {
                try {
                    MusicPlay.playOnce("2 minutes.wav");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void playSoundTimesUp(){
        if("Y".equals(config.getMusicOn())) {
        }
    }

}
