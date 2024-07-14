package sms.Admin_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sms.LoginPage.LoginDatabase;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Receipt_Controller {
    @FXML private Label AmountInNumbers_Label;
    @FXML private Label AmountInWords_Label;
    @FXML private Label BusinessStyle_Label;
    @FXML private Label RecieverName_Label;
    @FXML private Label Service_Label;
    @FXML private Label TIN_label;
    @FXML private Label TITLE_LABEL;
    @FXML private Label address_label;
    @FXML private Label recieve_label;
    @FXML private Label transactionID_label;
    @FXML private Label date_label;

    LoginDatabase loginDatabase = LoginDatabase.getInstance();

    public void initializeData(int transaction_id, String client_name, String client_address, double amount) {
        transactionID_label.setText("No.:   " + String.format("%06d", transaction_id));
        date_label.setText("Date:   " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        recieve_label.setText(String.valueOf(client_name));
        address_label.setText(String.valueOf(client_address));
        AmountInNumbers_Label.setText(formatDouble(amount));
        AmountInWords_Label.setText(capitalize(NumberToWords.convert(amount)) + " Only");
        RecieverName_Label.setText(loginDatabase.getAccount_Name());
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder(str.length());
        String[] words = str.split("\\s");

        for (int i = 0, l = words.length; i < l; ++i) {
            if (!words[i].isEmpty()) { // Check if word is not empty
                if (i > 0) {
                    result.append(" "); // Append space if not the first word
                }
                result.append(Character.toUpperCase(words[i].charAt(0)))
                        .append(words[i].substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    public static String formatDouble(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(number);
    }

}
