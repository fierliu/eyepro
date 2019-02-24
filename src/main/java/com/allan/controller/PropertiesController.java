package com.allan.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import com.allan.dao.PropertiesDAO;
import javafx.scene.control.*;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PropertiesController implements Initializable{
	@FXML
	private CheckBox  checkBoxPopUp;
	@FXML
	private RadioButton radioBtnPlayMusic, radioBtnPlayTTS1, radioBtnPlayTTS2,
			radioBtnPlayTTS3, radioBtnSilence;
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
	@FXML
	private ChoiceBox CBpopUpPosition,CBpopUpSize;
	PropertiesDAO pdao;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		pdao = PropertiesDAO.getInstance();
		//初始化弹窗
		checkBoxPopUp.setSelected(pdao.readPopUpSwitch());
		CBpopUpPosition.setValue(pdao.readPopUpPosition());
		CBpopUpSize.setValue(pdao.readPopUpSize());
		//初始化声音开关
		if(pdao.readMusicSwich()){
			radioBtnPlayMusic.setSelected(true);
		}else if(pdao.readSpvTTSSwitch()){
			radioBtnPlayTTS1.setSelected(true);
		}else if (pdao.readFreeTTSSwitch()){
			radioBtnPlayTTS2.setSelected(true);
		}else if(pdao.readSpvTTSSwitchCh()){
            radioBtnPlayTTS3.setSelected(true);
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

	public void checkBoxPopUpHandler(ActionEvent event){
		if(checkBoxPopUp.isSelected()){
			pdao.writePopUpSwitch("on");
		}
		else{
			pdao.writePopUpSwitch("off");
		}
	}

//-----------------声音handler--------------------
	public void radioBtnPlayMusicHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		if(radioBtnPlayMusic.isSelected()){
			pdao.writeMusicSound("on");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeFreeTTSSwitch("off");
			pdao.writeSpvTTSSwitchCh("off");
		}
	}
	//windows平台英文语音
	public void radioBtnPlaySpvTTSHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		if(radioBtnPlayTTS1.isSelected()){
			pdao.writeSpvTTSSwitch("on");
			pdao.writeMusicSound("off");
			pdao.writeFreeTTSSwitch("off");
			pdao.writeSpvTTSSwitchCh("off");
		}
	}
	//windows平台中文语音
	public void radioBtnPlaySpvTTSChHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		if(radioBtnPlayTTS3.isSelected()){
			pdao.writeSpvTTSSwitchCh("on");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeMusicSound("off");
			pdao.writeFreeTTSSwitch("off");
		}
	}

	public void radioBtnPlayFreeTTSHandler(ActionEvent event) throws IOException, SAXException, ParserConfigurationException {
		if(radioBtnPlayTTS2.isSelected()){
			pdao.writeFreeTTSSwitch("on");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeMusicSound("off");
			pdao.writeSpvTTSSwitchCh("off");
		}
	}

	public void radioBtnSilenceHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		if(radioBtnSilence.isSelected()){
			pdao.writeMusicSound("off");
			pdao.writeSpvTTSSwitch("off");
			pdao.writeFreeTTSSwitch("off");
			pdao.writeSpvTTSSwitchCh("off");
		}
	}


	public void btnNoticeWordHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
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

//		pdao = new PropertiesDAO();
		pdao.writeDayCountDown(s.toString());
		lbNoticeMissionDate.setText("Submitted, this will go into effect after the program restart.");
	}

	public void btnMissionNameHandler(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
		pdao.writeMission(textFieldMission.getText());
		lbNoticeMission.setText("Submitted, this will go into effect after the program restart.");
	}

	public void lbNoticeHandler(ActionEvent event){
		lbNoticeMission.setText("");
		lbNoticeWord.setText("");
	}

	public void CBpopUpPositionHandler(ActionEvent event){
		String value = (String) CBpopUpPosition.getValue();
		pdao.writePopUpPosition(value);
	}

	public void CBpopUpSizeHandler(ActionEvent event){
		String value = (String) CBpopUpSize.getValue();
		pdao.writePopUpSize(value);
	}
}
