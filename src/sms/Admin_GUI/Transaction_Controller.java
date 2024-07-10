package sms.Admin_GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Transaction_Controller{
    Transactions_Database TDB = Transactions_Database.getInstance();

    @FXML private TableView<Transaction_Template> Trans_Table;

    @FXML private TableColumn<?, ?> Trans_ID;
    @FXML private TableColumn<?, ?> Cust_ID;
    @FXML private TableColumn<?, ?> Cust_Name;
    @FXML private TableColumn<?, ?> Deadline;
    @FXML private TableColumn<?, ?> Trans_Amount;
    @FXML private TableColumn<?, ?> Trans_Date;
    @FXML private TableColumn<?, ?> Trans_Time;

    @FXML
    public void initialize() {
        Trans_ID.setCellValueFactory(new PropertyValueFactory<>("transaction_ID"));
        Cust_ID.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        Cust_Name.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        Trans_Amount.setCellValueFactory(new PropertyValueFactory<>("transaction_Amount"));
        Deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        Trans_Date.setCellValueFactory(new PropertyValueFactory<>("transaction_Date"));
        Trans_Time.setCellValueFactory(new PropertyValueFactory<>("transaction_Time"));

        ObservableList<Transaction_Template> transactions = TDB.retrieveTransactionData();
        Trans_Table.setItems(transactions);
    }

}
