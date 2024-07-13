package sms.Admin_GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Objects;

public class POS_MAIN_GUI extends Application {

    @Override
    public void start(Stage stage){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("POS_MAIN_GUI.fxml")));
            Scene scene_admin = new Scene(root);

            String css = Objects.requireNonNull(this.getClass().getResource("POS_MAIN_GUI.css")).toExternalForm();

            scene_admin.getStylesheets().add(css);

            stage.setTitle("P&C Optimal IT Solutions - Billing System");
            stage.setScene(scene_admin);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

}