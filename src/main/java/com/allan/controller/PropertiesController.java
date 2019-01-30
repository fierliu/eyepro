package com.allan.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import com.allan.dao.PropertiesDAO;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PropertiesController implements Initializable{
	@FXML
	private CheckBox  checkBoxPopUp;
	@FXML
	private RadioButton radioBtnPlayMusic, radioBtnPlayTTS1, radioBtnPlayTTS2, radioBtnSilence;
	@FXML
	private ToggleGroup group;
	@FXML
	private TextField textFieldNoticeWord, textFieldMission;
	@FXML
	private Button btnNoticeWord, btnMissionName;
	@FXML
	private Label lbNoticeWord, lbNoticeMission, lbNoticeMissionDate;
	@FXML
	private DatePicker datePickerMission;
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
		}else if(pdao.readSpvTTSSwitch()){
			radioBtnPlayTTS1.setSelected(true);
		}else if (pdao.readFreeTTSSwitch()){
			radioBtnPlayTTS2.setSelected(true);
		}else {
			radioBtnSilence.setSelected(true);
		}
		//显示提醒文字
		textFieldNoticeWord.setText(pdao.readNoticeWord());
		textFieldMission.setText(pdao.readMission());
		//			设置早于当日的时间不能选择
		final Callback<DatePicker, DateCell> dayCellFactory =
				new Callback<DatePicker, DateCell>() {
					@Override
					public DateCell call(final DatePicker datePicker) {
						return new DateCell() {
							@Override
							public void updateItem(LocalDate item, boolean empty) {
								super.updateItem(item, empty);
								if (item.isBefore(LocalDate.now())) {
									setDisable(true);
									setStyle("-fx-background-color: #ffc0cb;");
								}
							}
						};
					}
		};
		datePickerMission.setDayCellFactory(dayCellFactory);

		//显示已设定的日期
		LocalDate ld = LocalDate.parse(pdao.readDayCountDown());
		datePickerMission.setValue(ld);
//		System.out.println(ld);

	}

	public void checkBoxPopUpHandler(ActionEvent event)
			throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(checkBoxPopUp.isSelected()){
			pdao.writePopUpSwitch("on");
		}
		else{
			pdao.writePopUpSwitch("off");
		}
	}

//-----------------声音handler--------------------
	public void radioBtnPlayMusicHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(radioBtnPlayMusic.isSelected()){
			pdao.writeMusicSound("on");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeFreeTTSSwitch("off");
		}else{
			pdao.writeMusicSound("off");
		}
	}

	public void radioBtnPlaySpvTTSHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(radioBtnPlayTTS1.isSelected()){
			pdao.writeSpvTTSSwitch("on");
			pdao.writeMusicSound("off");
			pdao.writeFreeTTSSwitch("off");
		}else{
			pdao.writeSpvTTSSwitch("off");
		}
	}

	public void radioBtnPlayFreeTTSHandler(ActionEvent event) throws IOException, SAXException, ParserConfigurationException {
		pdao = new PropertiesDAO();
		if(radioBtnPlayTTS2.isSelected()){
			pdao.writeFreeTTSSwitch("on");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeMusicSound("off");
		}else{
			pdao.writeFreeTTSSwitch("off");
		}
	}

	public void radioBtnSilenceHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		if(radioBtnSilence.isSelected()){
			pdao.writeMusicSound("off");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeFreeTTSSwitch("off");
		}
	}


	public void btnNoticeWordHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		pdao.writeNoticeWord(textFieldNoticeWord.getText());
		lbNoticeWord.setText("Submitted.");
	}

	public void datePickerMissionHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{


//			datePickerMission.setValue(LocalDate.now().plusDays(1));

//			设置日期的格式为yyyy-mm-dd;
			StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
				 return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		datePickerMission.setConverter(converter);
		LocalDate s = datePickerMission.getValue();
//		System.out.println(s);

		pdao = new PropertiesDAO();
		pdao.writeDayCountDown(s.toString());
		lbNoticeMissionDate.setText("Submitted, this will go into effect after the program restart.");
	}

	public void btnMissionNameHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao = new PropertiesDAO();
		pdao.writeMission(textFieldMission.getText());
		lbNoticeMission.setText("Submitted, this will go into effect after the program restart.");
	}

	public void lbNoticeHandler(ActionEvent event){
		lbNoticeMission.setText("");
		lbNoticeWord.setText("");
	}
}
