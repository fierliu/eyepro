package com.allan.controller;

import com.allan.domain.Config;
import com.allan.utils.Cache;
import com.allan.utils.TimeUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private Label lbTime;
    @FXML
    private Label lbTime1;
    @FXML
    private Label lbTime2, lbCountDown, lbCountDownTitle, nextBreak;
    @FXML
    private Button btnQuit, getTimeBtn, getTimeBtn1, getTimeBtn2;
    @FXML
    private Pane pane;
    @FXML
    private ToolBar toolBar;
    @FXML
    private AnchorPane anchorPane;
    @SuppressWarnings("unused")
    private Stage stage;
    private Config config;
    private Timeline animation;
    private String S = "";
    private long period = 300;//单位 秒 5*60
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //设置最大宽度占满
        pane.prefWidthProperty().bind(anchorPane.widthProperty());
        toolBar.prefWidthProperty().bind(pane.widthProperty());
        //count down handler
        config = Cache.getCache().getConfigCache();
        if(!StringUtils.isEmpty(config.getDayCountdown())){
            lbCountDownTitle.setText("Count Down");
            try {
                String daysStr = TimeUtil.getDifferenceDays(config.getDayCountdown());
                Integer days = Integer.valueOf(daysStr) < 0 ? 0 : Integer.valueOf(daysStr);
                lbCountDown.setText(days + " days away from " + config.getMission());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            lbCountDown.setText("");
            lbCountDownTitle.setText("");
        }
        this.config = Cache.getCache().getConfigCache();
        this.showNextBreak();

    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void getTimeBtn2Handler(ActionEvent event){
        lbTime2.setText(showTime());
    }
    public void getTimeBtn1Handler(ActionEvent event){
        lbTime1.setText(showTime());
    }
    public void getTimeBtnHandler(ActionEvent event){
        lbTime.setText(showTime());
    }

    public void btnQuitHandler(ActionEvent event){
        System.exit(0);
    }

    public void lbCountDownHandler(ActionEvent event){


    }
    public String showTime(){
        String nowTime = df.format(new Date());// new Date()为获取当前系统时间
        return nowTime;
    }

    public void showNextBreak() {
        period = Long.parseLong(config.getBreakTime()) * 60 * 1000;
        animation = new Timeline(new KeyFrame(Duration.millis(period), e -> timeLabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        //首次执行，在界面上显示时间
        timeLabel();
    }

    private void timeLabel() {
        Date date = new Date();
        long time = date.getTime();
        time += period;
        nextBreak.setText(df.format(new Date(time)));
    }

}
