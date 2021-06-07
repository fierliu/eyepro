package com.allan.controller;

import com.allan.domain.Config;
import com.allan.utils.Cache;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class PopupController implements Initializable {

    @FXML
    private Label lbMsg, lbCountdown;

    private Stage stage;
    private Config config;

    private Timeline animation;
    private String S = "";
    private int tmp = 300;//单位 秒 5*60
    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.config = Cache.getCache().getConfigCache();
        lbMsg.setText(config.getNoticeText());
        clock();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void clock() {
        animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> timelabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void timelabel() {
        double minute = Math.floor(tmp / 60);
        double second = tmp % 60;
        tmp--;
        S = decimalFormat.format(minute) + ":" + decimalFormat.format(second);
        lbCountdown.setText(S);
        if (tmp == 0) {
            animation.stop();//停止计时
        }
    }

}
