package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Customer;
import lk.ijse.newOceansync.repository.CustomerRepo;

import java.sql.SQLException;

public class DeleteCustomerController {

    @FXML
    private Label lblName;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblTelNumber;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtSearchId.clear();
        lblAddress.setText("");
        lblName.setText("");
        lblCustomerId.setText("");
        lblTelNumber.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtSearchId.getText();
        try {
            boolean customerDeleted = CustomerRepo.customerDelete(customerId);
            if (customerDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {

        String customerId = txtSearchId.getText();
            Customer customer = CustomerRepo.searchCustomer(customerId);
            if (customer != null) {
                lblCustomerId.setText(customer.getCustomerId());
                lblName.setText(customer.getName());
                lblAddress.setText(customer.getAddress());
                lblTelNumber.setText(customer.getTel());
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }

    }

}
