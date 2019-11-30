package com.allan.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import com.allan.dao.PropertiesDAO;
import com.allan.domain.Property;
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
	private TextField textFieldMission;
	@FXML
	private TextArea textAreaNoticeWord;
	@FXML
	private DatePicker datePickerMission;
	private Property property;
	private PropertiesDAO pdao;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.property = Property.getInstance();
		this.pdao = PropertiesDAO.getInstance();
		//初始化弹窗
		checkBoxPopUp.setSelected(property.isPopUpSwitch());
//		CBpopUpPosition.setValue(pdao.readPopUpPosition());
//		CBpopUpSize.setValue(pdao.readPopUpSize());
		//初始化声音开关
		if(property.isMusicSwith()){
			radioBtnPlayMusic.setSelected(true);
		}else if(property.isSpvTTSSwitch()){
			radioBtnPlayTTS1.setSelected(true);
		}else if (property.isFreeTTSSwitch()){
			radioBtnPlayTTS2.setSelected(true);
		}else if(property.isSpvTTSSwitchCh()){
            radioBtnPlayTTS3.setSelected(true);
		}else {
			radioBtnSilence.setSelected(true);
		}
		//显示提醒文字
		textAreaNoticeWord.setText(property.getNoticeWord());
		textFieldMission.setText(property.getMission());
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
		LocalDate ld = LocalDate.parse(property.getDayCountDown());
		datePickerMission.setValue(ld);
	}

	public void btnConfirmHandler(ActionEvent event) {
		if(checkBoxPopUp.isSelected() && !property.isPopUpSwitch()){
			pdao.setPopUpSwitch(checkBoxPopUp.isSelected() ? "on" : "off");
        }
		//以下5个有且只有一个是true
		int selectSoundOrdinal = 0;
		if(radioBtnPlayMusic.isSelected()){
			selectSoundOrdinal = 1;
        }
		//windows平台英文语音
		if(radioBtnPlayTTS1.isSelected()){
		    selectSoundOrdinal = 2;
        }
		//freetts
		if(radioBtnPlayTTS2.isSelected()){
			selectSoundOrdinal = 3;
		}
		//windows平台中文语音
		if(radioBtnPlayTTS3.isSelected()){
		    selectSoundOrdinal = 4;
        }
		//silence
		if(radioBtnSilence.isSelected()){
		    selectSoundOrdinal = 5;
        }
		//找出property中5个开关是true的那个
		int propertySoundOrdinal = 0;
		if(property.isMusicSwith()){
			propertySoundOrdinal = 1;
		}
		if(property.isSpvTTSSwitch()){
			propertySoundOrdinal = 2;
		}
		if (property.isFreeTTSSwitch()){
			propertySoundOrdinal = 3;
		}
		if(property.isSpvTTSSwitchCh()){
			propertySoundOrdinal = 4;
		}
		if (property.isSilence()){
			propertySoundOrdinal = 5;
		}
		if(selectSoundOrdinal != propertySoundOrdinal){
			pdao.setSoundSwitch(selectSoundOrdinal);
		}

		//todo 注意NPE
		if (!textAreaNoticeWord.getText().equals(property.getNoticeWord())){
			pdao.setNoticeWord(textAreaNoticeWord.getText());
		}

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
		LocalDate localDate = datePickerMission.getValue();
		pdao.setDayCountDown(localDate.toString());

		pdao.setMission(textFieldMission.getText());

		pdao.saveXml();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText(" ");
		alert.setContentText("保存成功");
		alert.showAndWait();
	}

}
