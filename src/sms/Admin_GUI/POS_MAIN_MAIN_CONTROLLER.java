package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.io.IOException;

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
        int status = transactions_database.add_transaction(CustomerID,CustomerName,address,amount,DueDate);
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
                print();
            }
            case JOptionPane.NO_OPTION -> {
            }
        }
        close();
    }


    public void print() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
            Pane root = loader.load();

            Receipt_Controller controller = loader.getController();
            controller.initializeData(next_transaction_id, CustomerName, address, amount);

            Printer printer = Printer.getDefaultPrinter();
            if (printer == null) {
                JOptionPane.showMessageDialog(null, "No available printer found", "Printing Error", JOptionPane.ERROR_MESSAGE);
            } else {
                PrinterJob job = PrinterJob.createPrinterJob(printer);

                if (job != null) {
                    PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
                            Printer.MarginType.HARDWARE_MINIMUM);

                    // Optional: Customize margins (in points, 1 inch = 72 points)
                    double leftMargin = 25;
                    double rightMargin = 20;
                    double topMargin = 0;
                    double bottomMargin = 20;

                    pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
                            leftMargin, rightMargin, topMargin, bottomMargin);

                    boolean success = job.printPage(pageLayout, root);
                    if (success) {
                        job.endJob();
                        JOptionPane.showMessageDialog(null, "Receipt printed successfully", "Printing Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Printing failed", "Printing Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Could not create printer job", "Printing Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while printing", "Printing Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
