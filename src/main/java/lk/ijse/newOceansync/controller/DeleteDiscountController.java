package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Discount;
import lk.ijse.newOceansync.repository.DiscountRepo;

import java.sql.SQLException;

public class DeleteDiscountController {

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblDiscountType;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String discountId = txtSearchId.getText();

        boolean isDeleted = DiscountRepo.discountDelete(discountId);
        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
            clearFields();
        } else {
            new Alert(Alert.AlertType.WARNING, "Not Deleted").show();
        }

    }

    private void clearFields() {
        txtSearchId.clear();
        lblDiscount.setText(null);
        lblDiscountType.setText(null);
    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String discountId = txtSearchId.getText();
        try {
            Discount discount = DiscountRepo.discountSearchById(discountId);

            if (discount != null) {
                lblDiscountType.setText(discount.getType());
                lblDiscount.setText(String.valueOf(discount.getDiscount()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
        }

    }
}


