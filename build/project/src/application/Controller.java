package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
	private Button btn;
	@FXML
	private Button setColorBtn;
	@FXML
	private ChoiceBox<Integer> choice;
	@FXML
	private RadioButton remind;
	@SuppressWarnings("unused")
	private Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ReadBreak rb = new ReadBreak();
		int flag = rb.readFlag();
		int breakTime = rb.readBreak();
		choice.setValue(getVal(breakTime));
		if(flag==0) remind.setSelected(false);
		else remind.setSelected(true);
		choice.setItems(FXCollections.observableArrayList(30, 45, 60, 75));
		choice.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number>
		ov, Number old_val, Number new_val) -> {try {
			remindSetting(new_val.intValue());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}});
	}
	public void setStage(Stage stage){
		this.stage = stage;
	}
	public void setLabel(String s){
		lbTime.setText(s);
	}
	public void setColorBtnHandler(ActionEvent event) throws IOException{
        String batName = "mybat.bat"; //该bat文件保存在项目目录下，所以无需写出完整路径，如果文件不在项目目录下则需要直接写出文件路径
        runBat rBat = new runBat();
        rBat.run_bat(batName);
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
	public void remindHandler(ActionEvent event) throws FileNotFoundException, IOException{
//		System.out.println("handler");
		ReadBreak rb = new ReadBreak();
		if(remind.isSelected()) rb.writeFlag("1");
		else{
			rb.writeFlag("0");
//			System.out.println("not selected");
		}
	}
	public void remindSetting(int val) throws FileNotFoundException, IOException{
		ReadBreak rb = new ReadBreak();
		if (val==0) rb.writeBreak("1800000");
		else if(val==1) rb.writeBreak("2700000");
		else if(val==2) rb.writeBreak("3600000");
		else if(val==3) rb.writeBreak("4500000");
	}
	public void f_alert_informationDialog(String p_header, String p_message){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("Rest");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
//        _alert.initOwner(d_stage);
        _alert.setX(900);
        _alert.setY(500);
        _alert.show();
    }
	public String showTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowTime = df.format(new Date());// new Date()为获取当前系统时间
		return nowTime;
	}
	public int getVal(int val){
		return val/60000;
	}

}
