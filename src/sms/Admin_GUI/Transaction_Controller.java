package sms.Admin_GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;


public class Transaction_Controller{
    private ObservableList<Transaction_Template> transactions;
    Transactions_Database TDB = Transactions_Database.getInstance();


    @FXML private TableView<Transaction_Template> Trans_Table;

    @FXML private TableColumn<?, ?> Trans_ID;
    @FXML private TableColumn<?, ?> Cust_ID;
    @FXML private TableColumn<?, ?> Cust_Name;
    @FXML private TableColumn<?, ?> Deadline;
    @FXML private TableColumn<?, ?> Trans_Amount;
    @FXML private TableColumn<?, ?> Trans_Date;
    @FXML private TableColumn<?, ?> Trans_Time;
    @FXML private ChoiceBox<String> Month_Choice_Box;
    @FXML private ChoiceBox<String> Year_Choice_Box;
    @FXML private TextField SearchBar;


    @FXML
    public void initialize() {
        Trans_ID.setCellValueFactory(new PropertyValueFactory<>("transaction_ID"));
        Cust_ID.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        Cust_Name.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        Trans_Amount.setCellValueFactory(new PropertyValueFactory<>("transaction_Amount"));
        Deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        Trans_Date.setCellValueFactory(new PropertyValueFactory<>("transaction_Date"));
        Trans_Time.setCellValueFactory(new PropertyValueFactory<>("transaction_Time"));

        ObservableList<String> months = FXCollections.observableArrayList(
                "All", "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"
        );
        Month_Choice_Box.setItems(months);
        Month_Choice_Box.getSelectionModel().selectFirst();

        transactions = TDB.retrieveTransactionData();
        Year_ComboBox_SetUp();

        Month_Choice_Box.setOnAction(event -> Filter_Transactions_Date());
        Year_Choice_Box.setOnAction(event -> Filter_Transactions_Date());

        ObservableList<Transaction_Template> transactions = TDB.retrieveTransactionData();
        Trans_Table.setItems(transactions);

        setupSearchListener();
    }

    private void Year_ComboBox_SetUp(){
        List<String> years = new ArrayList<>();
        years.add("All");

        int earliest_year = Integer.MAX_VALUE;
        int latest_year = Integer.MIN_VALUE;

        for(Transaction_Template transaction : transactions){
            String transactionYear = transaction.getTransaction_Date().substring(0, 4); // Assuming format "yyyy-MM-dd"
            int year = Integer.parseInt(transactionYear);
            if (year > latest_year) {
                latest_year = year;
            }
            if (year < earliest_year) {
                earliest_year = year;
            }
        }

        for (int year = latest_year; year >= earliest_year; year--) {
            years.add(String.valueOf(year));
        }

        Year_Choice_Box.setItems(FXCollections.observableArrayList(years));
        Year_Choice_Box.getSelectionModel().selectFirst();
    }

    private void Filter_Transactions_Date() {
        String selectedMonth = Month_Choice_Box.getSelectionModel().getSelectedItem();
        String selectedYear = Year_Choice_Box.getSelectionModel().getSelectedItem();

        switch(selectedMonth){
            case "All" -> selectedMonth = "All";
            case "January" -> selectedMonth = "01";
            case "February" -> selectedMonth = "02";
            case "March" -> selectedMonth = "03";
            case "April" -> selectedMonth = "04";
            case "May" -> selectedMonth = "05";
            case "June" -> selectedMonth = "06";
            case "July" -> selectedMonth = "07";
            case "August" -> selectedMonth = "08";
            case "September" -> selectedMonth = "09";
            case "October" -> selectedMonth = "10";
            case "November" -> selectedMonth = "11";
            case "December" -> selectedMonth = "12";
        }

        ObservableList<Transaction_Template> filteredData = FXCollections.observableArrayList();

        // Filter transactions based on selected month and year
        for (Transaction_Template transaction : transactions) {
            String transactionMonth = transaction.getTransaction_Date().substring(5, 7); // Assuming format "yyyy-MM-dd"
            String transactionYear = transaction.getTransaction_Date().substring(0, 4); // Assuming format "yyyy-MM-dd"

            boolean matchMonth = selectedMonth.equals("All") || selectedMonth.equals(transactionMonth);
            boolean matchYear = selectedYear.equals("All") || selectedYear.equals(transactionYear);

            if (matchMonth && matchYear) {
                filteredData.add(transaction);
            }
        }

        // Update table with filtered data
        Trans_Table.setItems(filteredData);
    }

    private void setupSearchListener() {
        SearchBar.setOnKeyReleased(event -> searchTransactions());
    }

    private void searchTransactions() {
        String searchText = SearchBar.getText().trim().toLowerCase();
        ObservableList<Transaction_Template> filteredData = FXCollections.observableArrayList();

        for (Transaction_Template transaction : transactions) {
            String transactionID = String.valueOf(transaction.getTransaction_ID()) != null ? String.valueOf(transaction.getTransaction_ID()) : "";
            String customerID = String.valueOf(transaction.getCustomer_ID()) != null ? String.valueOf(transaction.getCustomer_ID()) : "";
            String customerName = transaction.getCustomer_Name() != null ? transaction.getCustomer_Name().toLowerCase() : "";
            String transactionDate = transaction.getTransaction_Date() != null ? transaction.getTransaction_Date().toLowerCase() : "";
            String deadline = transaction.getDeadline() != null ? transaction.getDeadline().toLowerCase() : "";

            if (transactionID.startsWith(searchText) ||
                    customerID.startsWith(searchText) ||
                    customerName.startsWith(searchText) ||
                    transactionDate.startsWith(searchText) ||
                    deadline.startsWith(searchText)) {
                filteredData.add(transaction);
            }
        }

        Trans_Table.setItems(filteredData);
    }

}

