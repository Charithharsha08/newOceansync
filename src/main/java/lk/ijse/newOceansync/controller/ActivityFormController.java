package lk.ijse.newOceansync.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ActivityFormController {

    @FXML
    private AnchorPane centerNode;

    @FXML
    void btnActivityUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddActivityOnAction(ActionEvent event) {
        AnchorPane addActivity = null;
        try {
            addActivity = FXMLLoader.load(this.getClass().getResource("/view/add_activity.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(addActivity);
    }

    @FXML
    void btnAllActivityOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteActivityOnAction(ActionEvent event) {

    }

}
