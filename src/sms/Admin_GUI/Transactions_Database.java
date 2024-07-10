package sms.Admin_GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Transactions_Database {
    String db_username, db_password;
    Connection connection;

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

        String query = "SELECT * FROM Transactions_list";
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
                transactions_made.add(new Transaction_Template(transaction_id,customer_id,customer_name,transaction_amount,deadline,transaction_date,transaction_time));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Database Error: " + e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return transactions_made;
    }




}
