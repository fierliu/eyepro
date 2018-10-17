package application;

public class Switch {
	private static boolean popUpSwitch;
	private static boolean musicSwitch;
	private static boolean TTSSwitch;
	private static String noticeWord;
	public static boolean isPopUpSwitch(){
		return popUpSwitch;
	}
	public static void setPopUpSwitch(boolean bo){
		popUpSwitch = bo;
	}
	public static boolean isMusicSwitch() {
		return musicSwitch;
	}
	public static void setMusicSwitch(boolean musicSwitch) {
		Switch.musicSwitch = musicSwitch;
	}
	public static boolean isTTSSwitch() {
		return TTSSwitch;
	}
	public static void setTTSSwitch(boolean tTSSwitch) {
		TTSSwitch = tTSSwitch;
	}
	public static String getNoticeWord(){
		return noticeWord;
	}
	public static void setNoticeWord(String word){
		noticeWord = word;
	}
}
