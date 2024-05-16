package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Cource;
import lk.ijse.newOceansync.repository.CourceRepo;

public class DeleteCourceFormController {

    @FXML
    private Label lblCost;

    @FXML
    private Label lblCourceId;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {

        txtSearchId.clear();
        lblCourceId.setText("");
        lblName.setText("");
        lblCost.setText("");
        lblDuration.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSearchId.getText();
        boolean isDeleted = CourceRepo.courceDelete(id);
        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Cource Deleted").show();
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtSearchId.getText();
        Cource cource = CourceRepo.getCourceByCourcId(id);

        if (cource != null) {

            lblCourceId.setText(cource.getCourceId());
            lblName.setText(cource.getName());
            lblCost.setText(String.valueOf(cource.getCost()));
            lblDuration.setText(String.valueOf(cource.getDuration()));
        }else{
            new Alert(Alert.AlertType.ERROR, "Cource not found").show();
        }

    }

}
