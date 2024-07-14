package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.text.DecimalFormat;

public class Transactions_Item_Controller {
    @FXML private Label Date;
    @FXML private Label Deadline;
    @FXML private Button Row_Button;
    @FXML private AnchorPane TableRows;
    @FXML private Label Time;
    @FXML private Label Transaction_Amount;
    @FXML private Label Transaction_ID;
    @FXML private Label Customer_ID;
    @FXML private Label Customer_Name;

    int transaction_id;
    int customer_id;
    String customer_name;
    double amount;
    String due_date;
    String transaction_date;
    String transaction_time;
    String address;

    public void set_transaction_data(Transaction_Template trans){
        Transaction_ID.setText(String.format("%06d", trans.getTransaction_ID()));
        Customer_ID.setText(String.format("%06d", trans.getCustomer_ID()));
        Customer_Name.setText(trans.getCustomer_Name());
        Transaction_Amount.setText("Php " + formatDouble(trans.getTransaction_Amount()));
        Deadline.setText(String.valueOf(trans.getDeadline()));
        Date.setText(trans.getTransaction_Date());
        Time.setText(trans.getTransaction_Time());

        transaction_id = trans.getTransaction_ID();
        customer_id = trans.getCustomer_ID();
        customer_name = trans.getCustomer_Name();
        amount = trans.getTransaction_Amount();
        due_date = trans.getDeadline();
        transaction_date = trans.getTransaction_Date();
        transaction_time = trans.getTransaction_Time();
        address = trans.getAddress();
    }

    public void print(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
            Pane root = loader.load();

            Receipt_Controller controller = loader.getController();
            controller.initializeData(transaction_id, customer_name, address, amount);

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

    public static String formatDouble(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(number);
    }

}
