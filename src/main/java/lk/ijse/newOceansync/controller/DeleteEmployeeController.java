package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Employee;
import lk.ijse.newOceansync.model.EmployeeTm;
import lk.ijse.newOceansync.repository.EmployeeRepo;

import java.sql.SQLException;

public class DeleteEmployeeController {

    @FXML
    private Label lblActivity;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblSalary;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lbllMonth;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtSearchId.clear();
        lblActivity.setText(null);
        lblDate.setText(null);
        lblEmployeeId.setText(null);
        lblId.setText(null);
        lblName.setText(null);
        lblSalary.setText(null);
        lblUserId.setText(null);
        lblUserName.setText(null);
        lbllMonth.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employeeId = txtSearchId.getText();
        try {
            EmployeeRepo.employeeDelete(employeeId);
            if (employeeId != null) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            throw new RuntimeException(e);
        }
}

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String employeeId = txtSearchId.getText();
        //System.out.printf(employeeId);
        try {
            Employee employee = EmployeeRepo.employeeSearchById(employeeId);
            if (employee != null) {
                lblActivity.setText(employee.getActivity());
                lblDate.setText(String.valueOf(employee.getDate()));
                lblEmployeeId.setText(employee.getEmployeeId());
                lblId.setText(employee.getId());
                lblName.setText(employee.getName());
                lblSalary.setText(String.valueOf(employee.getSalary()));
                lblUserId.setText(employee.getUserId());
                lbllMonth.setText(String.valueOf(employee.getMonth()));
               // lblUserName.setText(credential[0]);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    }


