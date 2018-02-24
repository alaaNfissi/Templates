package transportfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSnackbar;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author danml
 */
public class MenusController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private StackPane fabsContainer;
    @FXML
    private AnchorPane paneUsers;
    @FXML
    private AnchorPane paneTickets;
    @FXML
    private AnchorPane paneBuses;
    @FXML
    private AnchorPane paneDrivers;
    @FXML
    private HBox boxMenus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpFabs();
        setUpRipples();

    }

    private void showSnack(ActionEvent event) {
        JFXSnackbar fXSnackbar = new JFXSnackbar(rootPane);
        fXSnackbar.show("Hey Snack", Long.MAX_VALUE);
    }

    @FXML
    private void switchToUsers(MouseEvent event) {
    }

    @FXML
    private void switchToTickets(MouseEvent event) {
        try {
            paneTickets.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Tickets.fxml"));
            Scene scene = new Scene(root);
            Stage driverStage = new Stage();
            driverStage.setScene(scene);
            driverStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToBuses(MouseEvent event) {
        try {
            paneDrivers.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Buses.fxml"));
            Scene scene = new Scene(root);
            Stage driverStage = new Stage();
            driverStage.setScene(scene);
            driverStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToDrivers(MouseEvent event) {
        try {
            paneDrivers.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Drivers.fxml"));
            Scene scene = new Scene(root);
            Stage driverStage = new Stage();
            driverStage.setScene(scene);
            driverStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setUpFabs() {
        //Setting up icons for button
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE_ALT_NOTCH);
        icon.setStyle("-fx-fill:#ffffff;-fx-size:13px;");

        FontAwesomeIconView closeicon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        closeicon.setStyle("-fx-fill:#ffffff;-fx-size:13px;");
        FontAwesomeIconView logicon = new FontAwesomeIconView(FontAwesomeIcon.UNLOCK_ALT);
        logicon.setStyle("-fx-fill:#ffffff;-fx-size:13px;");

        JFXButton button1 = new JFXButton();
        Label label1 = new Label("G1");
        button1.setGraphic(icon);
        label1.setStyle("-fx-text-fill:WHITE");
        button1.setButtonType(ButtonType.RAISED);
        button1.setStyle("-fx-pref-width:30px;-fx-background-color:#F6C574;"
                + "-fx-background-radius:30px;-fx-pref-height:30px;");

        JFXButton button2 = new JFXButton();
        button2.setTooltip(new Tooltip("Log off"));
        button2.setButtonType(ButtonType.RAISED);
        button2.setGraphic(logicon);
        button2.setStyle("-fx-pref-width:30px;-fx-background-color:#F6C574;"
                + "-fx-background-radius:30px;-fx-pref-height:30px;");
        button2.setOnAction((ActionEvent event) -> {
            try {
                paneDrivers.getScene().getWindow().hide();
                Parent rood = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(rood);
                Stage driverStage = new Stage();
                driverStage.setScene(scene);
                driverStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JFXButton button3 = new JFXButton();
        button3.setButtonType(ButtonType.RAISED);
        button3.setTooltip(new Tooltip("Exit"));
        button3.setGraphic(closeicon);
        button3.setStyle("-fx-pref-width:30px;-fx-background-color:#F87951;"
                + "-fx-background-radius:30px;-fx-pref-height:30px;");
        button3.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        JFXNodesList nodesList = new JFXNodesList();
        nodesList.setSpacing(10);
        nodesList.addAnimatedNode(button1, (expanded) -> {
            return new ArrayList<KeyValue>() {
                {
                    add(new KeyValue(button1.rotateProperty(), expanded ? 360 : 0, Interpolator.EASE_BOTH));
                }
            };
        });
        nodesList.addAnimatedNode(button2);
        nodesList.addAnimatedNode(button3);

        fabsContainer.getChildren().add(nodesList);

    }

    private void setUpRipples() {
        JFXRippler ripplerUser = new JFXRippler(paneUsers, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler ripplerDriver = new JFXRippler(paneDrivers, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler ripplerBuses = new JFXRippler(paneBuses, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler ripplerTickets = new JFXRippler(paneTickets, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);

        boxMenus.getChildren().addAll(ripplerUser, ripplerDriver, ripplerBuses, ripplerTickets);
    }

}
