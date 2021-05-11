//package test;

import com.allan.controller.PopupController;
import com.allan.dao.ConfigDao;
import com.allan.domain.PopUp;
import com.allan.utils.MusicPlay;
import com.allan.utils.PopUpUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PopUpTest extends Application {

    public void showTimedDialog(long time, String message){
        Stage popup = new Stage();
        popup.initStyle(StageStyle.TRANSPARENT);//Stage要没有窗口装饰
        popup.setAlwaysOnTop(true);
        popup.setResizable(false);
        popup.initModality(Modality.APPLICATION_MODAL);
//		    popup.getIcons().add(new Image("/application/eye_tray.png"));
        Button closeBtn = new Button("Got it!");
//        closeBtn.setStyle("-fx-background:rgba(231,87,161,0.12);");
        closeBtn.setStyle("-fx-font: 14 arial; -fx-base: rgba(235,255,221,0.4);");
        closeBtn.setMaxSize(60,40);
        closeBtn.setPrefSize(60,40);
        closeBtn.setFocusTraversable(false);//设置默认显示时不获取焦点
        closeBtn.setOnAction(e -> {
            popup.close();
        });
        VBox root = new VBox();
//        root.setStyle("-fx-background:rgba(255,233,50,0.07);");//VBox要透明
//        root.setPadding(new Insets(19));
        root.setAlignment(Pos.CENTER);//显示位置
        root.setSpacing(20);

        //eclipse shape
        /*DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));

        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(50.0f);
        ellipse.setCenterY(50.0f);
        ellipse.setRadiusX(50.0f);
        ellipse.setRadiusY(25.0f);
        ellipse.setEffect(ds);*/
//        root.getChildren().add(ellipse);

        Label label = new Label(message);
        label.setFont(new Font("Arial", 18));
//        label.setTextFill(Color.web("#E78CA3"));
        label.setTextFill(Color.color(0.3,0.3,0.3));
        root.getChildren().addAll(label, closeBtn);
        Scene scene = new Scene(root);
        scene.setFill(null);//Scene要透明
        popup.setScene(scene);
        popup.setTitle("R&O");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        PopUp popUpData = PopUpUtil.getPopUp(primaryScreenBounds.getMinX(), primaryScreenBounds.getMinY(),
                primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        popup.setX(popUpData.getX());
        popup.setY(popUpData.getY());
        popup.setWidth(popUpData.getWidth());
        popup.setHeight(popUpData.getHeight());
        System.out.println("popUpData：" + popUpData);

        popup.show();

//        Thread thread = new Thread(() -> {
//            try {
//                Thread.sleep(time);
//                ConfigDao p = ConfigDao.getInstance();
//                if (popup.isShowing()) {
//                    Platform.runLater(() ->
//                            popup.close());
//                }
//                if(p.readMusicSwich()) MusicPlay.playOnce("Devils_Never_Cry.wav");
//                else if(p.readSpvTTSSwitch()) TTS.playSpVoiceTTS("Time's up!");
//                else if(p.readFreeTTSSwitch()) TTS.playFreeTTS("Time's up!");
//            } catch (Exception exp) {
//                exp.printStackTrace();
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
    }

//    public static void application(String[] args) throws IOException, SAXException, ParserConfigurationException {
//        Platform.runLater(()->{
//            try {
//                new PopUpTest().showTimedDialog(5000,"什么message");
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });



//    }

    public void showPopUp(long time, String message) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/com/allan/fxml/popup.fxml"));
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TAN);
        PopupController controller = fxmlloader.getController();
        Stage stage = new Stage();
//        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setAlwaysOnTop(true);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.getIcons().add(new Image("/com/allan/pics/tr.png"));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setWidth(250);
//		stage.setHeight(250);
//        stage.setX(primaryScreenBounds.getWidth() - 410);
//        stage.setY(primaryScreenBounds.getHeight() - 315);
        stage.setX(-8);
        stage.setY(primaryScreenBounds.getHeight() - 315);
        stage.setScene(scene);
        controller.setStage(stage);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
        if (KeyCode.ESCAPE == event.getCode()) {
            stage.setIconified(true);
        }
    });
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(()->{
            try {
//                new PopUpTest().showTimedDialog(15000,"什么message");
                setUserAgentStylesheet(STYLESHEET_CASPIAN);
                new PopUpTest().showPopUp(15000,"什么message");
            } /*catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }*/ catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
