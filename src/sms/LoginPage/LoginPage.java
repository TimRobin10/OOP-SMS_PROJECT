package sms.LoginPage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class LoginPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPage1.fxml")));
            Scene scene_login = new Scene(root);
            String css = Objects.requireNonNull(this.getClass().getResource("LoginPage1.css")).toExternalForm();

            scene_login.getStylesheets().add(css);
            scene_login.setFill(Color.TRANSPARENT);
            stage.setScene(scene_login);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
