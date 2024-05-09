package lk.ijse.newOceansync.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CourceFormController {

    @FXML
    private AnchorPane centerNode;

    @FXML
    void btnAddCourceOnAction(ActionEvent event) {
        AnchorPane addCource = null;
        try {
            addCource = FXMLLoader.load(this.getClass().getResource("/view/add_cource.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(addCource);
    }



    @FXML
    void btnAllCourceOnAction(ActionEvent event) {

    }

    @FXML
    void btnCourceUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteCourceOnAction(ActionEvent event) {

    }

}
