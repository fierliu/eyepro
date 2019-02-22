package com.allan.application;
/*

 * 另外PopUp占用的内存也有点高，是否能改用其他形式进行优化？
 */
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.media.NoPlayerException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.parsers.ParserConfigurationException;

import com.allan.controller.Controller;
import com.allan.dao.PropertiesDAO;
import com.allan.utils.GetTime;
import com.allan.utils.MusicPlay;
import com.allan.utils.SoundManager;
import com.allan.utils.TTS;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Main extends Application {
	public String time;
	private TrayIcon trayIcon;

	public void init() throws ParserConfigurationException, SAXException, IOException{

	}
	@Override
	public void start(Stage primaryStage) throws IOException, DocumentException {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/com/allan/fxml/scene.fxml"));
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Controller controller = fxmlloader.getController();
		//传递primaryStage参数给Controller
		controller.setStage(primaryStage);
//		primaryStage.getIcons().add(new Image("/application/eye_tray.png"));
		primaryStage.setTitle("EyePro");
		//terminate the all threads when close button clicked.
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event){
				//System.exit(0);
				primaryStage.hide();
			}
		});
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.hide();
		Platform.setImplicitExit(false);
		//系统托盘
        enableTray(primaryStage);

		Timer timer = new Timer();
        TimerTask  timerTask = new TimerTask (){
            public void run() {
				PropertiesDAO p = PropertiesDAO.getInstance();
//            	System.out.println(get_Time());
//            	Platform.runLater(()->showTimedDialog(300000, "5 Min."));
//            	-----------半点提醒--------------------------------------------
				if(get_Time().equals("30:00")){					
					Platform.runLater(()->{
						try {

							if(p.readPopUpSwitch()) showTimedDialog(120000, p.readNoticeWord());
                            SoundManager.playSound(SoundManager.HALF);
						} catch (ParserConfigurationException | SAXException | IOException e) {
							e.printStackTrace();
						}
					});
//					-------------整点提醒-------------------------
				}else if(get_Time().equals("00:00")){
					Platform.runLater(()->{
						try {
//							PropertiesDAO p = new PropertiesDAO();
							if(p.readPopUpSwitch()) showTimedDialog(300000, p.readNoticeWord());
							SoundManager.playSound(SoundManager.SHARP);
						} catch (ParserConfigurationException | SAXException | IOException e) {
							e.printStackTrace();
						}
					});
				}
            }
        };
        timer.schedule (timerTask, 0, 1000);
	}

	public String get_Time() {
        Calendar calendar = Calendar.getInstance();
        Date date = (Date) calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        time = format.format(date);
        return time;
	}
	public void showTimedDialog(long time, String message)
			throws ParserConfigurationException, SAXException, IOException {
		Stage popup = new Stage();
		popup.initStyle(StageStyle.TRANSPARENT);//Stage要没有窗口装饰
	    popup.setAlwaysOnTop(true);
	    popup.setResizable(false);
	    popup.initModality(Modality.APPLICATION_MODAL);
	    Button closeBtn = new Button("Got it!");
		closeBtn.setStyle("-fx-font: 10 arial; -fx-base: rgba(94,99,93,0.4);");
		closeBtn.setFocusTraversable(false);//设置默认显示时不获取焦点
	    closeBtn.setOnAction(e -> {
	        popup.close();
	    });
	    VBox root = new VBox();
//	    root.setStyle("-fx-background:transparent;");//VBox要透明
//	    root.setStyle("-fx-background:rgba(255,233,50,0.25);");//VBox要透明
	    root.setPadding(new Insets(10));
	    root.setAlignment(Pos.CENTER);//显示位置
	    root.setSpacing(10);

		Label label = new Label(message);
		label.setFont(new Font("Arial", 14));
		label.setTextFill(Color.color(0.3,0.3,0.3));
		root.getChildren().addAll(label, closeBtn);

	    Scene scene = new Scene(root);
		scene.setFill(null);//Scene要透明
	    popup.setScene(scene);
	    popup.setTitle("R&O");
	    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	    popup.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 180);
	    popup.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 130);
	    popup.setWidth(180);
	    popup.setHeight(130);
	    popup.show();

	    Thread thread = new Thread(() -> {
	        try {	        	
	            Thread.sleep(time);
	            PropertiesDAO p = PropertiesDAO.getInstance();
	            if (popup.isShowing()) {
	                Platform.runLater(() ->
	                popup.close());
	            }
				SoundManager.playSoundTimesUp();
	        } catch (Exception exp) {
	            exp.printStackTrace();
	        }
	    });
	    thread.setDaemon(true);
	    thread.start();
	}

	private void enableTray(final Stage stage) {
		PopupMenu popupMenu = new PopupMenu();
		java.awt.MenuItem openItem = new java.awt.MenuItem("Show");
		java.awt.MenuItem hideItem = new java.awt.MenuItem("Properties");
		java.awt.MenuItem quitItem = new java.awt.MenuItem("Exit");
		ActionListener acl = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				java.awt.MenuItem item = (java.awt.MenuItem) e.getSource();
				Platform.setImplicitExit(false); //多次使用显示和隐藏设置false

				if (item.getLabel().equals("Exit")) {
					SystemTray.getSystemTray().remove(trayIcon);
//					Platform.exit();
					System.exit(0);
					return;
				}
				if (item.getLabel().equals("Show")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.show();
						}
					});
				}
				if (item.getLabel().equals("Properties")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								Stage stage = new Stage();
								stage.initModality(Modality.APPLICATION_MODAL);
								FXMLLoader fxmlloader =
									new FXMLLoader(getClass().getResource("/com/allan/fxml/properties.fxml"));
								Parent root;
								root = fxmlloader.load();
								Scene scene = new Scene(root);
								stage.setResizable(false);
								stage.setScene(scene);
								stage.setTitle("Properties");
								stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		};
		//双击事件方法
		MouseListener sj = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Platform.setImplicitExit(false); //多次使用显示和隐藏设置false
				if (e.getClickCount() == 2) {
					if (stage.isShowing()) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								stage.hide();
							}
						});
					}else{
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								stage.show();
							}
						});
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		};

		openItem.addActionListener(acl);
		quitItem.addActionListener(acl);
		hideItem.addActionListener(acl);

		popupMenu.add(openItem);
		popupMenu.add(hideItem);
		popupMenu.add(quitItem);

		try {
			SystemTray tray = SystemTray.getSystemTray();
			BufferedImage image = ImageIO.read(Main.class
					.getResourceAsStream("/com/allan/pics/hiei.png"));//会有图标显示不全的问题
			trayIcon = new TrayIcon(image, "EyePro", popupMenu);
			trayIcon.setToolTip("EyePro");
			tray.add(trayIcon);
			trayIcon.addMouseListener(sj);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		launch(args);
	}

}
