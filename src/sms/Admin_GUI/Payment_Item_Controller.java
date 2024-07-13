package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Payment_Item_Controller {
    @FXML private Label Amount_LB;
    @FXML private Label Amount_LB2;
    @FXML private AnchorPane BasePane;
    @FXML private Label Customer_ID_LB;
    @FXML private Label Customer_Name_LB;
    @FXML private Label Due_Date_LB;
    @FXML private Label Due_Date_LB2;
    @FXML private Button PayButton;
    @FXML private Label Address_LB1;

    int CusID;
    String CusName;
    String DueDate;
    double DueAmount;
    String address;

    public void DueData_SetAccount(Payments_Due_Accounts payments) {
        Customer_Name_LB.setText(payments.getAccount_name());
        CusName = payments.getAccount_name();
        Due_Date_LB2.setText(payments.getDue_date());
        DueDate = payments.getDue_date();
        Amount_LB2.setText("Php\t" + String.valueOf(payments.getAmount()));
        DueAmount = payments.getAmount();
        Customer_ID_LB.setText("#" + String.valueOf(payments.getAccount_id()));
        CusID = payments.getAccount_id();
        Address_LB1.setText(payments.getAddress());
        address = payments.getAddress();
    }

    public void pay(){

    }
}