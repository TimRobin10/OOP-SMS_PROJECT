package sms.Admin_GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Admin_GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
            Scene scene_login = new Scene(root);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            System.out.println(screenBounds.getWidth());
            System.out.println(screenBounds.getHeight());

            stage.setScene(scene_login);
            stage.setMinWidth(screenBounds.getWidth());
            stage.setMinHeight(screenBounds.getHeight());
            stage.setResizable(true);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
