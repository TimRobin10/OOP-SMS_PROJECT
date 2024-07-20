package sms.Admin_GUI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.ResourceBundle;

public class Dashboard_Controller {


    @FXML private ImageView infoBox;
    @FXML private Polygon colambugon;
    @FXML private Text COL;
    @FXML private Text Areatxt;
    @FXML private Text HHtxt;
    @FXML private Text SubsTxt;
    @FXML private ImageView loc;
    @FXML private Text COLHHnum1;
    @FXML private Text DANG;
    @FXML private Text DANGHHnum;
    @FXML private Polygon danggawan;
    @FXML private Text SAN;
    @FXML private Text SANHHnum;
    @FXML private Polygon sanmiguel;
    @FXML private Text BASE;
    @FXML private Text BASEHHnum;
    @FXML private Polygon basecamp;
    @FXML private Text CAMP;
    @FXML private Text CAMPHHnum;
    @FXML private Polygon camp1;
    @FXML private Text SOUTH;
    @FXML private Text SOUTHHHnum;
    @FXML private Polygon south;
    @FXML private Text NORTH;
    @FXML private Text NORTHHHnum;
    @FXML private Polygon north;
    @FXML private Text ANAHAWON;
    @FXML private Text ANAHAWONHHnum;
    @FXML private Polygon anahwon;
    @FXML private Text PANADTALAN;
    @FXML private Text PANADTALANHHnum;
    @FXML private Polygon panadtalan;
    @FXML private Text DOLOGON;
    @FXML private Text DOLOGONHHnum;
    @FXML private Polygon dologon;
    @FXML private AreaChart<?, ?> areaChart;
    @FXML private Text Totalsubscribers;
    @FXML private Text subsMonth;
    @FXML private Text TotalInstallation;
    @FXML private Text InstallMonth;
    @FXML private Text CutMonth;
    @FXML private Text TotalCutoff;
    @FXML private Text dueMonth;
    @FXML private Text TotalPastDue;


    private EventHandler<? super javafx.scene.input.MouseEvent> MouseEvent;
    private Color color;
    Subscribers_Database subscribers_database = Subscribers_Database.getInstance();

    @FXML
    public void initialize()
    {
        subscribers_database.getPopulation();
        subscribers_database.getSubscribers();
        subscribers_database.CountNumberOfInstallation();
        subscribers_database.CountPastDueData();
        subscribers_database.CountCutoffData();

        initialize_data();
        init_table();

        subscribers_database.addListener(this::refreshUI);

        infoBox.setImage(new Image(getClass().getResourceAsStream("/resources/Image_Resources/infoBox.png")));
        loc.setImage(new Image(getClass().getResourceAsStream("/resources/Image_Resources/location.png")));

        //Default
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);
        loc.setVisible(false);

        //Colambugon
        COL.setVisible(false);
        COLHHnum1.setVisible(false);

        //Danggawan
        DANG.setVisible(false);
        DANGHHnum.setVisible(false);

        //San Miguel
        SAN.setVisible(false);
        SANHHnum.setVisible(false);

        //Base Camp
        BASE.setVisible(false);
        BASEHHnum.setVisible(false);

        //Camp1
        CAMP.setVisible(false);
        CAMPHHnum.setVisible(false);

        //South
        SOUTH.setVisible(false);
        SOUTHHHnum.setVisible(false);

        //North
        NORTH.setVisible(false);
        NORTHHHnum.setVisible(false);

        //Anahawon
        ANAHAWON.setVisible(false);
        ANAHAWONHHnum.setVisible(false);

        //Panadtalan
        PANADTALAN.setVisible(false);
        PANADTALANHHnum.setVisible(false);

        //Dologon
        DOLOGON.setVisible(false);
        DOLOGONHHnum.setVisible(false);

