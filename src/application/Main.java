package application;

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
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

public class Main extends Application {
	public String time;
	private TrayIcon trayIcon;
	@FXML

	public void init(){

	}
	@Override
	public void start(Stage primaryStage) throws IOException, DocumentException {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/application/scene.fxml"));
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
//            	System.out.println(get_Time());
//            	Platform.runLater(()->showTimedDialog(300000, "5 Min."));
				if(get_Time().equals("30:00")){
					try {
						MusicPlay.playOnce("2 minutes.wav");
					} catch (ParserConfigurationException | SAXException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.runLater(()->showTimedDialog(120000, "Beck & Neck."));
				}else if(get_Time().equals("00:00")){
					try {
						MusicPlay.playOnce("5 minutes.wav");
					} catch (ParserConfigurationException | SAXException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.runLater(()->showTimedDialog(300000, "Beck & Neck."));
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
	public void showTimedDialog(long time, String message) {
	    Stage popup = new Stage();
	    popup.setAlwaysOnTop(true);
	    popup.setResizable(false);
	    popup.initModality(Modality.APPLICATION_MODAL);
//	    popup.getIcons().add(new Image("/application/eye_tray.png"));
	    Button closeBtn = new Button("Got it!");
	    closeBtn.setOnAction(e -> {
	        popup.close();
	    });
	    VBox root = new VBox();
	    root.setPadding(new Insets(10));
	    root.setAlignment(Pos.CENTER);//显示位置
	    root.setSpacing(10);
	    root.getChildren().addAll(new Label(message), closeBtn);
	    Scene scene = new Scene(root);
	    popup.setScene(scene);
	    popup.setTitle("Rest & Check");
	    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	    popup.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 180);
	    popup.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 130);
	    popup.setWidth(180);
	    popup.setHeight(130);
	    popup.show();

	    Thread thread = new Thread(() -> {
	        try {
	            Thread.sleep(time);
	            MusicPlay.playOnce("Devils_Never_Cry.wav");
	            if (popup.isShowing()) {
	                Platform.runLater(() ->
	                popup.close());
	            }
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
									new FXMLLoader(getClass().getResource("/application/properties.fxml"));
							Parent root;

								root = fxmlloader.load();
								Scene scene = new Scene(root);
//								PropertiesController pcontroller = fxmlloader.getController();
								//传递Stage参数给Controller
//								pcontroller.setStage(stage);
								stage.setResizable(false);
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {
								// TODO Auto-generated catch block
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub

			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

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
					.getResourceAsStream("/application/eye_tray.png"));//会有图标显示不全的问题
//					.getResource("eye_tray.png"));
//			BufferedImage image = ImageIO.read(new File("eye_tray.png"));
//			java.awt.Image image = Toolkit.getDefaultToolkit().getImage("eye_tray.png");
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
