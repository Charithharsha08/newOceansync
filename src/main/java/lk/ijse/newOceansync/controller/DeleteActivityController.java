package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Activity;
import lk.ijse.newOceansync.model.Cource;
import lk.ijse.newOceansync.repository.ActivityRepo;

import java.sql.SQLException;

public class DeleteActivityController {

    @FXML
    private Label lblActivityId;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblName;

    @FXML
    private Label lblType;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtSearchId.clear();
        lblActivityId.setText("");
        lblCost.setText("");
        lblLocation.setText("");
        lblName.setText("");
        lblType.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSearchId.getText();
        try {
            boolean isDeleted = ActivityRepo.activityDelete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Activity Deleted").show();
                clearFields();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtSearchId.getText();
        try {
            Activity activity = ActivityRepo.activitySearchById(id);
            lblActivityId.setText(activity.getActivityId());
            lblCost.setText(String.valueOf(activity.getCost()));
            lblLocation.setText(activity.getLocation());
            lblName.setText(activity.getName());
            lblType.setText(activity.getType());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