        CAMPLocationHover();
        COLLocationHover();
        DANGLocationHover();
        SANLocationHover();
        BASELocationHover();
        SOUTHLocationHover();
        NORTHLocationHover();
        ANAHAWONLocationHover();
        PANADTALANLocationHover();
        DOLOGONLocationHover();
    }

    private void init_table(){
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Cut-Off");
        series3.getData().add(new XYChart.Data("Anahawon", 10));
        series3.getData().add(new XYChart.Data("Base Camp", 12));
        series3.getData().add(new XYChart.Data("Camp 1", 5));
        series3.getData().add(new XYChart.Data("Colambugon", 1));
        series3.getData().add(new XYChart.Data("Danggawan", 15));
        series3.getData().add(new XYChart.Data("Dologon", 20));
        series3.getData().add(new XYChart.Data("North Pob.", 30));
        series3.getData().add(new XYChart.Data("Panadtalan", 2));
        series3.getData().add(new XYChart.Data("San Miguel", 16));
        series3.getData().add(new XYChart.Data("South Pob.", 19));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Installation");
        series2.getData().add(new XYChart.Data("Anahawon", 25));
        series2.getData().add(new XYChart.Data("Base Camp", 30));
        series2.getData().add(new XYChart.Data("Camp 1", 15));
        series2.getData().add(new XYChart.Data("Colambugon", 8));
        series2.getData().add(new XYChart.Data("Danggawan", 10));
        series2.getData().add(new XYChart.Data("Dologon", 13));
        series2.getData().add(new XYChart.Data("North Pob.", 43));
        series2.getData().add(new XYChart.Data("Panadtalan", 9));
        series2.getData().add(new XYChart.Data("San Miguel", 16));
        series2.getData().add(new XYChart.Data("South Pob.", 22));

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Collection");
        series1.getData().add(new XYChart.Data("Anahawon", 89));
        series1.getData().add(new XYChart.Data("Base Camp", 75));
        series1.getData().add(new XYChart.Data("Camp 1", 62));
        series1.getData().add(new XYChart.Data("Colambugon", 55));
        series1.getData().add(new XYChart.Data("Danggawan", 56));
        series1.getData().add(new XYChart.Data("Dologon", 68));
        series1.getData().add(new XYChart.Data("North Pob.", 120));
        series1.getData().add(new XYChart.Data("Panadtalan", 30));
        series1.getData().add(new XYChart.Data("San Miguel", 98));
        series1.getData().add(new XYChart.Data("South Pob.", 92));

        areaChart.getData().addAll(series1, series2, series3);

    }

    private void DOLOGONLocationHover() {
        dologon.setOnMouseEntered(this::DOLOGONhandleMouseEntered);
        dologon.setOnMouseExited(this::DOLOGONhandleMouseExited);
    }

    private void DOLOGONhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        dologon.setFill(Color.rgb(248, 132, 86));
        DOLOGON.setVisible(true);
        DOLOGONHHnum.setText(String.valueOf(Subscribers_Database.population_Dologon));
        DOLOGONHHnum.setVisible(true);
    }

    private void DOLOGONhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        dologon.setFill(Color.rgb(211, 211, 211));
        DOLOGON.setVisible(false);
        DOLOGONHHnum.setVisible(false);
    }

    private void PANADTALANLocationHover() {
        panadtalan.setOnMouseEntered(this::PANADTALANhandleMouseEntered);
        panadtalan.setOnMouseExited(this::PANADTALANhandleMouseExited);
    }

    private void PANADTALANhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        panadtalan.setFill(Color.rgb(248, 132, 86));
        PANADTALAN.setVisible(true);
        PANADTALANHHnum.setText(String.valueOf(Subscribers_Database.population_Panadtalan));
        PANADTALANHHnum.setVisible(true);
    }

    private void PANADTALANhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        panadtalan.setFill(Color.rgb(211, 211, 211));
        PANADTALAN.setVisible(false);
        PANADTALANHHnum.setVisible(false);
    }

    private void ANAHAWONLocationHover() {
        anahwon.setOnMouseEntered(this::ANAHAWONhandleMouseEntered);
        anahwon.setOnMouseExited(this::ANAHAWONhandleMouseExited);
    }

    private void ANAHAWONhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        anahwon.setFill(Color.rgb(248, 132, 86));
        ANAHAWON.setVisible(true);
        ANAHAWONHHnum.setText(String.valueOf(Subscribers_Database.population_Anahawon));
        ANAHAWONHHnum.setVisible(true);
    }

    private void ANAHAWONhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        anahwon.setFill(Color.rgb(211, 211, 211));
        ANAHAWON.setVisible(false);
        ANAHAWONHHnum.setVisible(false);
    }

    private void NORTHLocationHover() {
        north.setOnMouseEntered(this::NORTHhandleMouseEntered);
        north.setOnMouseExited(this::NORTHhandleMouseExited);
    }

    private void NORTHhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        north.setFill(Color.rgb(248, 132, 86));
        NORTH.setVisible(true);
        NORTHHHnum.setText(String.valueOf(Subscribers_Database.population_NorthPoblacion));
        NORTHHHnum.setVisible(true);
    }

    private void NORTHhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        north.setFill(Color.rgb(211, 211, 211));
        NORTH.setVisible(false);
        NORTHHHnum.setVisible(false);
    }

    private void SOUTHLocationHover() {
        south.setOnMouseEntered(this::SOUTHhandleMouseEntered);
        south.setOnMouseExited(this::SOUTHhandleMouseExited);
    }

    private void SOUTHhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        south.setFill(Color.rgb(248, 132, 86));
        SOUTH.setVisible(true);
        SOUTHHHnum.setText(String.valueOf(Subscribers_Database.population_SouthPoblacion));
        SOUTHHHnum.setVisible(true);
    }

    private void SOUTHhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        south.setFill(Color.rgb(211, 211, 211));
        SOUTH.setVisible(false);
        SOUTHHHnum.setVisible(false);
    }

    private void CAMPLocationHover() {
        camp1.setOnMouseEntered(this::CAMPhandleMouseEntered);
        camp1.setOnMouseExited(this::CAMPhandleMouseExited);
    }

    private void CAMPhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        camp1.setFill(Color.rgb(248, 132, 86));
        CAMP.setVisible(true);
        CAMPHHnum.setText(String.valueOf(Subscribers_Database.population_Camp1));
        CAMPHHnum.setVisible(true);
    };

    private void CAMPhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        camp1.setFill(Color.rgb(211, 211, 211));
        CAMP.setVisible(false);
        CAMPHHnum.setVisible(false);
    }

    private void COLLocationHover() {
        colambugon.setOnMouseEntered(this::COLhandleMouseEntered);
        colambugon.setOnMouseExited(this::COLhandleMouseExited);
    }

    private void COLhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        colambugon.setFill(Color.rgb(248, 132, 86));
        COL.setVisible(true);
        COLHHnum1.setText(String.valueOf(Subscribers_Database.population_Colambugon));
        COLHHnum1.setVisible(true);
    };

    private void COLhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        colambugon.setFill(Color.rgb(211, 211, 211));
        COL.setVisible(false);
        COLHHnum1.setVisible(false);
    }

    private void DANGLocationHover() {
        danggawan.setOnMouseEntered(this::DANGhandleMouseEntered);
        danggawan.setOnMouseExited(this::DANGhandleMouseExited);
    }

    private void DANGhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        danggawan.setFill(Color.rgb(248, 132, 86));
        DANG.setVisible(true);
        DANGHHnum.setText(String.valueOf(Subscribers_Database.population_Danggawan));
        DANGHHnum.setVisible(true);
    }

    private void DANGhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        danggawan.setFill(Color.rgb(211, 211, 211));
        DANG.setVisible(false);
        DANGHHnum.setVisible(false);
    }

    private void SANLocationHover() {
        sanmiguel.setOnMouseEntered(this::SANhandleMouseEntered);
        sanmiguel.setOnMouseExited(this::SANhandleMouseExited);
    }

    private void SANhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        sanmiguel.setFill(Color.rgb(248, 132, 86));
        SAN.setVisible(true);
        SANHHnum.setText(String.valueOf(Subscribers_Database.population_SanMiguel));
        SANHHnum.setVisible(true);

    };

    private void SANhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        sanmiguel.setFill(Color.rgb(211, 211, 211));
        SAN.setVisible(false);
        SANHHnum.setVisible(false);
    }

    private void BASELocationHover() {
        basecamp.setOnMouseEntered(this::BASEhandleMouseEntered);
        basecamp.setOnMouseExited(this::BASEhandleMouseExited);
    }

    private void BASEhandleMouseEntered(MouseEvent e) {
        Areatxt.setVisible(true);
        HHtxt.setVisible(true);
        SubsTxt.setVisible(true);
        infoBox.setVisible(true);
        loc.setVisible(true);

        basecamp.setFill(Color.rgb(248, 132, 86));
        BASE.setVisible(true);
        BASEHHnum.setText(String.valueOf(Subscribers_Database.population_BaseCamp));
        BASEHHnum.setVisible(true);
    };

    private void BASEhandleMouseExited(MouseEvent e) {
        Areatxt.setVisible(false);
        HHtxt.setVisible(false);
        SubsTxt.setVisible(false);
        infoBox.setVisible(false);
        loc.setVisible(false);

        basecamp.setFill(Color.rgb(211, 211, 211));
        BASE.setVisible(false);
        BASEHHnum.setVisible(false);
    }

    void initialize_data(){
        Totalsubscribers.setText(String.valueOf(Subscribers_Database.NumberOfSubscribers));
        subsMonth.setText(current_date());

        TotalInstallation.setText(String.valueOf(Subscribers_Database.NumberOfInstallation));
        InstallMonth.setText(current_month());

        TotalCutoff.setText(String.valueOf(Subscribers_Database.TotalCutoff));
        CutMonth.setText(current_month());

        TotalPastDue.setText(String.valueOf(Subscribers_Database.TotalPastDue));
        dueMonth.setText(current_month());
    }

    public static String current_month() {
        LocalDate now = LocalDate.now();
        Month currentMonth = now.getMonth();
        String monthName = currentMonth.name().toLowerCase();
        return monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
    }

    public static String current_date() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return now.format(formatter);
    }

    private void refreshUI() {
        Platform.runLater(() -> {

        });
    }

}
