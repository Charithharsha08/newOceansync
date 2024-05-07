package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Customer;
import lk.ijse.newOceansync.repository.CustomerRepo;

import java.sql.SQLException;

public class AddCustomerController {

    @FXML
    private Label lblCustomerId;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtTel;
public void initialize(){
    loadNextCustomerId();
}

    private void loadNextCustomerId() {
        String currentId = null;
        try {
            currentId = CustomerRepo.currentId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String nextId = nextId(currentId);
        lblCustomerId.setText(nextId);

    }

    private String nextId(String currentId) {
        if (currentId != null) {
            // String[] split = currentId.split("O");
            String[] split = currentId.split("U");
            //  System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
//System.out.println("Arrays.toString(split) = " + split[0]);
            int id = Integer.parseInt(split[1]);    //2
            return "U" + ++id;

        }
        return "U1";
    }


    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtCustomerName.clear();
    txtAddress.clear();
    txtTel.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String customerId = lblCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        if (customerId.isEmpty()){
            lblCustomerId.setStyle("-fx-text-fill: red");
            return;
        }if (customerName.isEmpty()) {
            txtCustomerName.setStyle("-fx-text-fill: red");
            return;
        }if (address.isEmpty()) {
            txtAddress.setStyle("-fx-text-fill: red");
            return;
        }if (tel.isEmpty()) {
            txtTel.setStyle("-fx-text-fill: red");
            return;
        }
        Customer customer = new Customer(customerId,customerName,address,tel);
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



    @FXML
    void txtAddressOnAction(ActionEvent event) {
    txtTel.requestFocus();

    }

    @FXML
    void txtCustomerNameOnAction(ActionEvent event) {
txtAddress.requestFocus();
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {
    btnSaveOnAction(event);

    }

}
