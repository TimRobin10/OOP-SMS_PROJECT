package sms.Admin_GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

public class MenuController {
    @FXML private ToggleButton AccountsButton;
    @FXML private ToggleButton Billing;
    @FXML private ToggleButton DashboardButton;
    @FXML private ToggleButton DatabaseButton;
    @FXML private AnchorPane MenuPanel;
    @FXML private ToggleButton TransactionButton;
    @FXML private FontAwesomeIcon User_Icon;
    @FXML private Button User_icon;
    @FXML private BorderPane borderPane;

    ToggleGroup Menu = new ToggleGroup();

    @FXML
    private void initialize(){
        init_togglegroup();
        setupCenterPane();

        Menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> setupCenterPane());
    }

    void init_togglegroup(){
        AccountsButton.setToggleGroup(Menu);
        Billing.setToggleGroup(Menu);
        DashboardButton.setToggleGroup(Menu);
        DatabaseButton.setToggleGroup(Menu);
        TransactionButton.setToggleGroup(Menu);
        DatabaseButton.setSelected(true);
        ToggleSingleSelection();
    }

    void ToggleSingleSelection() {
        Menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            }
        });
    }

    void setupCenterPane() {
        try {
            ToggleButton selectedButton = (ToggleButton) Menu.getSelectedToggle();
            String fxmlFile = "";
            if (selectedButton == AccountsButton) {
                fxmlFile = "Accounts.fxml";
            } else if (selectedButton == Billing) {
                fxmlFile = "Payments.fxml";
            } else if (selectedButton == DatabaseButton) {
                fxmlFile = "DATABASE_MAIN.fxml";
            } else if (selectedButton == TransactionButton) {
                fxmlFile = "Transactions.fxml";
            } else if(selectedButton == DatabaseButton){
                fxmlFile = "Dashboard_Scene.fxml";
            }

            if (!fxmlFile.isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                AnchorPane AP = loader.load();
                borderPane.setCenter(AP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
