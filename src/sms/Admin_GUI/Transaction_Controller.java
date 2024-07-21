package sms.Admin_GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Transaction_Controller {
    @FXML private AnchorPane Center_Pane;
    @FXML private HBox HBOX;
    @FXML private AnchorPane Left;
    @FXML private Label Main_Label;
    @FXML private ChoiceBox<String> Month_Choice_Box;
    @FXML private ScrollPane ScrollPane;
    @FXML private TextField SearchBar;
    @FXML private ChoiceBox<String> Year_Choice_Box;
    @FXML private VBox vBox;

    private Transactions_Database trans = Transactions_Database.getInstance();
    private ObservableList<Transaction_Template> allTransactions;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        trans.addListener(this::UIupdate);
        init_transactions();
        setupSearchBar();
        setupChoiceBoxes();
    }

    void init_transactions() {
        allTransactions = trans.retrieveTransactionData();
        loadTransactions(allTransactions);
    }

    void setupSearchBar() {
        SearchBar.setOnKeyReleased(event -> filterTransactions());
    }

    void setupChoiceBoxes() {
        ObservableList<String> months = FXCollections.observableArrayList(
                "All", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        Month_Choice_Box.setItems(months);
        Month_Choice_Box.setValue("All");

        Set<Integer> years = allTransactions.stream()
                .map(transaction -> {
                    LocalDate date = LocalDate.parse(transaction.getTransaction_Date(), dateFormatter);
                    return date.getYear();
                })
                .collect(Collectors.toCollection(TreeSet::new));

        if (!years.isEmpty()) {
            int minYear = years.iterator().next();
            int maxYear = ((TreeSet<Integer>) years).last();

            ObservableList<String> yearList = FXCollections.observableArrayList("All");
            yearList.addAll(IntStream.rangeClosed(minYear, maxYear)
                    .map(i -> maxYear - (i - minYear))
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList()));
            Year_Choice_Box.setItems(yearList);
            Year_Choice_Box.setValue("All");
        } else {
            Year_Choice_Box.setItems(FXCollections.observableArrayList("All"));
            Year_Choice_Box.setValue("All");
        }

        Month_Choice_Box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filterTransactions());
        Year_Choice_Box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filterTransactions());
    }

    void loadTransactions(ObservableList<Transaction_Template> transactions) {
        vBox.getChildren().clear();
        try {
            for (Transaction_Template transaction : transactions) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Transactions_Items.fxml"));
                AnchorPane node = fxmlLoader.load();

                Transactions_Item_Controller controller = fxmlLoader.getController();
                controller.set_transaction_data(transaction);

                vBox.getChildren().add(node);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void filterTransactions() {
        String searchText = SearchBar.getText().toLowerCase();
        String selectedMonth = Month_Choice_Box.getValue();
        String selectedYear = Year_Choice_Box.getValue();

        ObservableList<Transaction_Template> filteredTransactions = FXCollections.observableArrayList();

        for (Transaction_Template transaction : allTransactions) {
            LocalDate date = LocalDate.parse(transaction.getTransaction_Date(), dateFormatter);
            boolean matchesMonth = selectedMonth.equals("All") || date.getMonth().toString().equalsIgnoreCase(selectedMonth);
            boolean matchesYear = selectedYear.equals("All") || String.valueOf(date.getYear()).equals(selectedYear);

            if (matchesMonth && matchesYear &&
                    (String.valueOf(transaction.getTransaction_Amount()).toLowerCase().contains(searchText) ||
                            transaction.getCustomer_Name().toLowerCase().contains(searchText) ||
                            String.valueOf(transaction.getTransaction_ID()).toLowerCase().contains(searchText) ||
                            String.valueOf(transaction.getCustomer_ID()).toLowerCase().contains(searchText) ||
                            transaction.getTransaction_Date().toLowerCase().contains(searchText))) {
                filteredTransactions.add(transaction);
            }
        }

        loadTransactions(filteredTransactions);
    }

    void UIupdate(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                allTransactions = trans.retrieveTransactionData();
                loadTransactions(allTransactions);
            }
        });
    }
}
