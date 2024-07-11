package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Accounts_Rows_Controller {

    @FXML private Label AccountName_LB;
    @FXML private Label Password_LB;
    @FXML private Label Role_LB;
    @FXML private Button Row_Button;
    @FXML private Label UserID_LB;
    @FXML private Label Username_LB;

    public void set_Account_Data(Accounts acc){
        AccountName_LB.setText(acc.getAccount_Name());
        Password_LB.setText(convertToAsterisks(acc.getAccount_Password()));
        Role_LB.setText(acc.getAccount_Role());
        UserID_LB.setText(String.valueOf(acc.getAccount_ID()));
        Username_LB.setText(acc.getUsername());
    }

    public static String convertToAsterisks(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return "*".repeat(input.length());
    }
}
