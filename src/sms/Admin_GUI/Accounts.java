package sms.Admin_GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Accounts {
    private int Account_ID;
    private String Username;
    private String Account_Password;
    private String Account_Name;
    private String Account_Role;

    public Accounts(int user_id, String username, String account_password, String account_name, String account_role){
        this.Account_ID = user_id;
        this.Username = username;
        this.Account_Password = account_password;
        this.Account_Name = account_name;
        this.Account_Role = account_role;
    }

    public int getAccount_ID() {
        return Account_ID;
    }

    public void setAccount_ID(int account_ID) {
        Account_ID = account_ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAccount_Password() {
        return Account_Password;
    }

    public void setAccount_Password(String account_Password) {
        Account_Password = account_Password;
    }

    public String getAccount_Name() {
        return Account_Name;
    }

    public void setAccount_Name(String account_Name) {
        Account_Name = account_Name;
    }

    public String getAccount_Role() {
        return Account_Role;
    }

    public void setAccount_Role(String account_Role) {
        Account_Role = account_Role;
    }
}
