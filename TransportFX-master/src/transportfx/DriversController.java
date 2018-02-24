/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transportfx;

import classes.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import models.driver;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class DriversController implements Initializable {

    @FXML
    private ImageView imgBack;
    @FXML
    private AnchorPane topAnchor;
    @FXML
    private ImageView imgProfile;
    @FXML
    private JFXButton btnChoose;
    @FXML
    private StackPane rootPane;
    @FXML
    private ToggleGroup gender;
    @FXML
    private ToggleGroup family;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXTextField txtSerialNo;
    @FXML
    private JFXButton btnGenerate;
    @FXML
    private JFXComboBox<String> comboLicence;
    @FXML
    private JFXComboBox<Number> comboYear;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXSnackbar snackEdit;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtMiddleName;
    @FXML
    private JFXTextField txtSurName;
    @FXML
    private JFXDatePicker dtDOB;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private RadioButton rdMale;
    @FXML
    private RadioButton rdFemale;
    @FXML
    private RadioButton rdSingle;
    @FXML
    private RadioButton rdMarried;
    @FXML
    private RadioButton rdOthers;
    private DbHandler handler;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pst;
    private ResultSet rs;
    @FXML
    private JFXComboBox<String> comboPLate;
    @FXML
    private TableView<driver> tableDriversInfo;
    @FXML
    private TableColumn<driver, String> colNames;
    @FXML
    private TableColumn<driver, String> colDob;
    @FXML
    private TableColumn<driver, String> colGender;
    @FXML
    private TableColumn<driver, String> colPhone;
    @FXML
    private TableColumn<driver, String> colStatus;
    @FXML
    private TableColumn<driver, String> colLicense;
    @FXML
    private TableColumn<driver, String> colIssued;

    private ObservableList<driver> data;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Instantiate db class
        handler = new DbHandler();

        JFXRippler backRippler = new JFXRippler(imgBack, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        topAnchor.getChildren().add(imgBack);
        populateCombos();
        // Set default selected radio buttons
        rdMale.setSelected(true);
        rdSingle.setSelected(true);
        //populate table
        buildDriversTable();

//        Rectangle clip=new Rectangle(imgProfile.getFitWidth(), imgProfile.getFitHeight());
//        clip.setArcHeight(120);
//        clip.setArcWidth(120);
//        imgProfile.setClip(clip);
//        SnapshotParameters parameters=new SnapshotParameters();
//        parameters.setFill(Color.TRANSPARENT);
//        WritableImage img=imgProfile.snapshot(parameters, null);
//        imgProfile.setClip(null);
//        imgProfile.setImage(img);
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
    private void choosePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(filterPNG, filterJPG);
        //show open dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                Rectangle clip = new Rectangle(imgProfile.getFitWidth(), imgProfile.getFitHeight());
                clip.setArcHeight(180);
                clip.setArcWidth(180);
                imgProfile.setImage(image);
                imgProfile.setClip(clip);
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                WritableImage img = imgProfile.snapshot(parameters, null);
                imgProfile.setClip(null);
                imgProfile.setImage(img);

            } catch (IOException ex) {
                Logger.getLogger(DriversController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void saveDriver(ActionEvent event) throws SQLException {

        try {
            String insert = "INSERT INTO drivers(sname,mname,lname,dob,gender,"
                    + "status,license,issued,photo,car_plate) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            conn = handler.getConnection();
            pst = conn.prepareStatement(insert);
            //Create blob object from image
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(imgProfile.getImage(), null), "png", byteOutput);
            Blob blob = conn.createBlob();
            blob.setBytes(1, byteOutput.toByteArray());
            // Set parameters
            pst.setString(1, txtSurName.getText().toUpperCase());
            pst.setString(2, txtFirstName.getText().toUpperCase());
            pst.setString(3, txtFirstName.getText().toUpperCase());
            pst.setDate(4, java.sql.Date.valueOf(dtDOB.getValue()));
            pst.setString(5, getGender());
            pst.setString(6, getMarital());
            pst.setString(7, comboLicence.getSelectionModel().getSelectedItem());
            pst.setString(8, comboYear.getSelectionModel().getSelectedItem().toString());
            pst.setBlob(9, blob);
            pst.setString(10, comboPLate.getSelectionModel().getSelectedItem());
            //save
            System.out.println("Size is :" +byteOutput.size());
           

            //int success = pst.executeUpdate();
            System.out.println(blob);
            //System.out.println(success);

        } catch (IOException ex) {
            Logger.getLogger(DriversController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editDriver(ActionEvent event) {
        JFXSnackbar fXSnackbar = new JFXSnackbar(rootPane);
        fXSnackbar.show("Edit functionality will be implemented later on", 5000);
    }

    @FXML
    private void generateSerialNo(ActionEvent event) {
        int random = 10000000 + (int) (Math.random() * 10000000);
        txtSerialNo.setText(String.valueOf(random));
    }

    @FXML
    private void clearFields(ActionEvent event) {

    }

    private void populateCombos() {
        try {
            for (int i = 2017; i > 1970; i--) {
                comboYear.getItems().addAll(i);
            }
            comboLicence.getItems().addAll("A", "B", "C");
            conn = handler.getConnection();
            pst = conn.prepareStatement("SELECT buses.plateno FROM buses");
            rs = pst.executeQuery();
            while (rs.next()) {
                comboPLate.getItems().addAll(rs.getString(1).toUpperCase());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private String getGender() {
        String ge = "";
        if (rdMale.isSelected()) {
            ge = "MALE";
        } else if (rdFemale.isSelected()) {
            ge = "MALE";
        }
        return ge;
    }

    private String getMarital() {
        String marital = "";
        if (rdMarried.isSelected()) {
            marital = "MARRIED";
        } else if (rdSingle.isSelected()) {
            marital = "SINGLE";
        } else if (rdOthers.isSelected()) {
            marital = "OTHERS";
        }
        return marital;
    }

    private void buildDriversTable() {
        try {
            String query = "SELECT * FROM drivers";
            conn = handler.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                int id = set.getInt(1);
                String names = set.getString(2) + " " + set.getString(3) + " " + set.getString(4);
                String dob = set.getString(5);
                String geda = set.getString(6);
                String status = set.getString(7);
                String license = set.getString(8);
                String issueD = set.getString(9);
                String car = set.getString(11);
                data.add(new driver(id, names, dob, geda, dob, status, license, issueD));
            }
            colNames.setCellValueFactory(new PropertyValueFactory<>("names"));
            colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colIssued.setCellValueFactory(new PropertyValueFactory<>("issued"));
            colLicense.setCellValueFactory(new PropertyValueFactory<>("license"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            tableDriversInfo.setItems(null);
            tableDriversInfo.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(DriversController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
