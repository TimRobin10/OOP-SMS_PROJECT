package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class POS_MAIN_MAIN_CONTROLLER {
    @FXML private TextField AddressTF;
    @FXML private TextField AmountTF;
    @FXML private Button CancelButton;
    @FXML private Button ConfirmButton;
    @FXML private TextField CustomerIDTF;
    @FXML private TextField CustomerNameTF;
    @FXML private TextField DueDateTF;
    @FXML private TextField TransactionIDTF;

    Transactions_Database transactions_database = Transactions_Database.getInstance();
    PaymentDatabase payment_database = PaymentDatabase.getInstance();
    int next_transaction_id;

    String CustomerName = "";
    int CustomerID = 0;
    String DueDate = "";
    String address = "";
    double amount = 0;


    public void setValues(String Customer_Name, int Customer_ID, String Due_date, String Address, double Amount) {
        next_transaction_id = transactions_database.latest_transaction_ID()+1;

        CustomerIDTF.setText(Integer.toString(Customer_ID));
        this.CustomerID = Customer_ID;

        CustomerNameTF.setText(Customer_Name);
        this.CustomerName = Customer_Name;

        DueDateTF.setText(Due_date);
        this.DueDate = Due_date;

        AddressTF.setText(Address);
        this.address = Address;

        AmountTF.setText("Php " + String.format("%.2f", Amount));
        this.amount = Amount;

        TransactionIDTF.setText(String.format("%06d", next_transaction_id));

    }

    public void close(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void confirm(){
        int print_option = 0;
        int status = transactions_database.add_transaction(CustomerID,CustomerName,amount,DueDate);
        switch(status){
            case 1 ->{
                print_option = JOptionPane.showOptionDialog(null,"Transaction added successfully\n\nDo you want a printed reciept?", "Payment Successful", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            }
            case 2 ->{
                JOptionPane.showMessageDialog(null, "Error: Transaction unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        switch (print_option){
            case JOptionPane.YES_OPTION -> {

            }
            case JOptionPane.NO_OPTION -> {

            }
        }
        close();
    }

}
