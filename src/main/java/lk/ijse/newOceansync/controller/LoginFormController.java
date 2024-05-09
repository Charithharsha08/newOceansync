package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.newOceansync.model.User;
import lk.ijse.newOceansync.repository.UserRepo;
import lk.ijse.newOceansync.util.Regex;

import java.io.IOException;


public class LoginFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Pane sidePane;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtuserid;

    public static String[] credential = new String[3];

    @FXML
    void btnLoginOnAction(ActionEvent event) {
           String userId = txtuserid.getText();
           String password = txtPassword.getText();

        try {
            User user = UserRepo.checkCredential(userId, password);
            if (user.getUserId().equals(userId) && user .getPassword().equals(password)) {
                navigateToTheDashboard();
                credential[0] = user.getUserId();
                credential[1] = user.getUserName();
                credential[2] = user.getPassword();

            }if (!user.getUserId().equals(userId)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("User Id is Incorrect");
                alert.showAndWait();
            }else if (!user.getPassword().equals(password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Password is Incorrect");
                alert.showAndWait();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToTheDashboard() {
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void linkRegistrationOnAction(ActionEvent event) throws IOException {
        AnchorPane activityPane = FXMLLoader.load(
                this.getClass().getResource("/view/registration_form.fxml."));
        this.sidePane.getChildren().clear();
        this.sidePane.getChildren().add(activityPane);
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLoginOnAction(event);
    }


    public void txtUserIdOnReleasedAction(KeyEvent keyEvent) {
    //    if (!Regex.setTextColor(T))
    }

    public void txtPasswordOnReleasedAction(KeyEvent keyEvent) {
        //
    }
}
