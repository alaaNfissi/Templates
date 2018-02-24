package transportfx;

import classes.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.Movements;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class BusesController implements Initializable {

    @FXML
    private ImageView imgBack;
    @FXML
    private ImageView imgBus;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXTextField txtBusNo;
    @FXML
    private JFXTextField txtcarPlateNo;
    @FXML
    private JFXTextField txtModel;
    @FXML
    private JFXTextField txtCapacity;
    @FXML
    private JFXDatePicker dtDatePurchased;
    @FXML
    private JFXTextField txtInsuranceStatus;
    @FXML
    private JFXDatePicker dtDateInsured;
    @FXML
    private JFXDatePicker dtExpiryDate;
    @FXML
    private JFXComboBox<String> comboDriverName;
    @FXML
    private JFXComboBox<String> comboDeparture;
    @FXML
    private JFXComboBox<String> comboDestination;
    private DbHandler handler;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;
    private final String pattern = "yyyy-MM-dd";
    @FXML
    private JFXButton btnReset;
    @FXML
    private TableView tableBusesList;
    @FXML
    private JFXButton bookTransit;
    @FXML
    private TableView<Movements> tableMovements;
    @FXML
    private TableColumn<Movements, Integer> colId;
    @FXML
    private TableColumn<Movements, String> colDriver;
    @FXML
    private TableColumn<Movements, String> colOrigin;
    @FXML
    private TableColumn<Movements, String> colDestination;
    @FXML
    private TableColumn<Movements, String> colTime;
    private ObservableList<Movements> data;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Instantiate database class
        handler = new DbHandler();
        // Date converter for javafx datepicker to conform with sql date format
        sqlDateFormatter();
        //auto populate bs list tableview
        buildBusesTable();
        //Anmate bus movement
        animateBus();
        //POpulate booking comboboxes
        populateCombooxes();
        buildMovementsTable();

    }

    @FXML
    private void goBack(MouseEvent event) {
        try {
            imgBack.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Menus.fxml"));
            Scene scene = new Scene(root);
            Stage menuStage = new Stage();
            menuStage.setScene(scene);
            menuStage.show();
        } catch (IOException ex) {
            Logger.getLogger(DriversController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void animateBus() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        KeyValue kv = new KeyValue(imgBus.xProperty(), 700);
        KeyFrame kf = new KeyFrame(Duration.seconds(10), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    private void saveDriver(ActionEvent event) {
        try {
            // Validation has not been observed,this is justb a prototype.So fuck off if you think i know nothing about validation.
            String insertQuery = "INSERT INTO buses(plateno,model,capacity,purchase_date,insurance_status,date_insured,expiry_date) "
                    + "VALUES (?,?,?,?,?,?,?)";
            conn = handler.getConnection();
            ps = conn.prepareStatement(insertQuery);
            ps.setString(1, txtcarPlateNo.getText().trim().toUpperCase());
            ps.setString(2, txtModel.getText().toUpperCase());
            ps.setString(3, txtCapacity.getText());
            ps.setDate(4, java.sql.Date.valueOf(dtDatePurchased.getValue()));
            ps.setString(5, txtInsuranceStatus.getText());
            ps.setDate(6, java.sql.Date.valueOf(dtDateInsured.getValue()));
            ps.setDate(7, java.sql.Date.valueOf(dtExpiryDate.getValue()));

            int success = ps.executeUpdate();
            if (success == 1) {
                JFXSnackbar fXSnackbar = new JFXSnackbar(rootPane);
                fXSnackbar.show("New bus saved into the records", 5000);
                clearFields();
                buildBusesTable();
            } else {
                JFXSnackbar fXSnackbar = new JFXSnackbar(rootPane);
                fXSnackbar.show("Check validity of data entered and save.I am too lazy to impose validation", 9000);
            }
        } catch (SQLException ex) {
            JFXSnackbar nackbar = new JFXSnackbar(rootPane);
            nackbar.show("ERROR \n" + ex.getMessage() + "\n Contact developer.", 9000);
        }

    }

    @FXML
    private void editDriver(ActionEvent event) {
        JFXSnackbar fXSnackbar = new JFXSnackbar(rootPane);
        fXSnackbar.show("This functionality will not be implemented here. I am tired", 5000);
    }

    void sqlDateFormatter() {
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);

                } else {
                    return null;
                }
            }

        };
        dtDateInsured.setConverter(converter);
        dtDatePurchased.setConverter(converter);
        dtExpiryDate.setConverter(converter);
    }

    private void clearFields() {
        txtBusNo.setText("");
        txtCapacity.setText("");
        txtInsuranceStatus.setText("");
        txtModel.setText("");
        txtcarPlateNo.setText("");
        dtDateInsured.setValue(null);
        dtDatePurchased.setValue(null);
        dtExpiryDate.setValue(null);

    }

    @FXML
    private void resetFields(ActionEvent event) {
        clearFields();
    }

    private void buildBusesTable() {

        try {
            conn = handler.getConnection();
            String query = "SELECT * FROM buses";

            ResultSet rst = conn.createStatement().executeQuery(query);
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            tableBusesList.getColumns().clear();
            int cols = rst.getMetaData().getColumnCount();

            for (int i = 0; i < cols; i++) {
                final int j = i;
                TableColumn col = new TableColumn(rst.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());

                    }
                });

                col.setPrefWidth(130);
                tableBusesList.getColumns().addAll(col);
            }
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int k = 1; k <= rst.getMetaData().getColumnCount(); k++) {
                    row.add(rst.getString(k));
                }
                data.add(row);
            }
            tableBusesList.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(BusesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void bookTransit(ActionEvent event) throws SQLException {
        conn = handler.getConnection();
        String query = "INSERT  INTO movements(driver,origin,destination,departure_time) VALUES (?,?,?,now())";
        ps = conn.prepareStatement(query);
        ps.setString(1, comboDriverName.getSelectionModel().getSelectedItem());
        ps.setString(2, comboDeparture.getSelectionModel().getSelectedItem());
        ps.setString(3, comboDestination.getSelectionModel().getSelectedItem());

        int success = ps.executeUpdate();
        if (success == 1) {
            buildMovementsTable();
        }

    }

    private void buildMovementsTable() {
        try {
            String query = "SELECT * FROM movements";
            conn = handler.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                int id = set.getInt(1);
                String driver = set.getString(2);
                String origin = set.getString(3);
                String destination = set.getString(4);
                String time = set.getString(5);
                data.add(new Movements(id, driver, origin, destination, time));

            }
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDriver.setCellValueFactory(new PropertyValueFactory<>("names"));
            colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
            colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

            tableMovements.setItems(null);
            tableMovements.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(BusesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateCombooxes() {
        try {
            comboDeparture.getItems().addAll("Nairobi", "Mombasa", "Kitale", "Machakos", "Voi", "Kisumu", "Eldoret", "Kampala", "Kigali", "Nyeri", "Embu", "Kiambu", "Mtwapa", "Thika");
            comboDestination.getItems().addAll("Nairobi", "Mombasa", "Kitale", "Machakos", "Voi", "Kisumu", "Eldoret", "Kampala", "Kigali", "Nyeri", "Embu", "Kiambu", "Mtwapa", "Thika");
            conn = handler.getConnection();
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT drivers.mname,drivers.lname FROM drivers");
            while (resultSet.next()) {
                String names = resultSet.getString(1) + " " + resultSet.getString(2);
                comboDriverName.getItems().addAll(names);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
