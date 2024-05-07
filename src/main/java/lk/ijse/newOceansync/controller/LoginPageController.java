package lk.ijse.newOceansync.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Pane sidePane;



    public void initialize() throws IOException {
        AnchorPane loginForm = FXMLLoader.load(
                this.getClass().getResource("/view/login_form.fxml."));
        this.sidePane.getChildren().clear();
        this.sidePane.getChildren().add(loginForm);
    }

}

