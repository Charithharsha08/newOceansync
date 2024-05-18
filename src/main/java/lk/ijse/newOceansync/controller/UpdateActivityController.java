package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.newOceansync.model.Activity;
import lk.ijse.newOceansync.repository.ActivityRepo;
import lk.ijse.newOceansync.util.Regex;
import lk.ijse.newOceansync.util.TextField;

import java.sql.SQLException;

public class UpdateActivityController {

    @FXML
    private Label lblActivityId;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtLocation;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtType;

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtSearchId.clear();
        txtName.clear();
        txtType.clear();
        txtLocation.clear();
        txtCost.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        double cost = 0;
        String id = txtSearchId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String location = txtLocation.getText();
        
        try {
            cost = Double.parseDouble(txtCost.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric value for cost").show();
            txtCost.requestFocus();
        }if (isValid()){


        Activity activity = new Activity(id, name, type, location, cost);
        try {
            boolean isUpdated = ActivityRepo.activityUpdate(activity);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Activity updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        }

    }

    @FXML
    void txtCostOnAction(ActionEvent event) {
    txtLocation.requestFocus();
    }

    @FXML
    void txtLocationOnAction(ActionEvent event) {
        btnUpdateOnAction(event);

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtType.requestFocus();

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {

        String id = txtSearchId.getText();
        try {
            Activity activity = ActivityRepo.activitySearchById(id);
            if (activity != null) {
                lblActivityId.setText(activity.getActivityId());
                txtName.setText(activity.getName());
                txtType.setText(activity.getType());
                txtLocation.setText(activity.getLocation());
                txtCost.setText(String.valueOf(activity.getCost()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtTypeOnAction(ActionEvent event) {
        txtCost.requestFocus();

    }

    public void txtCostReleasedOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.AMOUNT, txtCost);
    }
    public boolean isValid() {
        if (!Regex.setTextColor(TextField.AMOUNT, txtCost)) return false;
        return true;
    }
}
