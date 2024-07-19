package sms.Admin_GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DATABASE_CONTROLLER_MAIN implements Subscribers_Database.DatabaseObserver {

    @FXML private Button Add_New_Button;
    @FXML private Tab Current_Tab;
    @FXML private ToggleButton Location_Filter_Button1; // All
    @FXML private ToggleButton Location_Filter_Button10; // San Miguel
    @FXML private ToggleButton Location_Filter_Button11; // South Pob.
    @FXML private ToggleButton Location_Filter_Button2; // Anahawon
    @FXML private ToggleButton Location_Filter_Button3; // Base Camp
    @FXML private ToggleButton Location_Filter_Button4; // Camp 1
    @FXML private ToggleButton Location_Filter_Button5; // Colambugon
    @FXML private ToggleButton Location_Filter_Button6; // Danggawan
    @FXML private ToggleButton Location_Filter_Button7; // Dologon
    @FXML private ToggleButton Location_Filter_Button8; // North Pob.
    @FXML private ToggleButton Location_Filter_Button9; // Panadtalan
    @FXML private Tab OverDue_Tab;
    @FXML private Tab Past_Due_Tab;
    @FXML private TabPane tabPane;
    @FXML private VBox Subs_Layout;
    @FXML private VBox Subs_Layout1;
    @FXML private VBox Subs_Layout2;
    @FXML private TextField Search_Bar;
    @FXML private Label Main_Label;

    private Subscribers_Database subscribers_database = Subscribers_Database.getInstance();
    private ObservableList<DATABASE_SUBSCRIBERS> subs;
    private ObservableList<AnchorPane> allNodes = FXCollections.observableArrayList();
    ToggleButton[] locationButtons;

    @FXML
    void initialize() {
        subs = subscribers_database.getSubscribers();
        init_components();
        setupSearchBar();
        setupToggleButtons();

        Location_Filter_Button1.setSelected(true);
        filterSubscribers();

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == Past_Due_Tab || newTab == OverDue_Tab) {
                Add_New_Button.setDisable(true);
            } else {
                Add_New_Button.setDisable(false);
            }
        });
    }

    void init_components() {
        try {
            for (DATABASE_SUBSCRIBERS sub : subs) {
                FXMLLoader loaderAll = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                AnchorPane nodeAll = loaderAll.load();

                DATABASE_ITEMS_CONTROLLER controllerAll = loaderAll.getController();
                controllerAll.set_subscriber_data(sub);

                allNodes.add(nodeAll);
                Subs_Layout.getChildren().add(nodeAll); // Add all subscribers to the main layout

                if (sub.getSubscriberStatus().equals("PAST DUE")) {
                    FXMLLoader loaderPastDue = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                    AnchorPane nodePastDue = loaderPastDue.load();

                    DATABASE_ITEMS_CONTROLLER controllerPastDue = loaderPastDue.getController();
                    controllerPastDue.set_subscriber_data(sub);

                    Subs_Layout1.getChildren().add(nodePastDue);
                }

                if (sub.getSubscriberStatus().equals("OVERDUE")) {
                    FXMLLoader loaderOverdue = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                    AnchorPane nodeOverdue = loaderOverdue.load();

                    DATABASE_ITEMS_CONTROLLER controllerOverdue = loaderOverdue.getController();
                    controllerOverdue.set_subscriber_data(sub);

                    Subs_Layout2.getChildren().add(nodeOverdue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupSearchBar() {
        Search_Bar.textProperty().addListener((observable, oldValue, newValue) -> filterSubscribers());
    }

    private void setupToggleButtons() {
        locationButtons = new ToggleButton[]{
                Location_Filter_Button1, Location_Filter_Button2, Location_Filter_Button3, Location_Filter_Button4,
                Location_Filter_Button5, Location_Filter_Button6, Location_Filter_Button7, Location_Filter_Button8,
                Location_Filter_Button9, Location_Filter_Button10, Location_Filter_Button11
        };

        for (ToggleButton button : locationButtons) {
            button.setOnAction(event -> {
                if (!button.isSelected()) {
                    button.setSelected(true);
                } else {
                    for (ToggleButton otherButton : locationButtons) {
                        if (otherButton != button) {
                            otherButton.setSelected(false);
                        }
                    }
                    filterSubscribers();
                }
            });
        }
    }

    private void filterSubscribers() {
        Subs_Layout.getChildren().clear();
        Subs_Layout1.getChildren().clear();
        Subs_Layout2.getChildren().clear();

        String query = Search_Bar.getText().toLowerCase();
        Predicate<DATABASE_SUBSCRIBERS> predicate = subscriber -> {
            String lowerCaseQuery = query.toLowerCase();
            return String.format("%06d", subscriber.getSubscriberID()).contains(lowerCaseQuery) ||
                    subscriber.getSubscriberName().toLowerCase().contains(lowerCaseQuery) ||
                    subscriber.getSubscriberAddress().toLowerCase().contains(lowerCaseQuery);
        };

        ToggleButton selectedButton = getSelectedToggleButton();
        if (selectedButton != null && selectedButton != Location_Filter_Button1) {
            String selectedLocation = selectedButton.getText().toLowerCase();
            predicate = predicate.and(subscriber -> subscriber.getSubscriberAddress().toLowerCase().contains(selectedLocation));
        }

        ObservableList<DATABASE_SUBSCRIBERS> filteredSubs = subs.stream()
                .filter(predicate)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        try {
            for (DATABASE_SUBSCRIBERS sub : filteredSubs) {
                FXMLLoader loaderAll = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                AnchorPane nodeAll = loaderAll.load();

                DATABASE_ITEMS_CONTROLLER controllerAll = loaderAll.getController();
                controllerAll.set_subscriber_data(sub);

                Subs_Layout.getChildren().add(nodeAll); // Add to the main layout

                // Load node for Subs_Layout1 if status is "PAST DUE"
                if (sub.getSubscriberStatus().equals("PAST DUE")) {
                    FXMLLoader loaderPastDue = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                    AnchorPane nodePastDue = loaderPastDue.load();

                    DATABASE_ITEMS_CONTROLLER controllerPastDue = loaderPastDue.getController();
                    controllerPastDue.set_subscriber_data(sub);

                    Subs_Layout1.getChildren().add(nodePastDue);
                }

                // Load node for Subs_Layout2 if status is "OVERDUE"
                if (sub.getSubscriberStatus().equals("OVERDUE")) {
                    FXMLLoader loaderOverdue = new FXMLLoader(getClass().getResource("DATABASE_ITEMS_ALL.fxml"));
                    AnchorPane nodeOverdue = loaderOverdue.load();

                    DATABASE_ITEMS_CONTROLLER controllerOverdue = loaderOverdue.getController();
                    controllerOverdue.set_subscriber_data(sub);

                    Subs_Layout2.getChildren().add(nodeOverdue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private ToggleButton getSelectedToggleButton() {
        if (Location_Filter_Button1.isSelected()) return Location_Filter_Button1;
        if (Location_Filter_Button2.isSelected()) return Location_Filter_Button2;
        if (Location_Filter_Button3.isSelected()) return Location_Filter_Button3;
        if (Location_Filter_Button4.isSelected()) return Location_Filter_Button4;
        if (Location_Filter_Button5.isSelected()) return Location_Filter_Button5;
        if (Location_Filter_Button6.isSelected()) return Location_Filter_Button6;
        if (Location_Filter_Button7.isSelected()) return Location_Filter_Button7;
        if (Location_Filter_Button8.isSelected()) return Location_Filter_Button8;
        if (Location_Filter_Button9.isSelected()) return Location_Filter_Button9;
        if (Location_Filter_Button10.isSelected()) return Location_Filter_Button10;
        if (Location_Filter_Button11.isSelected()) return Location_Filter_Button11;
        return null;
    }

    public void Add() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DATABASE_ADD.fxml"));
            Parent root = loader.load();

            Stage posStage = new Stage();
            Scene posScene = new Scene(root);

            posStage.setTitle("Point of Sale");
            posStage.setScene(posScene);
            posStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate() {
        Platform.runLater(() ->{
            init_components();
        });
    }
}
