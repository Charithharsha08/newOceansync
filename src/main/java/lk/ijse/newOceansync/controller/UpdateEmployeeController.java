package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.newOceansync.model.Employee;
import lk.ijse.newOceansync.repository.EmployeeRepo;
import lk.ijse.newOceansync.util.Regex;
import lk.ijse.newOceansync.util.TextField;

import java.sql.Date;
import java.sql.SQLException;

public class UpdateEmployeeController {

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXTextField txtActivity;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtMonth;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtsalary;

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtSearchId.clear();
        txtName.clear();
        txtEmployeeId.clear();
        txtsalary.clear();
        txtActivity.clear();
        dpDate.setValue(null);
        txtMonth.clear();
        lblId.setText(null);
        lblUserId.setText(null);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        double salary = 0;
        String id =lblId.getText();
        String userId = lblUserId.getText();
        String employeeId = txtEmployeeId.getText();
        String name = txtName.getText();
        String activity = txtActivity.getText();
        String month = txtMonth.getText();
        try {
            salary = Double.parseDouble(txtsalary.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric value for salary").show();
            txtsalary.requestFocus();
        }
        Date date = Date.valueOf(dpDate.getValue());
        if (isValid()) {
            Employee employee = new Employee(id, employeeId, name, activity, month, salary, date, userId);
            try {
                boolean isUpdated = EmployeeRepo.employeeUpdate(employee);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    @FXML
    void dpDateOnAction(ActionEvent event) {

    }

    @FXML
    void tctActivityOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmployeeIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtMonthOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSalaryOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String employeeId = txtSearchId.getText();
        //System.out.printf(employeeId);
        try {
            Employee employee = EmployeeRepo.employeeSearchById(employeeId);
            if (employee != null) {
               txtName.setText(employee.getName());
               txtEmployeeId.setText(employee.getEmployeeId());
               txtsalary.setText(String.valueOf(employee.getSalary()));
               txtActivity.setText(employee.getActivity());
               dpDate.setValue(employee.getDate().toLocalDate());
               txtMonth.setText(String.valueOf(employee.getMonth()));
               lblId.setText(employee.getId());
               lblUserId.setText(employee.getUserId());
              // lblUserName.setText(employee.getUserName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtSalaryKeyReleasedOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.SALARY, txtsalary);
    }
    public boolean isValid() {
        if (!Regex.setTextColor(TextField.SALARY, txtsalary)) return false;
        return true;
    }
}


