package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
    Subscribers_Database subscribers_database = Subscribers_Database.getInstance();
    int next_transaction_id;

    String CustomerName = "";
    int CustomerID = 0;
    String DueDate = "";
    String address = "";
    double amount = 0;

    public void setValues(String Customer_Name, int Customer_ID, String Due_date, String Address, double Amount) {
        next_transaction_id = transactions_database.latest_transaction_ID() + 1;

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

    public void close() {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void confirm() {
        int status = transactions_database.add_transaction(CustomerID, CustomerName, address, amount, DueDate);
        switch (status) {
            case 1 -> {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Transaction added successfully\n\nDo you want a printed receipt?", ButtonType.YES, ButtonType.NO);
                successAlert.setTitle(null);
                Optional<ButtonType> result = successAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    print();
                }
                subscribers_database.pay(CustomerID, addOneMonth(DueDate));
            }
            case 2 -> {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error: Transaction unsuccessful");
                errorAlert.setTitle(null);
                errorAlert.showAndWait();
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
                Alert printerAlert = new Alert(Alert.AlertType.ERROR, "No available printer found");
                printerAlert.setTitle(null);
                printerAlert.showAndWait();
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
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Receipt printed successfully");
                        successAlert.setTitle(null);
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Printing failed");
                        errorAlert.setTitle(null);
                        errorAlert.showAndWait();
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Could not create printer job");
                    errorAlert.setTitle(null);
                    errorAlert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "An error occurred while printing");
            errorAlert.setTitle(null);
            errorAlert.showAndWait();
        }
    }

    public static String addOneMonth(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dueDate, formatter);
        LocalDate nextDueDate = date.plusMonths(1);
        return nextDueDate.format(formatter);
    }
}
