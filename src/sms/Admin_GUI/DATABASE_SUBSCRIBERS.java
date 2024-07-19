package sms.Admin_GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DATABASE_SUBSCRIBERS {
    private final SimpleIntegerProperty SubscriberID;
    private final SimpleStringProperty SubscriberName;
    private final SimpleStringProperty SubscriberContactNumber;
    private final SimpleStringProperty SubscriberAddress;
    private final SimpleStringProperty SubscriberPlan;
    private final SimpleStringProperty SubscriberStatus;
    private final SimpleStringProperty SubscriberDueDate;
    private final SimpleDoubleProperty SubscriberMonthlyCharges;
    private final SimpleStringProperty SubscriberInstallationDate;

    public DATABASE_SUBSCRIBERS(int ID, String Name, String Contact, String Address, String Plan, String Status, String DueDate, double MonthlyCharges, String InstallationDate) {
        this.SubscriberID = new SimpleIntegerProperty(ID);
        this.SubscriberName = new SimpleStringProperty(Name);
        this.SubscriberContactNumber = new SimpleStringProperty(Contact);
        this.SubscriberAddress = new SimpleStringProperty(Address);
        this.SubscriberPlan = new SimpleStringProperty(Plan);
        this.SubscriberStatus = new SimpleStringProperty(Status);
        this.SubscriberDueDate = new SimpleStringProperty(DueDate);
        this.SubscriberMonthlyCharges = new SimpleDoubleProperty(MonthlyCharges);
        this.SubscriberInstallationDate = new SimpleStringProperty(InstallationDate);
    }

    public int getSubscriberID() {
        return SubscriberID.get();
    }

    public SimpleIntegerProperty subscriberIDProperty() {
        return SubscriberID;
    }

    public String getSubscriberName() {
        return SubscriberName.get();
    }

    public SimpleStringProperty subscriberNameProperty() {
        return SubscriberName;
    }

    public String getSubscriberContactNumber() {
        return SubscriberContactNumber.get();
    }

    public SimpleStringProperty subscriberContactNumberProperty() {
        return SubscriberContactNumber;
    }

    public String getSubscriberAddress() {
        return SubscriberAddress.get();
    }

    public SimpleStringProperty subscriberAddressProperty() {
        return SubscriberAddress;
    }

    public String getSubscriberPlan() {
        return SubscriberPlan.get();
    }

    public SimpleStringProperty subscriberPlanProperty() {
        return SubscriberPlan;
    }

    public String getSubscriberStatus() {
        return SubscriberStatus.get();
    }

    public SimpleStringProperty subscriberStatusProperty() {
        return SubscriberStatus;
    }

    public String getSubscriberDueDate() {
        return SubscriberDueDate.get();
    }

    public SimpleStringProperty subscriberDueDateProperty() {
        return SubscriberDueDate;
    }

    public double getSubscriberMonthlyCharges() {
        return SubscriberMonthlyCharges.get();
    }

    public SimpleDoubleProperty subscriberMonthlyChargesProperty() {
        return SubscriberMonthlyCharges;
    }

    public String getSubscriberInstallationDate() {
        return SubscriberInstallationDate.get();
    }

    public SimpleStringProperty subscriberInstallationDateProperty() {
        return SubscriberInstallationDate;
    }
}
