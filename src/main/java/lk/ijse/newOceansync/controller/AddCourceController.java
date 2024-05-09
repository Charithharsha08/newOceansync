package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Cource;
import lk.ijse.newOceansync.repository.CourceRepo;

import java.sql.SQLException;

public class AddCourceController {

    @FXML
    private Label lblCourceId;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtName;
    
    public void initialize()  {
        loadNextCourceId();
    }

    private void loadNextCourceId() {
        String currentId = null;
        currentId = CourceRepo.currentId();
        String nextId = nextId(currentId);
        lblCourceId.setText(nextId);
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C");
            int id = Integer.parseInt(split[1]);    //2
            return "C" + ++id;

        }
        return "C1";
    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtName.clear();
        txtDuration.clear();
        txtCost.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String courceId = lblCourceId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        double cost = Double.parseDouble(txtCost.getText());

        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Name").show();
        }if (duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Duration").show();
        }if (txtCost.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Cost").show();
        }if(cost<0 ) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Cost").show();
        }

        Cource cource = new Cource(courceId,name,duration,cost);

        try {
            boolean isSaved = CourceRepo.courceSave(cource);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                clearFields();
                loadNextCourceId();
            }else {
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void txtCostOnAction(ActionEvent event) {
        btnSaveOnAction(event);
    }

    @FXML
    void txtDurationAction(ActionEvent event) {
        txtCost.requestFocus();
    }

    @FXML
    void txtnameOnAction(ActionEvent event) {
        txtDuration.requestFocus();

    }

}
