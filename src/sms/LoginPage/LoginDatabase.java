package sms.LoginPage;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class LoginDatabase {
    String db_username, db_password;
    Connection connection;

    //FOR SINGLETON DESIGN
    private static volatile LoginDatabase instance;

    public static LoginDatabase getInstance(){
        LoginDatabase result = instance;

        if(result == null){
            synchronized(LoginDatabase.class){
                result = instance;
                if(result == null){
                    instance = result = new LoginDatabase();
                }
            }
        }
        return result;
    }

    LoginDatabase() {

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
        String db_url = "jdbc:mysql://localhost:3306/users_db";

        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    int verifyAccount(String username_input, String password_input){
        int status = 2;
        String sql = "select * from accounts where username=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username_input);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                if(resultSet.getString("account_password").equals(password_input)){
                    status = 0;
                } else{
                    status = 1;
                }
            } else{
                status = 3;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return status;
    }

    String get_account_role(String username_input){
        String role = "";
        String sql = "select * from accounts where username=?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username_input);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getString("account_role");
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the given username", "User Not Found", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return role;
    }

    boolean verify_username(String username_input) {
        String username_query = "select* from accounts where username=?";
        try {
            PreparedStatement statement = connection.prepareStatement(username_query);
            statement.setString(1, username_input);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    void add_account(String username_input, String password_input){
        String sql = "insert into accounts (username, account_password) values (?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username_input);
            statement.setString(2, password_input);

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected == 0){
                JOptionPane.showMessageDialog(null,"Account Added Successfully", "Account Added", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
