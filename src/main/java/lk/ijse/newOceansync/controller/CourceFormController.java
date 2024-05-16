package lk.ijse.newOceansync.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CourceFormController {

    @FXML
    private AnchorPane centerNode;

    public void initialize() {
        loadCourcetable();

    }

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
        loadCourcetable();
    }

    private void loadCourcetable() {
        AnchorPane allCource = null;
        try {
            allCource = FXMLLoader.load(this.getClass().getResource("/view/view_all_cource.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(allCource);
    }

    @FXML
    void btnCourceUpdateOnAction(ActionEvent event) {
        AnchorPane updateCource = null;
        try {
            updateCource = FXMLLoader.load(this.getClass().getResource("/view/update_cource.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(updateCource);
    }

    @FXML
    void btnDeleteCourceOnAction(ActionEvent event) {
        AnchorPane deleteCource  = null;
        try {
            deleteCource = FXMLLoader.load(this.getClass().getResource("/view/delete_cource.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(deleteCource);
    }

}
