package sms.Admin_GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sms.LoginPage.LoginDatabase;

import javax.swing.*;
import java.io.IOException;

public class Accounts_Controller_Main {

    @FXML private AnchorPane AccountsForm;
    @FXML private VBox Accounts_Layout;
    @FXML private Label HeadLabel;
    @FXML private Button RemoveAccountButton;
    @FXML private ScrollPane ScrollPane;
    @FXML private FontAwesomeIcon searchIcon;
    @FXML private TextField searchTextField;
    @FXML private ChoiceBox<String> CBox;

    LoginDatabase LDB = LoginDatabase.getInstance();

    @FXML
    private void initialize(){
        init_accounts();
        cBoxCOntent();
        setupComboBoxListener();
        setupSearchListener();
    }

    void init_accounts(){
        ObservableList<Accounts> acc = LDB.fetchAccountData();
        try {
            for(Accounts a:acc){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Accounts_Rows.fxml"));
                AnchorPane Row_Node = loader.load();

                Accounts_Rows_Controller ARC = loader.getController();
                ARC.set_Account_Data(a);

                Accounts_Layout.getChildren().add(Row_Node);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void cBoxCOntent(){
        ObservableList<String> contents = FXCollections.observableArrayList("ALL","CEO", "ADMIN", "USER", "DEFAULT");
        CBox.setItems(contents);
        CBox.getSelectionModel().selectFirst();
    }

    private void setupComboBoxListener(){
        CBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filterAccountsByRole(newValue);
        });
    }



    private void filterAccountsByRole(String role){
        ObservableList<Accounts> acc = LDB.fetchAccountData();
        if (!role.equals("ALL")) {
            acc = acc.filtered(account -> account.getAccount_Role().equalsIgnoreCase(role));
        }
        displayAccounts(acc);
    }

    private void displayAccounts(ObservableList<Accounts> accounts){
        Accounts_Layout.getChildren().clear();
        try{
            for(Accounts a : accounts){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Accounts_Rows.fxml"));
                AnchorPane Row_Node = loader.load();

                Accounts_Rows_Controller ARC = loader.getController();
                ARC.set_Account_Data(a);

                Accounts_Layout.getChildren().add(Row_Node);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupSearchListener(){
        searchTextField.setOnKeyReleased(event -> searchAccounts());
    }

    private void searchAccounts(){
        String searchText = searchTextField.getText().trim().toLowerCase();
        ObservableList<Accounts> acc = LDB.fetchAccountData();
        if (!searchText.isEmpty()) {
            acc = acc.filtered(account ->
                    (account.getAccount_Name() != null && account.getAccount_Name().toLowerCase().contains(searchText)) ||
                            (account.getUsername() != null && account.getUsername().toLowerCase().contains(searchText)) ||
                            (String.valueOf(account.getAccount_ID()) != null && String.valueOf(account.getAccount_ID()).toLowerCase().contains(searchText))
            );
        }
        displayAccounts(acc);
    }

}
