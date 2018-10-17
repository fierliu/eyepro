package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class PropertiesController implements Initializable{
	@FXML
	private CheckBox  checkBoxPopUp;
	@FXML
	private RadioButton radioBtnPlayMusic, radioBtnPlayTTS, radioBtnSilence;
	@FXML
	private ToggleGroup group;
	@FXML
	private TextField textFieldNoticeWord;
	@FXML
	private Button btnNoticeWord;
	@FXML
	private Label lbNoticeWord;
	PropertiesDAO pdao;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

			try {
				pdao = new PropertiesDAO();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		checkBoxPopUp.setSelected(pdao.readPopUpSwitch());

		if(pdao.readMusicSwich()){
			radioBtnPlayMusic.setSelected(true);
		}else if(pdao.readTTSSwitch()){
			radioBtnPlayTTS.setSelected(true);
		}else{
			radioBtnSilence.setSelected(true);
		}
	}

	public void checkBoxPopUpHandler(ActionEvent event)
			throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(checkBoxPopUp.isSelected()){
			pdao.writePopUpSwitch("on");
			Switch.setPopUpSwitch(true);
		}
		else{
			pdao.writePopUpSwitch("off");
			Switch.setPopUpSwitch(false);
		}
	}

//-----------------声音handler--------------------
	public void radioBtnPlayMusicHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(radioBtnPlayMusic.isSelected()){
			pdao.writeMusicSound("on");
			Switch.setMusicSwitch(true);
			pdao.writeTTSSwitch("off");
			Switch.setTTSSwitch(false);
		}else{
			pdao.writeMusicSound("off");
			Switch.setMusicSwitch(false);
		}
	}

	public void radioBtnPlayTTSHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(radioBtnPlayTTS.isSelected()){
			pdao.writeTTSSwitch("on");
			Switch.setTTSSwitch(true);
			pdao.writeMusicSound("off");
			Switch.setMusicSwitch(false);
		}else{
			pdao.writeTTSSwitch("off");
			Switch.setTTSSwitch(false);
		}
	}

	public void radioBtnSilenceHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(radioBtnSilence.isSelected()){
			pdao.writeMusicSound("off");
			Switch.setMusicSwitch(false);
			pdao.writeTTSSwitch("off");
			Switch.setTTSSwitch(false);
		}
	}

	public void btnNoticeWordHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		pdao.writeNoticeWord(textFieldNoticeWord.getText());
		Switch.setNoticeWord(textFieldNoticeWord.getText());
		lbNoticeWord.setText("Submitted.");
	}

}
