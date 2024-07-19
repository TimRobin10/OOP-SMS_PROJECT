package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


public class DATABASE_ITEMS_CONTROLLER {
    @FXML private Label Address;
    @FXML private Label Customer_ID;
    @FXML private Label Customer_Name;
    @FXML private Label Due_Date;
    @FXML private Label Installation_Date;
    @FXML private AnchorPane Item_Base;
    @FXML private Button Item_Delete_Button;
    @FXML private Button Item_Edit_Button;
    @FXML private Label Contact_Number;
    @FXML private Label Monthly_Charges;
    @FXML private Label Plan;
    @FXML private Label Status;
    @FXML private HBox hBox1;

    int CustomerID;
    String CustomerName;
    String Customer_Contact_Number;
    String Customer_Due_Date;
    String Customer_Installation_Date;
    double Customer_Monthly_Charges;
    String Customer_Address;
    String Customer_Plan;
    String Customer_Status;

    Subscribers_Database subs = Subscribers_Database.getInstance();

    public void set_subscriber_data(DATABASE_SUBSCRIBERS subscribers){
        Customer_ID.setText(String.format("%06d", subscribers.getSubscriberID()));
        Customer_Name.setText(subscribers.getSubscriberName());
        Address.setText(subscribers.getSubscriberAddress());
        Due_Date.setText(subscribers.getSubscriberDueDate());
        Installation_Date.setText(subscribers.getSubscriberInstallationDate());
        Plan.setText(subscribers.getSubscriberPlan());
        Status.setText(subscribers.getSubscriberStatus());
        Monthly_Charges.setText("Php " + String.format("%.2f", subscribers.getSubscriberMonthlyCharges()));
        Contact_Number.setText(subscribers.getSubscriberContactNumber());
        Installation_Date.setText(subscribers.getSubscriberInstallationDate());

        CustomerID = subscribers.getSubscriberID();
        CustomerName = subscribers.getSubscriberName();
        Customer_Contact_Number = subscribers.getSubscriberContactNumber();
        Customer_Due_Date = subscribers.getSubscriberDueDate();
        Customer_Installation_Date = subscribers.getSubscriberInstallationDate();
        Customer_Monthly_Charges = subscribers.getSubscriberMonthlyCharges();
        Customer_Address = subscribers.getSubscriberAddress();
        Customer_Plan = subscribers.getSubscriberPlan();
        Customer_Status = subscribers.getSubscriberStatus();
    }

    public void edit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DATABASE_EDIT.fxml"));
            Parent root = loader.load();
            DATABASE_EDIT_CONTROLLER controller = loader.getController();

            Stage posStage = new Stage();
            Scene posScene = new Scene(root);

            controller.setData(CustomerID,CustomerName,Customer_Contact_Number,Customer_Due_Date,Customer_Monthly_Charges,Customer_Address,Customer_Plan);

            posStage.setTitle("Edit Subscriber Information");
            posStage.setScene(posScene);
            posStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        int option = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this subscriber?", "Confirmation", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, new String[]{"Yes", "No"}, 0);
        switch(option){
            case 0 -> {
                int status = subs.deleteSubscriber(CustomerID);
                switch (status){
                    case 1 -> JOptionPane.showMessageDialog(null, "Subscriber deleted successfully", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
                    case 2 -> JOptionPane.showMessageDialog(null, "Subscriber deletion unsuccessful", "Operation Unsuccessful", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            case 1 -> {
                break;
            }
        }
    }

}
