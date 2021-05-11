package com.allan.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.allan.dao.ConfigDao;
import com.allan.domain.Config;
import com.allan.utils.Cache;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PropertiesController implements Initializable{
    @FXML
    private CheckBox  checkBoxPopUp;
    @FXML
    private RadioButton radioBtnPlayMusic, radioBtnSilence;
    @FXML
    private TextField textFieldMission;
    @FXML
    private TextArea textAreaNoticeWord;
    @FXML
    private DatePicker datePickerMission;
    private Config config;
    private ConfigDao configDao;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.config = Cache.getCache().getConfigCache();
        this.configDao = new ConfigDao();
        //初始化弹窗
        checkBoxPopUp.setSelected("Y".equals(config.getPopupOn()));
        //初始化声音开关
        if("Y".equals(config.getMusicOn())){
            radioBtnPlayMusic.setSelected(true);
        }
        {
            radioBtnSilence.setSelected(true);
        }
        //显示提醒文字
        textAreaNoticeWord.setText(config.getNoticeText());
        textFieldMission.setText(config.getMission());
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
        LocalDate ld = LocalDate.parse(config.getDayCountdown());
        datePickerMission.setValue(ld);
    }

    public void btnConfirmHandler(ActionEvent event) {
        if(checkBoxPopUp.isSelected() && !"N".equals(config.getPopupOn())){
            Config config = new Config();
            config.setPopupOn(checkBoxPopUp.isSelected() ? "Y" : "N");
            configDao.updateConfig(config);
        }
        //以下5个有且只有一个是true
        Config configParam = new Config();
        if(radioBtnPlayMusic.isSelected()){
            configParam.setMusicOn("Y");
        }
        //silence
        if(radioBtnSilence.isSelected()){
            configParam.setMute("Y");
        }

        if (!textAreaNoticeWord.getText().equals(this.config.getNoticeText())){
            configParam.setNoticeText(textAreaNoticeWord.getText());
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
        configParam.setDayCountdown(localDate.toString());
        configParam.setMission(textFieldMission.getText());

        //先把5个声音开始都置为N
        Config configN = new Config();
        configN.setMute("N");
        configN.setMusicOn("N");
        configDao.updateConfig(configN);

        configDao.updateConfig(configParam);

        //重新加载配置到内存
        configDao.load();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(" ");
        alert.setContentText("保存成功");
        alert.showAndWait();
    }

}
