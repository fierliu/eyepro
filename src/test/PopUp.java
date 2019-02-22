package test;

import com.allan.dao.PropertiesDAO;
import com.allan.utils.MusicPlay;
import com.allan.utils.TTS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PopUp extends Application {
    //this is local change
    //this is remote server's change
    public void showTimedDialog(long time, String message)
            throws ParserConfigurationException, SAXException, IOException {
        Stage popup = new Stage();
        popup.initStyle(StageStyle.TRANSPARENT);//Stage要没有窗口装饰
        popup.setAlwaysOnTop(true);
        popup.setResizable(false);
        popup.initModality(Modality.APPLICATION_MODAL);
//		    popup.getIcons().add(new Image("/application/eye_tray.png"));
        Button closeBtn = new Button("Got it!");
//        closeBtn.setStyle("-fx-background:rgba(231,87,161,0.12);");
        closeBtn.setStyle("-fx-font: 10 arial; -fx-base: rgba(94,99,93,0.4);");
        closeBtn.setFocusTraversable(false);//设置默认显示时不获取焦点
        closeBtn.setOnAction(e -> {
            popup.close();
        });
        VBox root = new VBox();
//        root.setStyle("-fx-background:rgba(255,233,50,0.07);");//VBox要透明
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);//显示位置
        root.setSpacing(10);

        //eclipse shape
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));

        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(50.0f);
        ellipse.setCenterY(50.0f);
        ellipse.setRadiusX(50.0f);
        ellipse.setRadiusY(25.0f);
        ellipse.setEffect(ds);
//        root.getChildren().add(ellipse);

        Label label = new Label(message);
        label.setFont(new Font("Arial", 14));
//        label.setTextFill(Color.web("#E78CA3"));
        label.setTextFill(Color.color(0.3,0.3,0.3));
        root.getChildren().addAll(label, closeBtn);
        Scene scene = new Scene(root);
        scene.setFill(null);//Scene要透明
        popup.setScene(scene);
        popup.setTitle("R&O");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        popup.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth()/2 - 180);
        popup.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight()/2 - 130);
        System.out.println(primaryScreenBounds.getMinX());
        System.out.println(primaryScreenBounds.getWidth());
        System.out.println(primaryScreenBounds.getMinY());
        System.out.println(primaryScreenBounds.getHeight());
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
                if(p.readMusicSwich()) MusicPlay.playOnce("Devils_Never_Cry.wav");
                else if(p.readSpvTTSSwitch()) TTS.playSpVoiceTTS("Time's up!");
                else if(p.readFreeTTSSwitch()) TTS.playFreeTTS("Time's up!");
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

//    public static void application(String[] args) throws IOException, SAXException, ParserConfigurationException {
//        Platform.runLater(()->{
//            try {
//                new PopUp().showTimedDialog(5000,"什么message");
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });



//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(()->{
            try {
                new PopUp().showTimedDialog(15000,"什么message");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
