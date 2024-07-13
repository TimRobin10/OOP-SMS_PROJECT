package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javax.swing.*;

public class Accounts_Rows_Controller {

    @FXML private Label AccountName_LB;
    @FXML private PasswordField Password_LB;
    @FXML private Label Role_LB;
    @FXML private Button Row_Button;
    @FXML private Label UserID_LB;
    @FXML private Label Username_LB;

    String AccountName;
    String Password;
    String Role;
    int UserID;
    String UserName;

    public void set_Account_Data(Accounts acc){
        AccountName_LB.setText(acc.getAccount_Name());
        this.AccountName = acc.getAccount_Name();
        Password_LB.setText(acc.getAccount_Password());
        this.Password = acc.getAccount_Password();
        Role_LB.setText(acc.getAccount_Role());
        this.Role = acc.getAccount_Role();
        UserID_LB.setText(String.valueOf(acc.getAccount_ID()));
        this.UserID = acc.getAccount_ID();
        Username_LB.setText(acc.getUsername());
        this.UserName = acc.getUsername();
    }

    public void edit(){

    }


}
