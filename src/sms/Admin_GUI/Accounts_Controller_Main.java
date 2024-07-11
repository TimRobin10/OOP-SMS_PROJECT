package sms.Admin_GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import sms.LoginPage.LoginDatabase;

import java.io.IOException;
import java.util.Objects;

public class Accounts_Controller_Main {

    @FXML private AnchorPane AccountsForm;
    @FXML private VBox Accounts_Layout;
    @FXML private Label HeadLabel;
    @FXML private Button RemoveAccountButton;
    @FXML private ScrollPane ScrollPane;
    @FXML private FontAwesomeIcon searchIcon;
    @FXML private TextField searchTextField;

    LoginDatabase LDB = LoginDatabase.getInstance();

    @FXML
    private void initialize(){
        ObservableList<Accounts> acc = LDB.fetchAccountData();
        try {
            for(Accounts a:acc){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Accounts_Rows.fxml"));
                AnchorPane Row_Node = loader.load();

                Accounts_Rows_Controller ARC = loader.getController();
                ARC.set_Account_Data(a);

                Accounts_Layout.getChildren().add(Row_Node);
            }


            /*FXMLLoader childLoader = new FXMLLoader(getClass().getResource("Accounts_Rows.fxml"));
            AnchorPane childNode = childLoader.load();
            Accounts_Layout.getChildren().add(childNode);*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
