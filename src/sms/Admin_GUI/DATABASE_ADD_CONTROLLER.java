package sms.Admin_GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DATABASE_ADD_CONTROLLER implements Initializable {

    @FXML private Label AddressText;
    @FXML private ComboBox<String> BarangayComboBox;
    @FXML private Button CancelButton;
    @FXML private ComboBox<String> DistrictComboBox;
    @FXML private Label DueDateText;
    @FXML private Label MonthlyChargeText;
    @FXML private Label NameText;
    @FXML private Label NumberText;
    @FXML private ComboBox<String> PlanComboBox;
    @FXML private Label PlanText;
    @FXML private Button SaveButton;
    @FXML private AnchorPane mainAnchor;
    @FXML private Label textAddClient;
    @FXML private TextField Last_NameTF;
    @FXML private TextField Given_NameTF;
    @FXML private TextField Middle_InitialTF;
    @FXML private ComboBox<String> DueDate;
    @FXML private TextField AddressField;
    @FXML private TextField DistrictField;
    @FXML private TextField PlanField;
    @FXML private TextField DueDateField;
    @FXML private TextField MonthlyChargeField;
    @FXML private TextField NumberField;

    Subscribers_Database subs = Subscribers_Database.getInstance();
    private final Map<String, ObservableList<String>> districtData = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDistrictData();
        initializeBarangayComboBox();
        initializePlanComboBox();
        initializeDistrictComboBox();
        initializeDueDateComboBox();
        initializeMiddleInitialTextField();
        initializeMonthlyChargeField();
        initializeNumberField();

    }

    private void initializeDistrictData() {
        districtData.put("Base Camp", FXCollections.observableArrayList(
                "Purok 1", "Purok 2", "Purok 3", "Purok 3, NoyNay Village", "Purok 9", "Purok 9, Urban Poor", "Purok 7, Kalagutay"));
        districtData.put("Anahawon", FXCollections.observableArrayList(
                "Purok 2", "Purok 4", "Purok 6", "Purok 7"));
        districtData.put("Camp 1", FXCollections.observableArrayList());
        districtData.put("Colambugon", FXCollections.observableArrayList(
                "Purok 2", "Purok 3"));
        districtData.put("Danggawan", FXCollections.observableArrayList(
                "Purok 1", "Purok 5", "Kalinga"));
        districtData.put("Dologon", FXCollections.observableArrayList(
                "Purok 1", "Purok 1A", "Purok 2", "Purok 2A", "Purok 3", "Purok 3A", "Purok 4", "Purok 4A",
                "Purok 5", "Purok 5A", "Purok 5A, Paglaum Village", "Purok 6", "Purok 6, Talipapa", "Purok 6A",
                "Purok 9", "Purok 9, Donhai", "Purok 10"));
        districtData.put("North Poblacion", FXCollections.observableArrayList(
                "Purok 1", "Purok 1, Tuban Village", "Purok 1A", "Purok 2", "Purok 2A", "Purok 2A, Delcho Homes",
                "Purok 3", "Purok 4", "Purok 5", "Purok 5A", "Purok 5A, Teacher’s Village", "Purok 6", "Purok 6, Bulangan",
                "Purok 6A", "Purok 7", "Purok 7A", "Purok 8", "Purok 8A", "Purok 9", "Purok 9B", "Southern"));
        districtData.put("Panadtalan", FXCollections.observableArrayList(
                "Purok 2B", "Purok 5A, JVO Village"));
        districtData.put("San Miguel", FXCollections.observableArrayList(
                "Purok 1A", "Purok 1B", "Purok 2B", "Purok 3", "Purok 10A, Green Hills", "Purok 10B", "Paglaum Village",
                "Kalinga Village", "Dairy Farm Guard House"));
        districtData.put("South Poblacion", FXCollections.observableArrayList(
                "Purok 1", "Purok 1A", "Purok 1B", "Purok 2", "Purok 2, Tamarind St.", "Purok 2A, Tamarind St.",
                "Purok 3", "Purok 3A", "Purok 4", "Purok 4A", "Purok 5", "Purok 5A", "Purok 6", "Purok 6A",
                "Purok 7", "Purok 7A", "Purok 8", "Purok 9", "Purok 9B", "Purok 10", "Wet Market", "Perimeter"));
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

    private void initializeBarangayComboBox() {
        ObservableList<String> barangayList = FXCollections.observableArrayList(
                "Anahawon", "Base Camp", "Camp 1", "Colambugon", "Danggawan", "Dologon", "North Poblacion", "Panadtalan", "San Miguel", "South Poblacion"
        );
        BarangayComboBox.setItems(barangayList);

        BarangayComboBox.setOnAction(event -> {
            String selectedBarangay = BarangayComboBox.getValue();
            AddressField.setText(selectedBarangay);

            ObservableList<String> districts = districtData.get(selectedBarangay);
            if (selectedBarangay == null || selectedBarangay.isEmpty()) {
                DistrictComboBox.setItems(FXCollections.emptyObservableList());
                DistrictComboBox.setDisable(true);
                DistrictField.setDisable(true);
                DistrictField.setText("");
            } else {
                DistrictComboBox.setItems(districts != null ? districts : FXCollections.emptyObservableList());
                if (districts == null || districts.isEmpty()) {
                    DistrictComboBox.setDisable(true);
                    DistrictField.setDisable(true);
                    DistrictField.setText("");
                } else {
                    DistrictComboBox.setDisable(false);
                    DistrictField.setDisable(false);
                }
            }
        });

        String initialBarangay = BarangayComboBox.getValue();
        ObservableList<String> initialDistricts = districtData.get(initialBarangay);
        if (initialBarangay == null || initialBarangay.isEmpty()) {
            DistrictComboBox.setItems(FXCollections.emptyObservableList());
            DistrictComboBox.setDisable(true);
            DistrictField.setDisable(true);
            DistrictField.setText("");
        } else {
            DistrictComboBox.setItems(initialDistricts != null ? initialDistricts : FXCollections.emptyObservableList());
            if (initialDistricts == null || initialDistricts.isEmpty()) {
                DistrictComboBox.setDisable(true);
                DistrictField.setDisable(true);
                DistrictField.setText("");
            } else {
                DistrictComboBox.setDisable(false);
                DistrictField.setDisable(false);
            }
        }
    }

    private void initializeDistrictComboBox() {
        DistrictComboBox.setOnAction(event -> {
            String selectedDistrict = DistrictComboBox.getValue();
            DistrictField.setText(selectedDistrict);
        });
    }

    private void initializeMiddleInitialTextField() {
        Middle_InitialTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                Middle_InitialTF.setText(oldValue);
            } else if (!newValue.matches("[A-Z]*")) {
                Middle_InitialTF.setText(newValue.replaceAll("[^A-Z]", ""));
            }
        });
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

    private void initializeNumberField() {
        NumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                NumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 11) {
                NumberField.setText(oldValue);
            }
        });
    }

    public void save() {
        if (Last_NameTF.getText().isBlank() || Given_NameTF.getText().isBlank() ||
                NumberText.getText().isBlank() || PlanText.getText().isBlank() ||
                (!DistrictField.isDisable() && DistrictField.getText().isBlank())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Complete Credentials");
            alert.setContentText("Please fill out all required fields.");
            alert.showAndWait();
        } else {
            String name = Last_NameTF.getText() + ", " + Given_NameTF.getText() + " " + Middle_InitialTF.getText() +".";
            String contact = NumberText.getText();
            String plan = PlanText.getText();
            String acc_Status = "ACTIVE";
            String due_Date = DueDateField.getText();
            String address = AddressField.getText();
            String district = DistrictField.getText();
            double charge = Double.parseDouble(MonthlyChargeField.getText());
            String installation = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String current_due_date = calculateNextDueDate(due_Date, LocalDate.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String address_final;
            if (district == null || district.isBlank()) {
                address_final = address;
            } else {
                address_final = district + ", " + address;
            }

            subs.addSubscriber(name,contact,address_final,plan,acc_Status,due_Date,charge,installation,current_due_date);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText(name + " added successfully.");

            close();
        }
    }

    private LocalDate calculateNextDueDate(String due_Date, LocalDate currentDate) {
        LocalDate nextDueDate;
        switch (due_Date) {
            case "7th":
                nextDueDate = currentDate.with(TemporalAdjusters.firstDayOfNextMonth()).withDayOfMonth(7);
                break;
            case "15th":
                nextDueDate = currentDate.with(TemporalAdjusters.firstDayOfNextMonth()).withDayOfMonth(15);
                break;
            case "21st":
                nextDueDate = currentDate.with(TemporalAdjusters.firstDayOfNextMonth()).withDayOfMonth(21);
                break;
            case "30th":
                nextDueDate = currentDate.with(TemporalAdjusters.firstDayOfNextMonth()).withDayOfMonth(30);
                break;
            default:
                throw new IllegalArgumentException("Invalid due date: " + due_Date);
        }
        return nextDueDate;
    }

    public void close(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
}