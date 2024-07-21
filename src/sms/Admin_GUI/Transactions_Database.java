package sms.Admin_GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Transactions_Database {
    String db_username, db_password;
    Connection connection;
    ObservableList<Runnable> listeners = FXCollections.observableArrayList();

    //SINGLETON
    private static volatile Transactions_Database instance;
    public static Transactions_Database getInstance() {
        Transactions_Database result = instance;
        if (result == null) {
            synchronized (Transactions_Database.class) {
                if (instance == null) {
                    result = new Transactions_Database();
                }
            }
        }
        return result;
    }

    //Constructor
    private Transactions_Database() {
        Properties prop = new Properties();

        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("sms/Admin_GUI/database.properties");
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Database properties not found", "Database Error", JOptionPane.ERROR_MESSAGE);
            } else {
                prop.load(input);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        db_username = prop.getProperty("db_username");
        db_password = prop.getProperty("db_password");
        String db_url = "jdbc:mysql://localhost:3306/transactions_db";

        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void closeConnection_Transaction() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public ObservableList<Transaction_Template> retrieveTransactionData(){
        ObservableList<Transaction_Template> transactions_made = FXCollections.observableArrayList();

        String query = "SELECT * FROM Transactions_list ORDER BY transaction_id DESC";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int transaction_id = resultSet.getInt("transaction_id");
                int customer_id = resultSet.getInt("customer_id");
                String customer_name = resultSet.getString("customer_name");
                double transaction_amount = resultSet.getDouble("transaction_amount");
                String deadline = resultSet.getString("deadline");
                String transaction_date = resultSet.getString("transaction_date");
                String transaction_time = resultSet.getString("transaction_time");
                String customer_address = resultSet.getString("customer_address");
                transactions_made.add(new Transaction_Template(transaction_id,customer_id,customer_name,customer_address, transaction_amount, deadline,transaction_date,transaction_time));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Database Error: " + e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return transactions_made;
    }

    public int latest_transaction_ID() {
        int transaction_id = 0;

        String query = "SELECT MAX(transaction_id) AS max_transaction_id FROM Transactions_list";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                transaction_id = resultSet.getInt("max_transaction_id");  // Use the alias defined in the SQL query
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching latest transaction ID", e);
        }
        return transaction_id;
    }

    public int add_transaction(int Customer_ID, String Customer_Name, String address, double Transaction_amount, String deadline){
        int status = 0;
        String query = "INSERT INTO TRANSACTIONS_LIST (CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_ADDRESS, TRANSACTION_AMOUNT, DEADLINE, TRANSACTION_DATE, TRANSACTION_TIME) VALUES (?,?,?,?,?,CURRENT_DATE,CURRENT_TIME)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Customer_ID);
            preparedStatement.setString(2, Customer_Name);
            preparedStatement.setString(3, address);
            preparedStatement.setDouble(4, Transaction_amount);
            preparedStatement.setString(5, deadline);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                status = 1;
            } else {
                status = 0;
            }
            notifyListeners();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public void TDBaddListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

}
