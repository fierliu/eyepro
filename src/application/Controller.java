package application;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class Controller implements Initializable{

	@FXML
	private Label lbTime;
	@FXML
	private Label lbTime1;
	@FXML
	private Label lbTime2;
	@FXML
	private Button btn, btnQuit;
	@FXML
	private ChoiceBox<Integer> choice;
	@FXML
	private RadioButton remind;
	@SuppressWarnings("unused")
	private Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	public void setStage(Stage stage){
		this.stage = stage;
	}
	public void setLabel(String s){
		lbTime.setText(s);
	}
	public void getTimeBtn2Handler(ActionEvent event){
		lbTime2.setText(showTime());
	}
	public void getTimeBtn1Handler(ActionEvent event){
		lbTime1.setText(showTime());
	}
	public void getTimeBtnHandler(ActionEvent event){
		setLabel(showTime());
	}

	public void btnQuitHandler(ActionEvent event){
		System.exit(0);
	}

	public String showTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowTime = df.format(new Date());// new Date()为获取当前系统时间
		return nowTime;
	}

}
