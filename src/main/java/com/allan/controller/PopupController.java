package com.allan.controller;

import com.allan.dao.PropertiesDAO;
import com.allan.domain.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {

    @FXML
    private Button btnOk;

    @FXML
    private Label lbMsg;

    private Stage stage;
    private Property property;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.property = Property.getInstance();
        lbMsg.setText(property.getNoticeWord());
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void btnOkHandler(ActionEvent event){
//        System.out.println("btnOkHandler...");
        stage.close();
    }

}
