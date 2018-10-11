package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

public class PropertiesController implements Initializable{
	@FXML
	private CheckBox CheckBoxPlaySound, checkBoxPopUp;
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
		CheckBoxPlaySound.setSelected(pdao.readMusicSwich());//初始化checkbox的选择值
	}

	public void checkBoxPlaySoundHandler(ActionEvent event)
			throws DocumentException, IOException, ParserConfigurationException, SAXException{
		pdao = new PropertiesDAO();
		if(CheckBoxPlaySound.isSelected()) pdao.writeMusicSound("on");
		else pdao.writeMusicSound("off");
	}

	public void checkBoxPopUpHandler(ActionEvent event)
			throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(checkBoxPopUp.isSelected()) pdao.writePopUpSwitch("on");
		else pdao.writePopUpSwitch("off");
	}
}
