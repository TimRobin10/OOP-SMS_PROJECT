package sms.Admin_GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sms.LoginPage.LoginDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class MenuController {
    @FXML
    private ToggleButton AccountsButton;
    @FXML
    private ToggleButton Billing;
    @FXML
    private ToggleButton DashboardButton;
    @FXML
    private ToggleButton DatabaseButton;
    @FXML
    private AnchorPane MenuPanel;
    @FXML
    private ToggleButton TransactionButton;
    @FXML
    private FontAwesomeIcon User_Icon;
    @FXML
    private Button User_Button;
    @FXML
    private BorderPane borderPane;

    ToggleGroup Menu = new ToggleGroup();

    private AnchorPane dashboard;
    private AnchorPane transaction;
    private AnchorPane accounts;
    private AnchorPane billing;
    private AnchorPane database;

    LoginDatabase lgb = LoginDatabase.getInstance();

    @FXML
    private void initialize() {
        init_togglegroup();
        Menu_Scenes();
        setButtonText();

        lgb.addListener(this::setButtonText);

        Menu.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                Menu_Scenes();
            }
        });

    }

    void init_togglegroup() {
        AccountsButton.setToggleGroup(Menu);
        Billing.setToggleGroup(Menu);
        DashboardButton.setToggleGroup(Menu);
        DatabaseButton.setToggleGroup(Menu);
        TransactionButton.setToggleGroup(Menu);
        DatabaseButton.setSelected(true);
        ToggleSingleSelection();
    }

    void ToggleSingleSelection() {
        Menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            }
        });
    }

    public void Menu_Scenes(){
        if(Menu.getSelectedToggle() == DashboardButton){
            if(dashboard == null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard_Scene.fxml"));
                    dashboard = loader.load();

                    Dashboard_Controller controller = loader.getController();

                    borderPane.setCenter(dashboard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            borderPane.setCenter(dashboard);

        } else if(Menu.getSelectedToggle() == DatabaseButton){
            if(database == null){
                try{
                    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("DATABASE_MAIN.fxml"));
                    database = fxmlLoader2.load();

                    DATABASE_CONTROLLER_MAIN controller2 = fxmlLoader2.getController();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            borderPane.setCenter(database);

        } else if(Menu.getSelectedToggle() == Billing){
            if(billing == null){
                try {
                    FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("Payments.fxml"));
                    billing = fxmlLoader3.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            borderPane.setCenter(billing);

        } else if(Menu.getSelectedToggle() == TransactionButton){
            if(transaction == null){
                try {
                    FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("Transactions.fxml"));
                    transaction = fxmlLoader4.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            borderPane.setCenter(transaction);

        } else if(Menu.getSelectedToggle() == AccountsButton){
            if(accounts == null){
                try {
                    FXMLLoader fxmlLoader5 = new FXMLLoader(getClass().getResource("Accounts.fxml"));
                    accounts = fxmlLoader5.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            borderPane.setCenter(accounts);
        }
    }

    public void MenuClose(){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void Account_Information(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        try {
            Parent root = loader.load();
            profileController controller = loader.getController();
            controller.setMenuController(this);

            Stage profile_stage = new Stage();
            profile_stage.setTitle("Account Information");
            profile_stage.setScene(new Scene(root));
            profile_stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setButtonText(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                User_Button.setText(lgb.getAccount_Name());
            }
        });
    }

}
