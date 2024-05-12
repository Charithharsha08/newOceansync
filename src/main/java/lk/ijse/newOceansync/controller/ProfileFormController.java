package lk.ijse.newOceansync.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static lk.ijse.newOceansync.controller.LoginFormController.credential;

public class ProfileFormController {

    public AnchorPane rootNode;
    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    public void initialize() {
        lblUserId.setText(credential[0]);
        lblUserName.setText(credential[1]);
    }



    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        AnchorPane profileForm = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        this.rootNode.getChildren().clear();
        this.rootNode.getChildren().add(profileForm);

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?").showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        AnchorPane rootNode1 = null;
                        try {
                            rootNode1 = FXMLLoader.load(this.getClass().getResource("/view/login_page.fxml"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Scene scene = new Scene(rootNode1);

                        Stage stage = (Stage) this.rootNode.getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        //stage.setTitle("Dashboard Form");
                    }
                }
        );
    }
    }
