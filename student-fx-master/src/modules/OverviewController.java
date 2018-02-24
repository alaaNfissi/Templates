/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import classes.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class OverviewController implements Initializable {

    @FXML
    private HBox groupHolder;
    @FXML
    private Group groupRegistered;
    @FXML
    private Group groupTarget;
    @FXML
    private Group groupGents;
    @FXML
    private Group groupLadies;
    @FXML
    private JFXListView<String> departmentList;
    private ObservableList<String> departments;
    @FXML
    private Button btnAddDepartment;

    protected Connection connection;
    private DbHandler handler;
    Statement stmt = null;
    ResultSet rs = null;
    JFXTextField txtDepartment;
    @FXML
    private StackPane deptStackPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRipples();
        handler = new DbHandler();

        setUpDepartments();
    }

    private void setRipples() {
        JFXRippler rippler1 = new JFXRippler(groupRegistered);
        JFXRippler rippler2 = new JFXRippler(groupGents);
        JFXRippler rippler3 = new JFXRippler(groupLadies);
        JFXRippler rippler4 = new JFXRippler(groupTarget);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler2.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler3.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler4.setMaskType(JFXRippler.RipplerMask.RECT);

        rippler1.setRipplerFill(Paint.valueOf("#1564C0"));
        rippler2.setRipplerFill(Paint.valueOf("#00AACF"));
        rippler3.setRipplerFill(Paint.valueOf("#00B3A0"));
        rippler4.setRipplerFill(Paint.valueOf("#F87951"));

        groupHolder.getChildren().addAll(rippler1, rippler2, rippler3, rippler4);
    }

    private void setUpDepartments() {
        departmentList.getItems().clear();
        try {
            connection = handler.getConnection();

            String query = "SELECT department.`name` FROM department";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                rs = pst.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("name");
                    departments = FXCollections.observableArrayList(name);
                    departmentList.getItems().addAll(departments);
                }
            }
            rs.close();

        } catch (Exception ex) {
            Logger.getLogger(OverviewController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }

    }

    @FXML
    private void addDepartment(ActionEvent event) {
        //Body Input text
        txtDepartment = new JFXTextField();
        txtDepartment.setPromptText("Enter new department name");
        txtDepartment.setLabelFloat(false);
        txtDepartment.setPrefSize(150, 50);
        txtDepartment.setPadding(new Insets(10, 5, 10, 5));
        txtDepartment.setStyle("-fx-font-size:13px; -fx-font-weight:bold;-fx-text-fill:#00B3A0");
        // Heading text
        Text t = new Text("Add New Department");
        t.setStyle("-fx-font-size:14px;");

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(t);
        dialogLayout.setBody(txtDepartment);

        JFXDialog dialog = new JFXDialog(deptStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        // close button
        JFXButton closeButton = new JFXButton("Dismiss");
        closeButton.setStyle("-fx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-font-size: 14px;-fx-text-fill: WHITE;");
        //Add button
        JFXButton addBtn = new JFXButton("Add");
        addBtn.setStyle("-fx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-font-size: 14px;-fx-text-fill: WHITE;"
                + "");
        closeButton.setOnAction((ActionEvent event1) -> {
            dialog.close();
        });       
        addBtn.setOnAction((ActionEvent event1) -> {
            System.out.println(txtDepartment.getText());
            saveDepartment();
            setUpDepartments();
            dialog.close();
        });
        
        HBox box=new HBox();
        box.setSpacing(20);
        box.setPrefSize(200, 50);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(addBtn,closeButton);

        dialogLayout.setActions(box);
        
        dialog.show();
    }

    private void saveDepartment() {
        String name = txtDepartment.getText().trim();
        try {
            if (name.isEmpty() || name.equals("")) {
                return;
            }
            connection = handler.getConnection();
            String query = "INSERT INTO department(name) VALUES (?) ";
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println(query);
            System.out.println(name);
                       
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + ex.getCause());
        }

    }

}
