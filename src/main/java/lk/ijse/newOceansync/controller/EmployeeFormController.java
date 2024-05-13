package lk.ijse.newOceansync.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.newOceansync.model.Employee;
import lk.ijse.newOceansync.model.EmployeeTm;
import lk.ijse.newOceansync.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colActivity;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private static TableView<EmployeeTm> tblEmployee;

    @FXML
    private AnchorPane sidepane;

    private List<Employee> employeeList = new ArrayList<>();

    public void initialize() {
//        this.employeeList =getAllEmployee();
//        setCellValueFactory();
//        loadEmployeeTable();
    }

    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> employeeTms = FXCollections.observableArrayList();
        for (Employee employee : employeeList) {
            employeeTms.add(new EmployeeTm(employee.getId(),employee.getEmployeeId(),employee.getName(),employee.getActivity(),employee.getSalary(),employee.getDate(),employee.getUserId()));
        }
        tblEmployee.setItems(employeeTms);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }


    @FXML
    void btnAddEmployeeInAction(ActionEvent event)  {
        AnchorPane addEmployee = null;
        try {
            addEmployee = FXMLLoader.load(this.getClass().getResource("/view/add_employee.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidepane.getChildren().clear();
        this.sidepane.getChildren().add(addEmployee);

    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        AnchorPane deleteEmployee = null;
        try {
            deleteEmployee = FXMLLoader.load(this.getClass().getResource("/view/delete_employee.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidepane.getChildren().clear();
        this.sidepane.getChildren().add(deleteEmployee);
    }

    @FXML
    void btnUpdateEmployeeAction(ActionEvent event) {
        AnchorPane updateEmployee = null;
        try {
            updateEmployee = FXMLLoader.load(this.getClass().getResource("/view/update_employee.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidepane.getChildren().clear();
        this.sidepane.getChildren().add(updateEmployee);
    }

}
