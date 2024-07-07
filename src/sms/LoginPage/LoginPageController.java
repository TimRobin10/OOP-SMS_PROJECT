package sms.LoginPage;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;

public class LoginPageController {
    LoginDatabase database = LoginDatabase.getInstance();
    FadeTransition fade_Left = new FadeTransition();
    FadeTransition fade_right = new FadeTransition();
    TranslateTransition translate = new TranslateTransition();

    Boolean IsSignInPasswordVisible = false;
    Boolean IsSignUpPasswordVisible = false;
    Boolean IsConfirmPasswordVisible = false;
    int SignIn_SignOut_Indicator = 1;       //1 means user is signing in

    @FXML private AnchorPane AnchorPane;
    @FXML private TextField SignInUsernameTextField;
    @FXML private PasswordField SignInPasswordFieldHidden;
    @FXML private TextField SignInPasswordFieldView;
    @FXML private PasswordField SignUpPasswordFieldHidden;
    @FXML private TextField SignUpPasswordFieldView;
    @FXML private Pane LeftPane;
    @FXML private Pane RightPane;
    @FXML private Pane FloatingPaneBG;
    @FXML private Label FloatingPaneMainLabel;
    @FXML private Label FloatingPaneSecondaryLabel;
    @FXML private Button FloatingPaneButton1;
    @FXML private TextField SignUpConfirmPasswordView;
    @FXML private PasswordField SignUpConfirmPasswordHidden;
    @FXML private Label SignInInvalidLabels;
    @FXML private Label SignUpInvalidLabel;
    @FXML private TextField SignUpUsernameTextField;

    public void close (){
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
    }

    public void ShowPassword_SignIn(){
        SignInPasswordFieldView.textProperty().bindBidirectional(SignInPasswordFieldHidden.textProperty());

        if(IsSignInPasswordVisible){
            SignInPasswordFieldView.setVisible(false);
            SignInPasswordFieldHidden.setVisible(true);
            IsSignInPasswordVisible = false;
        } else{
            SignInPasswordFieldView.setVisible(true);
            SignInPasswordFieldHidden.setVisible(false);
            IsSignInPasswordVisible = true;
        }
    }

    public void ShowPassword_SignUp(){
        SignUpPasswordFieldView.textProperty().bindBidirectional(SignUpPasswordFieldHidden.textProperty());

        if(IsSignUpPasswordVisible){
            SignUpPasswordFieldView.setVisible(false);
            SignUpPasswordFieldHidden.setVisible(true);
            IsSignUpPasswordVisible = false;
        } else{
            SignUpPasswordFieldView.setVisible(true);
            SignUpPasswordFieldHidden.setVisible(false);
            IsSignUpPasswordVisible = true;
        }
    }

    public void ConfirmPassword_SignIn_View(){
        SignUpConfirmPasswordView.textProperty().bindBidirectional(SignUpConfirmPasswordHidden.textProperty());

        if(IsConfirmPasswordVisible){
            SignUpConfirmPasswordView.setVisible(false);
            SignUpConfirmPasswordHidden.setVisible(true);
            IsConfirmPasswordVisible = false;
        } else{
            SignUpConfirmPasswordView.setVisible(true);
            SignUpConfirmPasswordHidden.setVisible(false);
            IsConfirmPasswordVisible = true;
        }
    }

    public void SignIn() {
        SignInInvalidLabels.setText("");
        String username = SignInUsernameTextField.getText();
        String password = SignInPasswordFieldHidden.getText();
        int verify = database.verifyAccount(username, password);

        if(username.isEmpty() && password.isEmpty()){
            SignInInvalidLabels.setText("Error: Please input necessary data");
        } else if(username.isEmpty()){
            SignInInvalidLabels.setText("Error: Username cannot be empty");
        } else if(password.isEmpty()){
            SignInInvalidLabels.setText("Error: Password cannot be empty");
        } else {
            switch (verify) {
                case 0 -> {
                    String Account_Role = database.get_account_role(username);

                    switch (Account_Role) {
                        case "ADMIN" -> {
                            JOptionPane.showMessageDialog(null, "Welcome " + username, "Welcome Admin", JOptionPane.INFORMATION_MESSAGE);
                            database.closeConnection();
                        }
                        case "USER" -> {
                            JOptionPane.showMessageDialog(null, "Welcome " + username, "Welcome User", JOptionPane.INFORMATION_MESSAGE);
                            database.closeConnection();
                        }
                        case "DEFAULT" ->{
                            JOptionPane.showMessageDialog(null, "Sorry. You are not allowed to enter the system yet. Please contact your system Administrator to update your account.", "Default Users", JOptionPane.INFORMATION_MESSAGE);
                            database.closeConnection();
                        }
                    }
                }
                case 1 -> SignInInvalidLabels.setText("Error: Incorrect Password.");
                case 2 -> SignInInvalidLabels.setText("Error: Incorrect Username.");
                case 3 -> SignInInvalidLabels.setText("Error: Username Does Not Exist.");
            }
        }
    }

