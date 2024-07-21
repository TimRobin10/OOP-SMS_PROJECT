
package sms.Admin_GUI;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Payment_Main_Controller{
    @FXML private AnchorPane Center_Pane;
    @FXML private GridPane Grid;
    @FXML private Label MainLabel;
    @FXML private ScrollPane ScrollPane;
    @FXML private TextField SearchBar;
    @FXML private FlowPane Flow;

    private final Subscribers_Database subscribers_database = Subscribers_Database.getInstance();
    private ObservableList<DATABASE_SUBSCRIBERS> dueAccounts;

    private static volatile Payment_Main_Controller instance;

    @FXML
    private void initialize() {
        initDueAccounts();
        setSearchBar();
        subscribers_database.addListener(this::RefreshUI);
    }

    //Singleton
    public static Payment_Main_Controller getInstance() {
        Payment_Main_Controller result = instance;
        if (instance == null) {
            synchronized (Payment_Main_Controller.class) {
                if (instance == null) {
                    result = instance = new Payment_Main_Controller();
                }
            }
        }
        return result;
    }

    public void initDueAccounts() {
        dueAccounts = subscribers_database.getSubscribers();
        updateUI(dueAccounts);
        sortDueAccounts(dueAccounts);
    }

    public void setSearchBar() {
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAccounts(newValue);
        });
    }

    private void filterAccounts(String searchText) {
        ObservableList<DATABASE_SUBSCRIBERS> filteredList = dueAccounts.filtered(account ->
                String.valueOf(account.getSubscriberID()).toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getSubscriberName().toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getSubscriberCurrentDueDate().toLowerCase().contains(searchText.toLowerCase()) ||
                        String.valueOf(account.getSubscriberMonthlyCharges()).toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getSubscriberContactNumber().toLowerCase().contains(searchText.toLowerCase())
        );

        sortDueAccounts(filteredList);
        updateUI(filteredList);
    }

    public void updateUI(ObservableList<DATABASE_SUBSCRIBERS> filteredList) {
        Flow.getChildren().clear();
        try {
            for (DATABASE_SUBSCRIBERS account : filteredList) {
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

    private LocalDate parseDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dueDate, formatter);
    }

    private void sortDueAccounts(ObservableList<DATABASE_SUBSCRIBERS> accounts) {
        accounts.sort(Comparator.comparing(account -> parseDueDate(account.getSubscriberCurrentDueDate())));
    }

    private void RefreshUI(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dueAccounts = subscribers_database.getSubscribers();
                updateUI(dueAccounts);
            }
        });
    }

}
