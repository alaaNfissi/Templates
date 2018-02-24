/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transportfx;

import classes.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class TicketsController implements Initializable {

    @FXML
    private ImageView imgBack;
    @FXML
    private JFXComboBox<String> comboFrom;
    @FXML
    private JFXComboBox<String> comboTo;
    @FXML
    private JFXTextField txtFare;
    @FXML
    private JFXButton btnPay;
    private Connection conn;
    private DbHandler handler;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView tableTickets;
    @FXML
    private Label lblTicketsSold;
    @FXML
    private Label lblTotalSales;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // populate combos
        populateCombos();
        //Instantian Dbhandler class
        handler = new DbHandler();
        //Validate Amount textfield with validator
        StringConverter<Integer> formatter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == null) {
                    return "0";
                }
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        };
        txtFare.setTextFormatter(new TextFormatter<>(formatter));
        //Populate tables
        buildDataTable();
        updateStatistics();

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

    @FXML
    private void bookTicket(ActionEvent event) throws SQLException {
        //Get variables
        String from = comboFrom.getSelectionModel().getSelectedItem();
        String to = comboTo.getSelectionModel().getSelectedItem();
        Double amount = Double.parseDouble(txtFare.getText().trim());
        // Establish connection
        conn = handler.getConnection();
        String sql = "INSERT INTO tickets(origin,destination,amount,`time`) VALUES (?,?,?,now())";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, from);
        pst.setString(2, to);
        pst.setDouble(3, amount);
        int success = pst.executeUpdate();
        if (success == 1) {
            JFXSnackbar fXSnackbar = new JFXSnackbar(rootPane);
            fXSnackbar.show("Ticket book successful", 4000);
            buildDataTable();
            updateStatistics();
            
        }

    }

    private void populateCombos() {

        comboFrom.getItems().addAll("Nairobi", "Mombasa", "Kisumu", "Kitale", "Machakos",
                "Thika", "Kiambu", "Voi", "Embu", "Marsabit");
        comboTo.getItems().addAll("Nairobi", "Mombasa", "Kisumu", "Kitale", "Machakos",
                "Thika", "Kiambu", "Voi", "Embu", "Marsabit");

    }

    private void buildDataTable() {
        try {
            conn = handler.getConnection();
            String query = "SELECT * FROM tickets";

            ResultSet rst = conn.createStatement().executeQuery(query);
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            tableTickets.getColumns().clear();
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
                tableTickets.getColumns().addAll(col);
            }
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int k = 1; k <= rst.getMetaData().getColumnCount(); k++) {
                    row.add(rst.getString(k));
                }
                data.add(row);
            }
            tableTickets.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(TicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateStatistics() {
        try {
            String query = "SELECT count(id),sum(amount) FROM  tickets";
            conn = handler.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                lblTicketsSold.setText(rs.getString(1));
                lblTotalSales.setText("Kes "+rs.getInt(2));

            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