    public  void Sign_Up(){
        boolean username_status, password_status;
        SignUpInvalidLabel.setText("");
        String username_input = SignUpUsernameTextField.getText();
        String password_input = SignUpPasswordFieldHidden.getText();
        String confirmPassword_input = SignUpConfirmPasswordHidden.getText();

        password_status = password_input.equals(confirmPassword_input);
        username_status = database.verify_username(username_input);

        if(username_input.contains(" ")){
            SignUpInvalidLabel.setText("Error: Username cannot contain spaces");
        } else if(username_input.contains("%") || username_input.contains("*")|| username_input.contains("?") || username_input.contains("-") || username_input.contains("\\") || username_input.contains("/") || username_input.contains("#") || username_input.contains("(") || username_input.contains(")")){
            SignUpInvalidLabel.setText("Error: Username cannot contain special characters (%*()?-\\/#)");
        } else if(username_input.isEmpty() && password_input.isEmpty() && confirmPassword_input.isEmpty()){
            SignUpInvalidLabel.setText("Error: Please input necessary data");
        } else if(username_input.isEmpty()){
            SignUpInvalidLabel.setText("Username cannot be empty");
        } else if(password_input.isEmpty()){
            SignUpInvalidLabel.setText("Password cannot be empty");
        } else if(confirmPassword_input.isEmpty()){
            SignUpInvalidLabel.setText("Confirm Password cannot be empty");
        } else if(password_input.length() < 8){
            SignUpInvalidLabel.setText("Password must be at least 8 characters");
        }else{
            if (username_status && !password_status) {
                SignUpInvalidLabel.setText("Error: Username already exists and password does not match");
            } else if (username_status) {
                SignUpInvalidLabel.setText("Error: Username already exists");
            } else if (!password_status) {
                SignUpInvalidLabel.setText("Error: Password does not match");
            } else {
                database.add_account(username_input, password_input);
            }
        }

    }

    public void SignIn_SignOut(){
        translate.setNode(FloatingPaneBG);
        if(SignIn_SignOut_Indicator == 1){
            fade_Left.setNode(LeftPane);
            fade_Left.setDuration(Duration.millis(500));
            fade_Left.setFromValue(1);
            fade_Left.setToValue(0);
            fade_Left.play();

            fade_right.setNode(RightPane);
            fade_right.setDuration(Duration.millis(500));
            fade_right.setFromValue(0);
            fade_right.setToValue(1);
            fade_right.play();

            fadeText_Label(FloatingPaneMainLabel,"Create Account!");
            fadeText_Label(FloatingPaneSecondaryLabel,"Enter credentials to create your employee account.");
            fadeText_Button(FloatingPaneButton1,"Sign in");

            translate.setByX(-425);
            translate.setDuration(Duration.millis(500));
            translate.setInterpolator(Interpolator.EASE_BOTH);
            translate.play();
            SignIn_SignOut_Indicator = 2;
        } else{
            fade_Left.setNode(LeftPane);
            fade_Left.setDuration(Duration.millis(500));
            fade_Left.setFromValue(0);
            fade_Left.setToValue(1);
            fade_Left.play();

            fade_right.setNode(RightPane);
            fade_right.setDuration(Duration.millis(500));
            fade_right.setFromValue(1);
            fade_right.setToValue(0);
            fade_right.play();

            fadeText_Label(FloatingPaneMainLabel,"Welcome Back!");
            fadeText_Label(FloatingPaneSecondaryLabel,"Enter personal details to your employee account.");
            fadeText_Button(FloatingPaneButton1,"Sign up");

            translate.setByX(425);
            translate.setDuration(Duration.millis(500));
            translate.setInterpolator(Interpolator.EASE_BOTH);
            translate.play();
            SignIn_SignOut_Indicator = 1;
        }
    }


    private void fadeText_Label(Label label, String newText) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), label);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            label.setText(newText);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), label);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    private void fadeText_Button(Button button, String newText) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), button);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            button.setText(newText);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), button);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
