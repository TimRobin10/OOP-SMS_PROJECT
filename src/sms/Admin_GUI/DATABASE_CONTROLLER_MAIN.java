package sms.Admin_GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DATABASE_CONTROLLER_MAIN {

    @FXML private ComboBox<String> Filter_Combobox;
    @FXML private Label Main_Label;
    @FXML private ToggleButton Location_Filter_Button1; // All
    @FXML private ToggleButton Location_Filter_Button2; // Anahawon
    @FXML private ToggleButton Location_Filter_Button3; // Base Camp
    @FXML private ToggleButton Location_Filter_Button4; // Camp 1
    @FXML private ToggleButton Location_Filter_Button5; // Colambugon
    @FXML private ToggleButton Location_Filter_Button6; // Danggawan
    @FXML private ToggleButton Location_Filter_Button7; // Dologon
    @FXML private ToggleButton Location_Filter_Button8; // North Poblacion
    @FXML private ToggleButton Location_Filter_Button9; // Panadtalan
    @FXML private ToggleButton Location_Filter_Button10; // San Miguel
    @FXML private ToggleButton Location_Filter_Button11; // South Poblacion
    @FXML private TextField Search_Bar;
    @FXML private VBox Subs_Layout;

    private final ToggleGroup location_Filter_ToggleGroup = new ToggleGroup();
    private final Subscribers_Database subscribers_database = Subscribers_Database.getInstance();
    private ObservableList<DATABASE_SUBSCRIBERS> subs;
    private static volatile DATABASE_CONTROLLER_MAIN instance;

    public DATABASE_CONTROLLER_MAIN() {}

    @FXML
    void initialize() {
        loadSubscribers();
        initialize_combobox();
        initialize_togglebutton();
        init_searchbar();
        init_table();

        subscribers_database.addListener(this::UIUpdater);
    }

    public static DATABASE_CONTROLLER_MAIN getinstance() {
        DATABASE_CONTROLLER_MAIN result = instance;
        if (instance == null) {
            synchronized (DATABASE_SUBSCRIBERS.class) {
                result = instance;
                if (result == null) {
                    instance = result = new DATABASE_CONTROLLER_MAIN();
                }
            }
        }
        return result;
    }

    private void loadSubscribers() {
       subs = subscribers_database.getSubscribers();
    }


    public void initialize_combobox() {
        ObservableList<String> combobox_items = FXCollections.observableArrayList("All", "Connected", "Past Due", "Overdue");
        Filter_Combobox.setItems(combobox_items);
        Filter_Combobox.getSelectionModel().selectFirst();

        Filter_Combobox.valueProperty().addListener((observable, oldValue, newValue) -> update_table());
    }

    public void init_searchbar() {
        Search_Bar.textProperty().addListener((observable, oldValue, newValue) -> update_table());
    }

    public void initialize_togglebutton() {
        Location_Filter_Button1.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button2.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button3.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button4.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button5.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button6.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button7.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button8.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button9.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button10.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button11.setToggleGroup(location_Filter_ToggleGroup);
        Location_Filter_Button1.setSelected(true);

        location_Filter_ToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            } else {
                update_table();
            }
        });
    }

    public void init_table() {
        update_table();
    }

    public void update_table() {
        Subs_Layout.getChildren().clear();

        ObservableList<DATABASE_SUBSCRIBERS> subs_items = subscribers_database.getSubscribers();
        String selectedPlace = getSelectedPlace();
        String selectedStatus = Filter_Combobox.getValue().toLowerCase();

        for (DATABASE_SUBSCRIBERS sub : subs_items) {
            boolean matchesPlace = selectedPlace.equals("All") || sub.getSubscriberAddress().contains(selectedPlace);
            boolean matchesStatus = selectedStatus.equals("all") || sub.getSubscriberStatus().toLowerCase().equals(selectedStatus);
            boolean matchesSearch = Search_Bar.getText().isEmpty() || String.format("%06d", sub.getSubscriberID()).equals(Search_Bar.getText())
                    || sub.getSubscriberName().toLowerCase().contains(Search_Bar.getText().toLowerCase()) || sub.getSubscriberAddress().toLowerCase().contains(Search_Bar.getText().toLowerCase())
                    || sub.getSubscriberStatus().toLowerCase().contains(Search_Bar.getText().toLowerCase()) || sub.getSubscriberStatus().equals(Search_Bar.getText()) || sub.getSubscriberPlan().toLowerCase().equals(Search_Bar.getText().toLowerCase())
                    || String.valueOf(sub.getSubscriberMonthlyCharges()).equals(Search_Bar.getText());

            if (matchesPlace && matchesStatus && matchesSearch) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                    AnchorPane node = loader.load();

                    DATABASE_ITEMS_CONTROLLER DIC = loader.getController();
                    DIC.set_subscriber_data(sub);

                    Subs_Layout.getChildren().add(node);
                } catch (IOException e) {
                    showErrorAlert("Loading Error", "Unable to load subscriber data.", e.getMessage());
                }
            }
        }
    }

    private String getSelectedPlace() {
        Toggle selectedToggle = location_Filter_ToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            Main_Label.setText(((ToggleButton) selectedToggle).getText());
            return ((ToggleButton) selectedToggle).getText();
        }
        return "All";
    }

    public void Add() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DATABASE_ADD.fxml"));
            Parent root = loader.load();

            Stage posStage = new Stage();
            Scene posScene = new Scene(root);

            posStage.setTitle("Add Subscriber");
            posStage.getIcons().add(new Image("/resources/Image_Resources/4.png"));
            posStage.setScene(posScene);
            posStage.show();
        } catch (IOException e) {
            showErrorAlert("Add Subscriber Error", "Unable to open the add subscriber window.", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    void UIUpdater(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                update_table();
            }
        });
    }

}
