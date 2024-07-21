package PROFILE;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class profileController implements Initializable {



    @FXML
    private TextField AdminPI;
    @FXML
    private TextField EditProfileAdmin;

    @FXML
    private AnchorPane EditProfileInfo;

    @FXML
    private AnchorPane EditProfilePanel;

    @FXML
    private Label FirstNameLabel;

    @FXML
    private Label FirstNameLabelPI;

    @FXML
    private TextField FirstNameTextField;

    @FXML
    private TextField FirstNameTextFieldPI;

    @FXML
    private Label LastNameLabel;

    @FXML
    private TextField LastNameTextField;

    @FXML
    private TextField LastNameTextField1;

    @FXML
    private TextField LastNameTextFieldPI;

    @FXML
    private Button LogoutButton;

    @FXML
    private AnchorPane MainForm;

    @FXML
    private Label PasswordLabel;


    @FXML
    private Label PasswordLabelPI;

    @FXML
    private TextField PasswordTextFieldPI;

    @FXML
    private AnchorPane PersonalInfoBotPanel;

    @FXML
    private AnchorPane PersonalInfoTopPanel;

    @FXML
    private Label ProfileText;

    @FXML
    private Label RoleLabelPI;

    @FXML
    private TextField RoleTextField;

    @FXML
    private Label SystemAdminLabel;

    @FXML
    private Label SystemAdminLabel1;

    @FXML
    private TabPane TabPane;

    @FXML
    private Label UserIDLabelPI;

    @FXML
    private TextField UserIDTextField;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label UsernameLabelPI;
    @FXML
    private TextField PasswordTextField;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private TextField UsernameTextFieldPI;

    @FXML
    private  Button UpdateProfileBtn;

    @FXML
    private TextField ConfirmPasswordTextField;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UpdateProfileBtn.setOnAction(event -> updatePersonalInfo());
        LogoutButton.setOnAction(event -> handleLogout());

    }

    @FXML
    private void updatePersonalInfo() {
        String firstName = FirstNameTextField.getText();
        String lastName = LastNameTextField.getText();
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();
        String confirmPassword = ConfirmPasswordTextField.getText();


        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Input Error", "Fields Cannot Be Empty", "Please fill out all fields.");

            FirstNameTextField.setText("");
            LastNameTextField.setText("");
            UsernameTextField.setText("");
            PasswordTextField.setText("");
            ConfirmPasswordTextField.setText("");
            return;
        }


        if (!password.equals(confirmPassword)) {
            showAlert("Password Mismatch", "Passwords Do Not Match", "Please make sure the passwords match.");

            FirstNameTextField.setText("");
            LastNameTextField.setText("");
            UsernameTextField.setText("");
            PasswordTextField.setText("");
            ConfirmPasswordTextField.setText("");
            return;
        }

        if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0))) {
            showAlert("Input Error", "Names Must Start with a Capital Letter", "Please ensure first and last names start with a capital letter.");

            FirstNameTextField.setText("");
            LastNameTextField.setText("");
            UsernameTextField.setText("");
            PasswordTextField.setText("");
            ConfirmPasswordTextField.setText("");


            return;
        }
        firstName = capitalizeName(firstName);
        lastName = capitalizeName(lastName);

        FirstNameTextFieldPI.setText(firstName);
        LastNameTextFieldPI.setText(lastName);
        UsernameTextFieldPI.setText(username);
        PasswordTextFieldPI.setText(password);
        EditProfileAdmin.setText(firstName + " " + lastName);
        AdminPI.setText(firstName + " " + lastName);


        FirstNameTextField.setText("");
        LastNameTextField.setText("");
        UsernameTextField.setText("");
        PasswordTextField.setText("");
        ConfirmPasswordTextField.setText("");
    }

    private String capitalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
    @FXML
    private void handleLogout() {


        System.out.println("Logged out successfully!");


    }


    


}
