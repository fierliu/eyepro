package com.allan.application;

import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import com.allan.controller.MainController;
import com.allan.controller.PopupController;
import com.allan.dao.ConfigDao;
import com.allan.domain.Config;
import com.allan.service.PopupService;
import com.allan.utils.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
  private TrayIcon trayIcon;
  private Config config;

  public void init() {
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    new ConfigDao().load();//加载配置
    config = Cache.getCache().getConfigCache();
    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/com/allan/fxml/scene.fxml"));
    setUserAgentStylesheet(STYLESHEET_CASPIAN);
    Parent root = fxmlloader.load();
    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("/com/allan/fxml/scene.css").toExternalForm());
    MainController mainController = fxmlloader.getController();
    //传递primaryStage参数给Controller
    mainController.setStage(primaryStage);
    primaryStage.setTitle("EyePro");
    setUserAgentStylesheet(STYLESHEET_CASPIAN);

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

    PopupService popupService = PopupService.newInstance();
    popupService.startTimer(config);
  }

  private void enableTray(final Stage stage) {
    PopupMenu popupMenu = new PopupMenu();
    java.awt.MenuItem openItem = new java.awt.MenuItem("Show");
    java.awt.MenuItem hideItem = new java.awt.MenuItem("Settings");
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
        if (item.getLabel().equals("Settings")) {
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
                stage.setTitle("Settings");
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
              .getResourceAsStream("/com/allan/pics/hiei.png"));
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
