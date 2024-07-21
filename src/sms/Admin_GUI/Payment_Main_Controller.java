package sms.Admin_GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Payment_Main_Controller {
    @FXML private AnchorPane Center_Pane;
    @FXML private GridPane Grid;
    @FXML private Label MainLabel;
    @FXML private ScrollPane ScrollPane;
    @FXML private TextField SearchBar;
    @FXML private FlowPane Flow;

    private final Subscribers_Database subscribers_database = Subscribers_Database.getInstance();
    private ObservableList<DATABASE_SUBSCRIBERS> dueAccounts;

    @FXML
    private void initialize() {
        initDueAccounts();
        setSearchBar();
        subscribers_database.addListener(this::RefreshUI);
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
        if (searchText == null || searchText.isEmpty()) {
            updateUI(dueAccounts);
            return;
        }

        List<DATABASE_SUBSCRIBERS> filteredList = new ArrayList<>(dueAccounts.filtered(account ->
                String.valueOf(account.getSubscriberID()).toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getSubscriberName().toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getSubscriberCurrentDueDate().toLowerCase().contains(searchText.toLowerCase()) ||
                        String.valueOf(account.getSubscriberMonthlyCharges()).toLowerCase().contains(searchText.toLowerCase()) ||
                        account.getSubscriberContactNumber().toLowerCase().contains(searchText.toLowerCase())
        ));

        sortDueAccounts(FXCollections.observableArrayList(filteredList));
        updateUI(FXCollections.observableArrayList(filteredList));
    }

    public void updateUI(ObservableList<DATABASE_SUBSCRIBERS> filteredList) {
        Platform.runLater(() -> {
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
                JOptionPane.showMessageDialog(null, "Error loading payment items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private LocalDate parseDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dueDate, formatter);
    }

    private void sortDueAccounts(ObservableList<DATABASE_SUBSCRIBERS> accounts) {
        List<DATABASE_SUBSCRIBERS> list = new ArrayList<>(accounts);
        list.sort(Comparator.comparing(account -> parseDueDate(account.getSubscriberCurrentDueDate())));
        accounts.setAll(list);
    }

    private void RefreshUI() {
        Platform.runLater(() -> {
            dueAccounts = subscribers_database.getSubscribers();
            updateUI(dueAccounts);
        });
    }
}
