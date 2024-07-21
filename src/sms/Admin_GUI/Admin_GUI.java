package sms.Admin_GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Admin_GUI extends Application {

    @Override
    public void start(Stage stage){
        try{

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            Scene scene_admin = new Scene(root);

            String css = Objects.requireNonNull(this.getClass().getResource("MenuConroller.css")).toExternalForm();

            scene_admin.getStylesheets().add(css);

            stage.setScene(scene_admin);
            stage.setTitle("P&C Optimal IT Solutions");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
