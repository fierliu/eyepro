package com.allan.utils;

import com.allan.dao.ConfigDao;
import com.allan.domain.Config;

import java.io.IOException;

public class SoundManager {

    public static final String HALF = "half";
    public static final String SHARP = "sharp";
    private static Config config = new ConfigDao().findAll();

    public static void playSound(String word){

        String chTimeString = SoundManager.getTimeString("ch");
        String enTimeString = SoundManager.getTimeString("en");
        if("half".equals(word)){
            if("Y".equals(config.getMusicOn())) {
                try {
                    MusicPlay.playOnce("2 minutes.wav");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if("Y".equals(config.getSpvttsOn()))
                TTS.playSpVoiceTTS(enTimeString);
            else if("Y".equals(config.getFreettsOn()))
                TTS.playFreeTTS(enTimeString);
            else if("Y".equals(config.getSpvttsChOn()))
                TTS.playSpVoiceTTS(chTimeString);
        }
        if("sharp".equals(word)){
            if("Y".equals(config.getMusicOn())) {
                try {
                    MusicPlay.playOnce("2 minutes.wav");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if("Y".equals(config.getSpvttsOn()))
                TTS.playSpVoiceTTS(enTimeString);
            else if("Y".equals(config.getFreettsOn()))
                TTS.playFreeTTS(enTimeString);
            else if("Y".equals(config.getSpvttsChOn()))
                TTS.playSpVoiceTTS(chTimeString);
        }

    }

    public static void playSoundTimesUp(){
        if("Y".equals(config.getSpvttsChOn())) TTS.playSpVoiceTTS("时间到。");
        else if("Y".equals(config.getMusicOn())) {
            try {
                MusicPlay.playOnce("Devils_Never_Cry.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if ("Y".equals(config.getSpvttsOn())) TTS.playSpVoiceTTS("Time's up!");
        else if("Y".equals(config.getFreettsOn())) TTS.playSpVoiceTTS("Time's up!");


    }

    private static String getTimeString(String language){
        String time = TimeUtil.getSharpAndHalfTime();
        if("ch".equals(language)){
            time = "现在时间是 "+ time;
        }
        else if("en".equals(language)){
            //after 12:00, the sharp time spoke by spvoice can pronounce 'hundred'
            if(time.substring(2).equals(":00"))
                time = "It's "+ time.substring(0,2) + " o'clock now";
            //before 10:00, the sharp time spoke by spvoice can pronounce 'zero'
            if(time.substring(0,1).equals("0"))
                time = "It's "+ time.substring(1)+ " now";
        }
        return time;
    }

//    public static void main(String[] args) {
//        SoundManager.playSound(SoundManager.HALF);
//    }
}
