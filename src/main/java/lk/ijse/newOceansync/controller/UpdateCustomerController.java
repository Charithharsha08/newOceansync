package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Customer;
import lk.ijse.newOceansync.repository.CustomerRepo;

import java.sql.SQLException;

public class UpdateCustomerController {

    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtTel;
    public Label lblCustomerId;
    @FXML
    private JFXTextField txtSearchId;

    public void txtCustomerNameOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();

    }

    public void txtAddressOnAction(ActionEvent actionEvent) {
        txtTel.requestFocus();
    }

    public void txtTelOnAction(ActionEvent actionEvent) {
        txtTel.requestFocus();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        if (customerId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Customer Id").show();
        }if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Customer Name").show();
        }if (address.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Address").show();
        }if (tel.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Telephone Number").show();
        }
        Customer customer = new Customer(customerId, customerName, address, tel);
        try {
            boolean customerSaved = CustomerRepo.customerSave(customer);
            if (customerSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                clearFields();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
       // lblCustomerId.setText("");
        txtCustomerName.clear();
        txtAddress.clear();
        txtTel.clear();
        txtSearchId.clear();
        lblCustomerId.setText("");
    }

    public void brnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtSearchIdOnAction(ActionEvent actionEvent) {

        String customerId = txtSearchId.getText();
        Customer customer = CustomerRepo.searchCustomer(customerId);
        if (customer != null){
            lblCustomerId.setText(customer.getCustomerId());
            txtCustomerName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtTel.setText(customer.getTel());
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
        }
    }
}
