package sms.Admin_GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Payments_Due_Accounts {
    private SimpleIntegerProperty account_id;
    private SimpleStringProperty account_name;
    private SimpleStringProperty due_date;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty address;

    public Payments_Due_Accounts(int account_id, String account_name, String due_date, double amount, String address) {
        this.account_id = new SimpleIntegerProperty(account_id);
        this.account_name = new SimpleStringProperty(account_name);
        this.due_date = new SimpleStringProperty(due_date);
        this.amount = new SimpleDoubleProperty(amount);
        this.address = new SimpleStringProperty(address);
    }

    public int getAccount_id() {
        return account_id.get();
    }

    public SimpleIntegerProperty account_idProperty() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id.set(account_id);
    }

    public String getAccount_name() {
        return account_name.get();
    }

    public SimpleStringProperty account_nameProperty() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name.set(account_name);
    }

    public String getDue_date() {
        return due_date.get();
    }

    public SimpleStringProperty due_dateProperty() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date.set(due_date);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getAddress(){
        return address.get();
    }
}
