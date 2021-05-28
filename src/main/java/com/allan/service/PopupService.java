package com.allan.service;

import com.allan.controller.PopupController;
import com.allan.domain.Config;
import com.allan.utils.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 弹窗计时服务
 */
public class PopupService {

    private static PopupService instance = new PopupService();
    private PopupService(){}
    private Timer popUpTimer;
    private TimerTask popupTimerTask;


    /**
     * 单例持有
     * @return
     */
    public static PopupService newInstance(){
        return instance;
    }

    public void startTimer(Config config) {
        if (popUpTimer != null) {
            popUpTimer.cancel();//重新设置间隔时间时，原来的timer停掉
        }
        if (popupTimerTask != null) {
            popupTimerTask.cancel();
        }
        popUpTimer = new Timer();
        popupTimerTask = new TimerTask (){
            public void run() {
                Platform.runLater(()->{
                    try {
                        if("Y".equals(config.getPopupOn())) {
                            showTimedDialog(300000);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        long period = Long.parseLong(config.getBreakTime()) * 60 * 1000;
        popUpTimer.schedule(popupTimerTask, period, period);
    }

    public void showTimedDialog(long time) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/com/allan/fxml/popup.fxml"));
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
//		scene.setFill(null);
        PopupController controller = fxmlloader.getController();
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image("/com/allan/pics/tr.png"));
        stage.setScene(scene);
        controller.setStage(stage);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//		stage.setX(primaryScreenBounds.getWidth() - 410);
        stage.setX(- 8);
        stage.setY(primaryScreenBounds.getHeight() - 315);
        //按esc最小化
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.setIconified(true);
            }
        });
        stage.show();

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(time);
                if (stage.isShowing()) {
                    Platform.runLater(() ->
                            stage.close());
                }
                SoundManager.playSoundTimesUp();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
