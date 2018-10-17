package application;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TTSPlay {
	public static void playTTS(String scentence) throws ParserConfigurationException, SAXException, IOException{
		PropertiesDAO pdao = new PropertiesDAO();
    	if(pdao.readTTSSwitch()){
	    	VoiceManager vm = VoiceManager.getInstance();
	        Voice voice = vm.getVoice("kevin16");
	        voice.allocate();
	        voice.speak(scentence);
    	}
	}
}
