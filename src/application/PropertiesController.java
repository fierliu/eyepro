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
	private CheckBox CheckBoxPlaySound;
	PropertiesDAO pdao;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			pdao = new PropertiesDAO();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CheckBoxPlaySound.setSelected(pdao.readMusicSwich());//初始化checkbox的选择值
	}

	public void checkBoxPlaySoundHandler(ActionEvent event) throws DocumentException, IOException, ParserConfigurationException, SAXException{
		pdao = new PropertiesDAO();
		if(CheckBoxPlaySound.isSelected()){
//			System.out.println("selected");
			pdao.writeMusicSound("on");
		}
		else{
//			System.out.println("deselected");
			pdao.writeMusicSound("off");
		}
	}
}
