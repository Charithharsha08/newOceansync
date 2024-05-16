package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Cource;
import lk.ijse.newOceansync.repository.CourceRepo;

public class UpdateCourceController {

    @FXML
    private Label lblCourceId;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {

        txtSearchId.clear();
        txtName.clear();
        txtCost.clear();
        txtDuration.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtSearchId.getText();
        String name = txtName.getText();
        double cost = Double.parseDouble(txtCost.getText());
        String duration = txtDuration.getText();

        if (txtName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Name").show();
        }
        if (txtCost.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Cost").show();
        }
        if (txtDuration.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Duration").show();
        }
        Cource cource = new Cource(id, name, duration, cost);
        boolean isUpdated = CourceRepo.courceUpdate(cource);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Cource Updated").show();
            clearFields();
        }else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }

    }

    @FXML
    void txtCostOnAction(ActionEvent event) {
txtDuration.requestFocus();
    }

    @FXML
    void txtDurationOnAction(ActionEvent event) {
btnUpdateOnAction(event);
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
txtCost.requestFocus();
    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtSearchId.getText();

        Cource cource = CourceRepo.getCourceByCourcId(id);
        if (cource != null) {
            lblCourceId.setText(cource.getCourceId());
            txtName.setText(cource.getName());
            txtCost.setText(String.valueOf(cource.getCost()));
            txtDuration.setText(cource.getDuration());
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Cource Id").show();
        }

    }

}
