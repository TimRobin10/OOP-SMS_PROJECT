package sms.Admin_GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction_Template {
    private final SimpleIntegerProperty Transaction_ID;
    private final SimpleIntegerProperty Customer_ID;
    private final SimpleStringProperty Customer_Name;
    private final SimpleDoubleProperty Transaction_Amount;
    private final SimpleStringProperty Deadline;
    private final SimpleStringProperty Transaction_Date;
    private final SimpleStringProperty Transaction_Time;

    public Transaction_Template(int transactionId, int customerId, String customerName, double transactionAmount, String deadline, String transactionDate, String transactionTime) {
        Transaction_ID = new SimpleIntegerProperty(transactionId);
        Customer_ID = new SimpleIntegerProperty(customerId);
        Customer_Name = new SimpleStringProperty(customerName);
        Transaction_Amount = new SimpleDoubleProperty(transactionAmount);
        Deadline = new SimpleStringProperty(deadline);
        Transaction_Date = new SimpleStringProperty(transactionDate);
        Transaction_Time = new SimpleStringProperty(transactionTime);
    }

    public int getTransaction_ID() {
        return Transaction_ID.get();
    }

    public SimpleIntegerProperty transaction_IDProperty() {
        return Transaction_ID;
    }

    public int getCustomer_ID() {
        return Customer_ID.get();
    }

    public SimpleIntegerProperty customer_IDProperty() {
        return Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name.get();
    }

    public SimpleStringProperty customer_NameProperty() {
        return Customer_Name;
    }

    public double getTransaction_Amount() {
        return Transaction_Amount.get();
    }

    public SimpleDoubleProperty transaction_AmountProperty() {
        return Transaction_Amount;
    }

    public String getDeadline() {
        return Deadline.get();
    }

    public SimpleStringProperty deadlineProperty() {
        return Deadline;
    }

    public String getTransaction_Date() {
        return Transaction_Date.get();
    }

    public SimpleStringProperty transaction_DateProperty() {
        return Transaction_Date;
    }

    public String getTransaction_Time() {
        return Transaction_Time.get();
    }

    public SimpleStringProperty transaction_TimeProperty() {
        return Transaction_Time;
    }
}
