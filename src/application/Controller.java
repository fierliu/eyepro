package application;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller implements Initializable{

	@FXML
	private Label lbTime;
	@FXML
	private Label lbTime1;
	@FXML
	private Label lbTime2, lbCountDown, lbCountDownTitle;
	@FXML
	private Button btnQuit, getTimeBtn, getTimeBtn1, getTimeBtn2;
	@SuppressWarnings("unused")
	private Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//count down handler
		if(Switch.getDayCountDown() != ""){
			lbCountDownTitle.setText("Count Down");
			try {
				lbCountDown.setText(GetTime.getDifferenceDays(Switch.getDayCountDown())
						+ " days away from the " + Switch.getMission());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			lbCountDown.setText("");
			lbCountDownTitle.setText("");
		}
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowTime = df.format(new Date());// new Date()为获取当前系统时间
		return nowTime;
	}

}
