package sms.Admin_GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Subscribers_Database{
    private List<PaymentDatabase.DatabaseObserver> observers = new ArrayList<>();

    private static Subscribers_Database instance;
    private Connection con;

    private Subscribers_Database() {
        try {
            Properties prop = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("sms/Admin_GUI/database.properties");
            if (input == null) {
                throw new RuntimeException("Database properties not found");
            }
            prop.load(input);

            String db_username = prop.getProperty("db_username");
            String db_password = prop.getProperty("db_password");
            String db_url = "jdbc:mysql://localhost:3306/subscribers_db";
            con = DriverManager.getConnection(db_url, db_username, db_password);
        } catch (Exception e) {
            throw new RuntimeException("Database connection error", e);
        }
    }

    public static synchronized Subscribers_Database getInstance() {
        if (instance == null) {
            instance = new Subscribers_Database();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing database connection", e);
        }
    }

    public ObservableList<DATABASE_SUBSCRIBERS> getSubscribers() {
        ObservableList<DATABASE_SUBSCRIBERS> subscribers = FXCollections.observableArrayList();
        String query = "SELECT * FROM subscribers";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                int subscriber_id = res.getInt("Subscriber_id");
                String Name = res.getString("Customer_Name");
                String Contact_Number = res.getString("Contact_Number");
                String Address = res.getString("Address");
                String Plan = res.getString("Plan");
                String Acc_Status = res.getString("Acc_Status");
                String Due_Date = res.getString("Due_Date");
                double Monthly_Charges = res.getDouble("Monthly_Charges");
                String Installation_Date = res.getString("Installation_Date");
                subscribers.add(new DATABASE_SUBSCRIBERS(subscriber_id, Name, Contact_Number, Address, Plan, Acc_Status, Due_Date, Monthly_Charges, Installation_Date));
            }
            notifyObservers();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching subscribers", e);
        }
        return subscribers;
    }

    public int latestSubscriberId() {
        int maxId = 0;
        String query = "SELECT MAX(subscriber_id) as max_id FROM subscribers";
        try (PreparedStatement pstm = con.prepareStatement(query);
             ResultSet res = pstm.executeQuery()) {
            if (res.next()) {
                maxId = res.getInt("max_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching latest subscriber ID", e);
        }
        return maxId;
    }

    public void addSubscriber(String Name, String Contact, String Address, String Plan, String Acc_Status, String Due_Date, double Monthly_Charges, String Installation_Date, String curr_due_date) {
        String query = "INSERT INTO subscribers (CUSTOMER_NAME, CONTACT_NUMBER, ADDRESS, PLAN, ACC_STATUS, DUE_DATE, MONTHLY_CHARGES, INSTALLATION_DATE, current_due_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setString(1, Name);
            pstm.setString(2, Contact);
            pstm.setString(3, Address);
            pstm.setString(4, Plan);
            pstm.setString(5, Acc_Status);
            pstm.setString(6, Due_Date);
            pstm.setDouble(7, Monthly_Charges);
            pstm.setString(8, Installation_Date);
            pstm.setString(9, curr_due_date);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText(null);
                alert.setContentText("Subscriber added successfully!");
                alert.showAndWait();
            }
            notifyObservers();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding subscriber", e);
        }
    }

    public int editSubscriber(int subscriber_id, String Name, String Contact, String Address, String Plan, String Due_Date, double Monthly_Charges) {
        int status = 0;
        String query = "UPDATE subscribers SET Customer_Name = ?, Contact_Number = ?, Address = ?, Plan = ?, Due_Date = ?, Monthly_Charges = ? WHERE Subscriber_id = ?";

        try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setString(1, Name);
            pstm.setString(2, Contact);
            pstm.setString(3, Address);
            pstm.setString(4, Plan);
            pstm.setString(5, Due_Date);
            pstm.setDouble(6, Monthly_Charges);
            pstm.setInt(7, subscriber_id);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 1) {
                status = 1; // Success
            }
            notifyObservers();
        } catch (SQLException e) {
            throw new RuntimeException("Error editing subscriber", e);
        }
        return status;
    }

    public int deleteSubscriber(int subscriber_id) {
        int status = 0;
        String query = "DELETE FROM subscribers WHERE Subscriber_id = ?";

        try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setInt(1, subscriber_id);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 1) {
                status = 1; // Success
            }
            notifyObservers();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting subscriber", e);
        }
        return status;
    }

    public interface DatabaseObserver {
        void onUpdate();
    }

    public void addObserver(PaymentDatabase.DatabaseObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PaymentDatabase.DatabaseObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (PaymentDatabase.DatabaseObserver observer : observers) {
            observer.onUpdate();
        }
    }
}
