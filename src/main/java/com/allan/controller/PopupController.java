package com.allan.controller;

import com.allan.dao.PropertiesDAO;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PropertiesDAO propertiesDAO = new PropertiesDAO();
        lbMsg.setText(propertiesDAO.readNoticeWord());
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
