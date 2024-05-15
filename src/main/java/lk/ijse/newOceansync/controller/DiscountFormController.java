package lk.ijse.newOceansync.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DiscountFormController {

    @FXML
    private AnchorPane centerNode;

    public void initialize() {
        loadDiscounttable();
    }

    private void loadDiscounttable() {
        AnchorPane allDiscount = null;
        try {
            allDiscount = FXMLLoader.load(this.getClass().getResource("/view/view_all_discount.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(allDiscount);
    }

    @FXML
    void btnAddDiscountOnAction(ActionEvent event) {
        AnchorPane addDiscount = null;
        try {
            addDiscount = FXMLLoader.load(this.getClass().getResource("/view/add_discount.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(addDiscount);

    }

    @FXML
    void btnAllDiscountOnAction(ActionEvent event) {
        loadDiscounttable();
    }

    @FXML
    void btnDeleteDiscountOnAction(ActionEvent event) {
        AnchorPane deleteDiscount   = null;
        try {
            deleteDiscount = FXMLLoader.load(this.getClass().getResource("/view/delete_discount.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(deleteDiscount);
    }

}
