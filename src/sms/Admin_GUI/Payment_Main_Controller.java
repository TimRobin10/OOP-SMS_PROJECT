package sms.Admin_GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import sms.Admin_GUI.PaymentDatabase;
import sms.Admin_GUI.Payment_Item_Controller;
import sms.Admin_GUI.Payments_Due_Accounts;

import javax.swing.*;
import java.util.stream.Collectors;

public class Payment_Main_Controller {
    @FXML private AnchorPane Center_Pane;
    @FXML private GridPane Grid;
    @FXML private Label MainLabel;
    @FXML private ScrollPane ScrollPane;
    @FXML private TextField SearchBar;
    @FXML private FlowPane Flow;

    private final PaymentDatabase PDB = PaymentDatabase.getInstance();
    private ObservableList<Payments_Due_Accounts> dueAccounts;

    @FXML
    private void initialize() {
        initDueAccounts();
        setSearchBar();
    }

    private void initDueAccounts() {
        dueAccounts = PDB.due_accounts(); // Assigning to instance variable
        updateUI(dueAccounts); // Initially show all accounts
    }

    public void setSearchBar() {
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAccounts(newValue);
        });
    }

    private void filterAccounts(String searchText) {
        ObservableList<Payments_Due_Accounts> filteredList = dueAccounts.filtered(account ->
                String.valueOf(account.getAccount_id()).toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getAccount_name().toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getDue_date().toLowerCase().contains(searchText.toLowerCase()) ||
                        String.valueOf(account.getAmount()).toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getDue_date().toLowerCase().contains(searchText.toLowerCase())
        );

        updateUI(filteredList);
    }

    private void updateUI(ObservableList<Payments_Due_Accounts> filteredList) {
        Flow.getChildren().clear();
        try {
            for (Payments_Due_Accounts account : filteredList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment_Items.fxml"));
                AnchorPane newPaymentItem = loader.load();

                Payment_Item_Controller PIC = loader.getController();
                PIC.DueData_SetAccount(account);

                Flow.getChildren().add(newPaymentItem);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
