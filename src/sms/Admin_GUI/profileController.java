package sms.Admin_GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sms.LoginPage.LoginDatabase;
import sms.LoginPage.LoginPage;


import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static sun.net.httpserver.simpleserver.Main.main;

public class profileController {

    @FXML private TextField AdminPI;
    @FXML private TextField EditProfileAdmin;
    @FXML private AnchorPane EditProfileInfo;
    @FXML private AnchorPane EditProfilePanel;
    @FXML private Label FirstNameLabel;
    @FXML private Label FirstNameLabelPI;
    @FXML private TextField AccountNameTF;
    @FXML private TextField FirstNameTextFieldPI;
    @FXML private Label LastNameLabel;
    @FXML private TextField LastNameTextField;
    @FXML private TextField LastNameTextField1;
    @FXML private TextField LastNameTextFieldPI;
    @FXML private Button LogoutButton;
    @FXML private AnchorPane MainForm;
    @FXML private Label PasswordLabel;
    @FXML private Label PasswordLabelPI;
    @FXML private TextField PasswordTextFieldPI;
    @FXML private AnchorPane PersonalInfoBotPanel;
    @FXML private AnchorPane PersonalInfoTopPanel;
    @FXML private Label ProfileText;
    @FXML private Label RoleLabelPI;
    @FXML private TextField RoleTextField;
    @FXML private Label SystemAdminLabel;
    @FXML private Label SystemAdminLabel1;
    @FXML private TabPane TabPane;
    @FXML private Label UserIDLabelPI;
    @FXML private TextField UserIDTextField;
    @FXML private Label UsernameLabel;
    @FXML private Label UsernameLabelPI;
    @FXML private TextField PasswordTextField;
    @FXML private TextField UsernameTextField;
    @FXML private TextField UsernameTextFieldPI;
    @FXML private  Button UpdateProfileBtn;
    @FXML private TextField ConfirmPasswordTextField;

    LoginDatabase lgb = LoginDatabase.getInstance();
    private MenuController menuController;

    @FXML
    public void initialize() {
        init_personalinfo();
        lgb.addListener(this::updateUI);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    void close(){
        Stage stage = (Stage) MainForm.getScene().getWindow();
        stage.close();
    }

    void init_personalinfo(){
        AdminPI.setText(lgb.getAccount_Name());
        SystemAdminLabel1.setText("System " + lgb.getAccount_Role());
        FirstNameTextFieldPI.setText(lgb.getAccount_Name());
        UsernameTextFieldPI.setText(lgb.getAccount_Username());
        PasswordTextFieldPI.setText(lgb.getAccount_Password());

        RoleTextField.setText(lgb.getAccount_Role());
        UserIDTextField.setText(lgb.getAccount_Username());
        EditProfileAdmin.setText(lgb.getAccount_Name());
        AccountNameTF.setText(lgb.getAccount_Name());
        PasswordTextField.setText(lgb.getAccount_Password());
        UsernameTextField.setText(lgb.getAccount_Username());
        SystemAdminLabel.setText("System " + lgb.getAccount_Role());
    }

    public void save_edit() {
        String username_input = UsernameTextField.getText();
        String password_input = PasswordTextField.getText();
        String Account_Name = AccountNameTF.getText();
        int User_ID = lgb.getAccount_ID();
        String confirmPassword_input = ConfirmPasswordTextField.getText();

        boolean username_status;
        boolean password_status;

        if (username_input.equals(lgb.getAccount_Username())) {
            username_status = false;
        } else {
            username_status = lgb.verify_username(username_input);
        }

        if (password_input.equals(lgb.getAccount_Password())) {
            confirmPassword_input = lgb.getAccount_Password();
            password_status = true;
        } else {
            password_status = password_input.equals(confirmPassword_input);
        }

        if (username_input.contains(" ")) {
            showAlert("Error: Username cannot contain spaces", "Information Error!");
            return;
        }

        if (username_input.contains("%") || username_input.contains("*") || username_input.contains("?") ||
                username_input.contains("-") || username_input.contains("\\") || username_input.contains("/") ||
                username_input.contains("#") || username_input.contains("(") || username_input.contains(")")) {
            showAlert("Error: Username cannot contain special characters (%*()?\\/#)", "Information Error!");
            return;
        }

        if (username_input.isEmpty()) {
            showAlert("Error: Username cannot be empty", "Information Error!");
            return;
        }

        if (password_input.isEmpty()) {
            showAlert("Error: Password cannot be empty", "Information Error!");
            return;
        }

        if (confirmPassword_input.isEmpty()) {
            showAlert("Error: Confirm Password cannot be empty", "Information Error!");
            return;
        }

        if (password_input.length() < 8) {
            showAlert("Error: Password must be at least 8 characters", "Information Error!");
            return;
        }

        if (!username_input.equals(lgb.getAccount_Username()) && username_status) {
            showAlert("Error: Username already exists", "Information Error!");
            return;
        }

        if (!password_status) {
            showAlert("Error: Password does not match", "Information Error!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure of the changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            lgb.editAccountInfo(Account_Name, password_input, username_input, User_ID);
        }
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Account Force Restart");
        alert2.setHeaderText(null);
        alert2.setContentText("Restart Account to apply changes.");
        alert2.showAndWait();
        Logout2();
    }



    public void Logout(){
        showAlert("Are you sure you want to Log out?", "Logging Out");
        close();
        if(menuController != null){
            menuController.MenuClose();
        }
        LoginPage.launchLogin();
    }

    public void Logout2(){
        close();
        if(menuController != null){
            menuController.MenuClose();
        }
        LoginPage.launchLogin();
    }

    public void updateUI(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                init_personalinfo();
            }
        });
    }

    private void showAlert(String message, String x) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(x);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
