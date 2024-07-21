package sms.LoginPage;

public class AccountObjects {
    private int user_id = 9999;
    private String username = "SampleUsername";
    private String password = "SamplePassword";
    private String Account_Name = "SampleAccountName";
    private String Account_Role = "SampleAccountRole";

    public AccountObjects(){}

    public AccountObjects(int user_id, String username, String password, String Account_Name, String Account_Role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.Account_Name = Account_Name;
        this.Account_Role = Account_Role;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccount_Name() {
        return Account_Name;
    }

    public String getAccount_Role() {
        return Account_Role;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount_Name(String account_Name) {
        Account_Name = account_Name;
    }

    public void setAccount_Role(String account_Role) {
        Account_Role = account_Role;
    }
}
