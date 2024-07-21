package sms.Admin_GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sms.LoginPage.LoginDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditAccountsController {
    @FXML private TextField AccountNameField;
    @FXML private Label AccountNameLabel;
    @FXML private AnchorPane BotAnchorPane;
    @FXML private Button CancelButton;
    @FXML private Label MainAccountNameLabel;
    @FXML private Label MainAccountRoleLabel;
    @FXML private AnchorPane MainFrame;
    @FXML private Label MainIDNumberLabel;
    @FXML private Label PasswordLabel;
    @FXML private ComboBox<String> RoleBox;
    @FXML private Label RoleLabel;
    @FXML private Button SaveButton;
    @FXML private AnchorPane TopAnchorPane;
    @FXML private TextField UsernameField;
    @FXML private Label UsernameLabel;
    @FXML private PasswordField ConfirmPasswordField;
    @FXML private PasswordField NewPasswordField;

    LoginDatabase ldb = LoginDatabase.getInstance();
    Accounts this_account;

    @FXML
    public void initialize(){
        initComboBox();
    }

    public void set_account(Accounts account){
        this.this_account = account;
        if (this_account != null) {
            initComboBox();
        }
    }

    public void initComboBox(){
        if (this_account == null) {
            return;
        }
        RoleBox.setItems(FXCollections.observableArrayList("CEO", "ADMIN", "USER", "DEFAULT"));
        RoleBox.setValue(this_account.getAccount_Role());

        AccountNameField.setText(this_account.getAccount_Name());
        UsernameField.setText(this_account.getUsername());
        NewPasswordField.setText(this_account.getAccount_Password());
        MainAccountNameLabel.setText(this_account.getAccount_Name());
        MainAccountRoleLabel.setText(this_account.getAccount_Role());
        MainIDNumberLabel.setText("ID No. " + String.valueOf(this_account.getAccount_ID()));
    }

    public void edit() {
        String acc_name = AccountNameField.getText();
        String acc_role = RoleBox.getValue();
        String username = UsernameField.getText();
        String password = NewPasswordField.getText();
        String confirmPassword = ConfirmPasswordField.getText();

        if (username.isEmpty()) {
            showAlert("Error: Username cannot be empty", "Information Error!");
            return;
        }

        if (username.contains(" ")) {
            showAlert("Error: Username cannot contain spaces", "Information Error!");
            return;
        }

        if (username.matches(".*[ %*()?\\/#\\-()\\\\].*")) {
            showAlert("Error: Username cannot contain special characters (%*()?\\/#-)", "Information Error!");
            return;
        }

        if (password.isEmpty()) {
            showAlert("Error: Password cannot be empty", "Information Error!");
            return;
        }

        if (password.length() < 8) {
            showAlert("Error: Password must be at least 8 characters", "Information Error!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            if(!(password.equals(this_account.getAccount_Password()))){
                showAlert("Error: Password and Confirm Password do not match", "Information Error!");
                return;
            }
        }

        boolean password_status = password.equals(this_account.getAccount_Password()) || !password.isEmpty();
        if (!password_status) {
            showAlert("Error: Incorrect current password", "Information Error!");
            return;
        }

        boolean username_status = username.equals(this_account.getUsername()) || !ldb.getAccount_Username().equals(username);
        if (!username_status) {
            showAlert("Error: Username already exists", "Information Error!");
            return;
        }

        // Confirm changes
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure of the changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ldb.editAccountInfoMain(acc_name, password, acc_role, username, this_account.getAccount_ID());
        }
    }

    public void close_edit(){
        Stage stage = (Stage) MainFrame.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message, String x) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(x);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
