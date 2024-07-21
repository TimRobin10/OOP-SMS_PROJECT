package sms.LoginPage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sms.Admin_GUI.Accounts;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class LoginDatabase {
    String db_username, db_password;
    static Connection connection;
    public static AccountObjects current_account = new AccountObjects();
    ObservableList<Runnable> listeners = FXCollections.observableArrayList();

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

    private LoginDatabase() {

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

    /*public void closeConnection_Login() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }*/

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

    String save_data(String username_input){
        String role = "";
        String sql = "select * from accounts where username=?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username_input);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("account_password");
                String account_name = resultSet.getString("account_name");
                String account_role = resultSet.getString("account_role");
                current_account = new AccountObjects(user_id,username,password,account_name,account_role);
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the given username", "User Not Found", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return role;
    }

    public boolean verify_username(String username_input) {
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

    public ObservableList<Accounts> fetchAccountData(){
        ObservableList<Accounts> accounts = FXCollections.observableArrayList();
        String query = "SELECT * FROM ACCOUNTS";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("account_password");
                String account_name = resultSet.getString("account_name");
                String account_role = resultSet.getString("account_role");
                accounts.add(new Accounts(id, username, password, account_name, account_role));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return accounts;
    }

    public void editAccountInfo(String account_name, String account_password, String username, int ID) {
        String query = "UPDATE accounts SET account_name = ?, account_password = ?, username = ? WHERE user_id = ?";

        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, account_name);
            psmt.setString(2, account_password);
            psmt.setString(3, username);
            psmt.setInt(4, ID);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Information Updated");
        alert.setHeaderText(null);
        alert.setContentText("Account Information Updated");
        alert.showAndWait();
        notifyListeners();
    }

    public void editAccountInfoMain(String account_name, String account_password, String acc_role, String username, int ID) {
        String query = "UPDATE accounts SET account_name = ?, account_password = ?, username = ?, account_role = ? WHERE user_id = ?";

        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, account_name);
            psmt.setString(2, account_password);
            psmt.setString(3, username);
            psmt.setString(4, acc_role);
            psmt.setInt(5, ID);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Information Updated");
        alert.setHeaderText(null);
        alert.setContentText("Account Information Updated");
        alert.showAndWait();
        notifyListeners();
    }

    public String getAccount_Name(){
        return current_account.getAccount_Name();
    }

    public String getAccount_Role(){
        return current_account.getAccount_Role();
    }

    public String getAccount_Username(){
        return current_account.getUsername();
    }

    public String getAccount_Password(){
        return current_account.getPassword();
    }

    public int getAccount_ID(){
        return current_account.getUser_id();
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

}
