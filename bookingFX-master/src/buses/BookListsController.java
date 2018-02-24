/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import trains.*;
import planes.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Trip;

/**
 * FXML Controller class
 *
 * @author dan
 */
public class BookListsController implements Initializable {

    @FXML
    private TableColumn<Trip, String> colID;
    @FXML
    private TableColumn<Trip, String> colName1;
    @FXML
    private TableColumn<Trip, String> colname2;
    @FXML
    private TableColumn<Trip, String> colOrigin;
    @FXML
    private TableColumn<Trip, String> colDestination;
    @FXML
    private TableColumn<Trip, String> colCost;
    @FXML
    private TableColumn<Trip, String> colRefundable;
    @FXML
    private TableView<Trip> tableList;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
    }

    //set dummy data
    private void setTableData() {
        final ObservableList<Trip> data = FXCollections.observableArrayList(
                new Trip("TR1", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR1", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR2", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR3", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230400", "NO"),
                new Trip("TR4", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR5", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR6", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23700", "NO"),
                new Trip("TR7", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "YES"),
                new Trip("TR8", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR9", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230600", "NO"),
                new Trip("TR10", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "YES"),
                new Trip("TR11", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 234000", "NO"),
                new Trip("TR12", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR13", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230400", "YES"),
                new Trip("TR14", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230200", "NO"),
                new Trip("TR15", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230000", "YES"),
                new Trip("TR16", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "NO"),
                new Trip("TR17", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 23000", "YES"),
                new Trip("TR18", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230600", "NO"),
                new Trip("TR19", "Dan Mlayah", "Aggie Dan", "Kenya", "Uganda", "Kshs 230400", "NO")
        );
        colID.setCellValueFactory(new PropertyValueFactory<>("tripID"));
        colName1.setCellValueFactory(new PropertyValueFactory<>("name1"));
        colname2.setCellValueFactory(new PropertyValueFactory<>("name2"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("tripCost"));
        colRefundable.setCellValueFactory(new PropertyValueFactory<>("refundable"));
        tableList.setItems(data);
        
        
        

    }

}
