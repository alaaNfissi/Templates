/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookingfx;

import com.jfoenix.controls.JFXRippler;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author danml
 */
public class mainController implements Initializable {

    @FXML
    private MaterialDesignIconView iconClose;
    @FXML
    private Group groupTrains;
    @FXML
    private Group groupPlanes;
    @FXML
    private Group groupBuses;
    @FXML
    private AnchorPane parentContainer;
    @FXML
    private Group groupTrains1;
    @FXML
    private HBox menusHolder;
    @FXML
    private AnchorPane BUS;
    @FXML
    private AnchorPane PLANE;
    @FXML
    private AnchorPane TRAIN;
    @FXML
    private AnchorPane ANALYTIC;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpRipples();
    }

    private void setUpRipples() {
        JFXRippler fXRippler = new JFXRippler(BUS, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler1 = new JFXRippler(TRAIN, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler2 = new JFXRippler(PLANE, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler3 = new JFXRippler(ANALYTIC, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        menusHolder.getChildren().addAll(fXRippler2, fXRippler1, fXRippler, fXRippler3);
    }

    @FXML
    private void hideStage(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    private void openPlanes(MouseEvent event) {
        openStage("/planes/MainPlanes.fxml");
    }

    @FXML
    private void openTrains(MouseEvent event) {
        openStage("/trains/MainTrains.fxml");
    }

    @FXML
    private void openBuses(MouseEvent event) {
        openStage("/buses/MainBuses.fxml");
    }

    @FXML
    private void openAnalytics(MouseEvent event) {
        openStage("Analytics.fxml");
    }

    private void openStage(String fxml) {
        try {
            Stage currentStage = (Stage) PLANE.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            currentStage.hide();

        } catch (IOException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
