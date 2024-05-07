package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.newOceansync.model.Employee;
import lk.ijse.newOceansync.repository.EmployeeRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public class AddEmployeeController {

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextField txtActivity;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtMonth;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtsalary;
    public void initialize(){
        lblUserId.setText(LoginFormController.credential[0]);
        lblUserName.setText(LoginFormController.credential[1]);
        loadNextId();
    }

    private void loadNextId() {
        String currentId = EmployeeRepo.currerntId();
        String nextId = nextId(currentId);
        lblId.setText(nextId);
    }

    private static String nextId(String currentId) {
        if (currentId != null) {
            // String[] split = currentId.split("O");
            String[] split = currentId.split("E");
            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            System.out.println("Arrays.toString(split) = " + split[0]);
            int id = Integer.parseInt(split[1]);    //2
            return "E" + ++id;

        }
        return "E1";
    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtName.clear();
        txtActivity.clear();
        txtsalary.clear();
        txtEmployeeId.clear();
        txtMonth.clear();
        dpDate.setValue(null);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
       String id = lblId.getText();
       String userId = lblUserId.getText();
      // String userName = lblUserName.getText();
       String employeeId = txtEmployeeId.getText();
       String name = txtName.getText();
       String activity = txtActivity.getText();
       double salary = Double.parseDouble(txtsalary.getText());
       String month = txtMonth.getText();
       Date date = Date.valueOf(dpDate.getValue().toString());

       if (employeeId.isEmpty()){
           new Alert(Alert.AlertType.INFORMATION, "Please Enter Employee Id").show();
       }if (name.isEmpty()){
           new Alert(Alert.AlertType.INFORMATION, "Please Enter Name").show();
        }if (activity.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Activity").show();
        }if (salary == 0){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Salary").show();
        }if (month.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Month").show();
        }if (date.toString().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Date").show();
        }
       Employee employee = new Employee(id,employeeId,name,activity,month,salary,date,userId);

        try {
            boolean isEmployeeSaved = EmployeeRepo.employeeSave(employee);
            if (isEmployeeSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved").show();
                clearFields();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            throw new RuntimeException(e);
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

}
