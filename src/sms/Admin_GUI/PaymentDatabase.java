package sms.Admin_GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sms.LoginPage.LoginDatabase;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class PaymentDatabase {
    String db_username, db_password;
    static Connection connection;

    //FOR SINGLETON DESIGN
    private static volatile PaymentDatabase instance;

    public static PaymentDatabase getInstance(){
        PaymentDatabase result = instance;

        if(result == null){
            synchronized(LoginDatabase.class){
                result = instance;
                if(result == null){
                    instance = result = new PaymentDatabase();
                }
            }
        }
        return result;
    }

    private PaymentDatabase() {

        Properties prop = new Properties();

        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("sms/LoginPage/database.properties");
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

    public void closeConnection_Payments() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public ObservableList<Payments_Due_Accounts> due_accounts(){
        ObservableList<Payments_Due_Accounts> dues = FXCollections.observableArrayList();

        String query = "SELECT * FROM DEADLINES";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int customer_id = resultSet.getInt("customer_id");
                String customer_name = resultSet.getString("customer_name");
                double amount = resultSet.getDouble("subscription_amount");
                String deadline = resultSet.getString("deadline");
                String address = resultSet.getString("address");
                dues.add(new Payments_Due_Accounts(customer_id,customer_name,deadline,amount, address));
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return dues;
    }
}
