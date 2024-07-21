package sms.Admin_GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import javax.swing.*;
import java.util.Optional;

public class DATABASE_EDIT_CONTROLLER {
    @FXML private TextField AddressField;
    @FXML private Label AddressText;
    @FXML private Button CancelButton;
    @FXML private ComboBox<String> DueDate;
    @FXML private TextField DueDateField;
    @FXML private Label DueDateText;
    @FXML private TextField NameTF;
    @FXML private TextField MonthlyChargeField;
    @FXML private Label MonthlyChargeText;
    @FXML private Label NameText;
    @FXML private TextField NumberField;
    @FXML private Label NumberText;
    @FXML private ComboBox<String> PlanComboBox;
    @FXML private TextField PlanField;
    @FXML private Label PlanText;
    @FXML private Button SaveButton;
    @FXML private AnchorPane mainAnchor;
    @FXML private Label textAddClient;

    Subscribers_Database subs = Subscribers_Database.getInstance();
    int customer_id;

    @FXML
    public void initialize() {
        initializeMonthlyChargeField();
        initializePlanComboBox();
        initializeDueDateComboBox();

    }

    void setData(int Customer_ID,String Customer_Name, String Contact_Number, String Due_Date, double Monthyly_Charges, String Customer_Address, String Customer_Plan){
        NameTF.setText(Customer_Name);
        AddressField.setText(Customer_Address);
        DueDateField.setText(Due_Date);
        MonthlyChargeField.setText(String.valueOf(Monthyly_Charges));
        PlanField.setText(Customer_Plan);
        NumberField.setText(Contact_Number);
        customer_id = Customer_ID;
    }

    private void initializeMonthlyChargeField() {
        MonthlyChargeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                MonthlyChargeField.setText(oldValue);
            }
        });


        StringConverter<Double> converter = new DoubleStringConverter();
        TextFormatter<Double> formatter = new TextFormatter<>(converter);
        MonthlyChargeField.setTextFormatter(formatter);
    }

    private void initializePlanComboBox() {
        PlanComboBox.setItems(FXCollections.observableArrayList("POSTPAID", "PREPAID"));

        PlanComboBox.setOnAction(event -> {
            String selectedPlan = PlanComboBox.getValue();
            PlanField.setText(selectedPlan);
        });
    }

    private void initializeDueDateComboBox() {
        DueDate.setItems(FXCollections.observableArrayList("7th", "15th", "21st", "30th"));

        DueDate.setOnAction(event -> {
            String selectedDueDate = DueDate.getValue();
            DueDateField.setText(selectedDueDate);
        });
    }

    public void save() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Changes");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to save the changes?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            subs.editSubscriber(customer_id, NameTF.getText(),NumberField.getText(),AddressField.getText(),PlanField.getText(),DueDateField.getText(),Double.parseDouble(MonthlyChargeField.getText()));
            alert.setTitle("Change Saved");
            alert.setHeaderText(null);
            alert.setContentText("Subscriber infromation successfully edited.");
            alert.getButtonTypes().setAll(new ButtonType("Ok"));
            alert.showAndWait();
            cancel();
        } else {

        }
    }

    public void cancel(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
}