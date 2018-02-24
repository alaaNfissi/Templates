/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author dan
 */
public class MainBusesController implements Initializable {

    @FXML
    private MaterialDesignIconView iconDialog;
    @FXML
    private ImageView imgInfo;
    @FXML
    private ImageView imgHome;
    @FXML
    private ImageView imgExit;
    @FXML
    private HBox hoxImages;
    @FXML
    private Pane popUpPane;
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox boxPopUpMenusHolder;
    @FXML
    private VBox menuBook;
    @FXML
    private VBox menuDetail;
    @FXML
    private VBox menuList;
    @FXML
    private AnchorPane contentPane;
    private static JFXPopup staticJFXPopup;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setRipples();
    }

    @FXML
    private void openDialog(MouseEvent event) {
        JFXPopup fXPopup = new JFXPopup();
        fXPopup.setPopupContent(popUpPane);
        fXPopup.show(rootPane, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 10, 70);
        staticJFXPopup = fXPopup;

    }

    @FXML
    private void showInfo(MouseEvent event) {
        try {
            //dim overlay on new stage opening
            Region veil = new Region();
            veil.setPrefSize(1100, 650);
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
            Logger.getLogger(MainBusesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void goHome(MouseEvent event) throws IOException {
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.TRANSPARENT);
        Parent parent = FXMLLoader.load(getClass().getResource("/bookingfx/main.fxml"));
        Scene scene = new Scene(parent);
        newStage.setScene(scene);
        newStage.show();
        imgInfo.getScene().getWindow().hide();

    }

    @FXML
    private void exit(MouseEvent event) {
        Platform.exit();
    }

    private void setRipples() {

        JFXRippler fXRippler = new JFXRippler(imgInfo, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler1 = new JFXRippler(imgHome, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler2 = new JFXRippler(imgExit, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        fXRippler.setRipplerFill(Paint.valueOf("#D4761F"));
        fXRippler1.setRipplerFill(Paint.valueOf("#D4761F"));
        fXRippler2.setRipplerFill(Paint.valueOf("#D4761F"));
        hoxImages.getChildren().addAll(fXRippler, fXRippler1, fXRippler2);

        JFXRippler rippler = new JFXRippler(menuBook, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler rippler1 = new JFXRippler(menuDetail, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler rippler2 = new JFXRippler(menuList, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);

        boxPopUpMenusHolder.getChildren().addAll(rippler, rippler1, rippler2);
    }

    @FXML
    private void showBookingNode(MouseEvent event) {
        switchScene("/buses/Book.fxml");
    }

    @FXML
    private void showDetailsNode(MouseEvent event) {
        switchScene("/buses/Details.fxml");
    }

    @FXML
    private void showListsNode(MouseEvent event) {
        switchScene("/buses/BookLists.fxml");
    }

    private void switchScene(String fxml) {
        if (staticJFXPopup.isShowing()) {
            staticJFXPopup.hide();
        }

        try {
            contentPane.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxml));
            ObservableList<Node> elements = pane.getChildren();
            contentPane.getChildren().addAll(elements);
        } catch (IOException ex) {
            Logger.getLogger(MainBusesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
