/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookingfx;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author dan
 */
public class AnalyticsController implements Initializable {

    @FXML
    private PieChart pieBookings;
    @FXML
    private HBox hoxImages;
    @FXML
    private ImageView imgInfo;
    @FXML
    private ImageView imgHome;
    @FXML
    private MaterialDesignIconView iconClose;
    @FXML
    private StackPane rootPane;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populatePieChart();
    } 
    
    private void populatePieChart(){
        ObservableList<PieChart.Data> piedata=FXCollections.observableArrayList(
        new PieChart.Data("Trains", 30),new PieChart.Data("Planes", 15),new PieChart.Data("Trains", 65)
        );
        pieBookings.setData(piedata);
    
    }

    @FXML
    private void showInfo(MouseEvent event) {
        try {
            //dim overlay on new stage opening
            Region veil = new Region();
            veil.setPrefSize(1000, 600);
            veil.setStyle("-fx-background-color:rgba(0,0,0,0.4)");
            Stage newStage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("/bookingfx/About.fxml"));

            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.getScene().getRoot().setEffect(new DropShadow());
            newStage.showingProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    rootPane.getChildren().add(veil);
                } else if (rootPane.getChildren().contains(veil)) {
                    rootPane.getChildren().removeAll(veil);
                }

            });
            newStage.centerOnScreen();
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AnalyticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goHome(MouseEvent event) throws IOException {
         Stage newStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/bookingfx/main.fxml"));
        Scene scene = new Scene(parent);
        newStage.setScene(scene);
        newStage.show();
        imgInfo.getScene().getWindow().hide();
    }

    @FXML
    private void closeStage(MouseEvent event) {
        Platform.exit();
    }
    
}
