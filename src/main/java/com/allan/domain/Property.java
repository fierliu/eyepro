package com.allan.domain;

public class Property {
    public boolean isSilence() {
        return silence;
    }

    public void setSilence(boolean silence) {
        this.silence = silence;
    }

    private static class InnerObject{
        private static Property property = new Property();
    }
    public static Property getInstance(){
        return InnerObject.property;
    }

    private String breakTime;

    private boolean popUpSwitch;

    private String noticeWord;

    private boolean musicSwith;

    private boolean SpvTTSSwitch;

    private boolean SpvTTSSwitchCh;

    private boolean FreeTTSSwitch;

    private boolean silence;

    private String dayCountDown;

    private String mission;

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getNoticeWord() {
        return noticeWord;
    }

    public void setNoticeWord(String noticeWord) {
        this.noticeWord = noticeWord;
    }

    public String getDayCountDown() {
        return dayCountDown;
    }

    public void setDayCountDown(String dayCountDown) {
        this.dayCountDown = dayCountDown;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public boolean isPopUpSwitch() {
        return popUpSwitch;
    }

    public void setPopUpSwitch(boolean popUpSwitch) {
        this.popUpSwitch = popUpSwitch;
    }

    public boolean isFreeTTSSwitch() {
        return FreeTTSSwitch;
    }

    public void setFreeTTSSwitch(boolean freeTTSSwitch) {
        FreeTTSSwitch = freeTTSSwitch;
    }

    public boolean isMusicSwith() {
        return musicSwith;
    }

    public void setMusicSwith(boolean musicSwith) {
        this.musicSwith = musicSwith;
    }

    public boolean isSpvTTSSwitch() {
        return SpvTTSSwitch;
    }

    public void setSpvTTSSwitch(boolean spvTTSSwitch) {
        SpvTTSSwitch = spvTTSSwitch;
    }

    public boolean isSpvTTSSwitchCh() {
        return SpvTTSSwitchCh;
    }

    public void setSpvTTSSwitchCh(boolean spvTTSSwitchCh) {
        SpvTTSSwitchCh = spvTTSSwitchCh;
    }
}
