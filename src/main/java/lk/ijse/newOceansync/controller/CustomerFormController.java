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
import lk.ijse.newOceansync.model.Customer;
import lk.ijse.newOceansync.model.tm.CustomerTm;
import lk.ijse.newOceansync.repository.CustomerRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane sidepane;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    private List<Customer> customerList = new ArrayList<>();

    public void initialize(){
        this.customerList = getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
    }
    private List<Customer> getAllCustomers() {
        List<Customer> customerList = null;
        try {
            customerList = CustomerRepo.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return customerList;
    }
    private void setCellValueFactory() {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }
    private void loadCustomerTable() {
        ObservableList<CustomerTm> customerObservableList = FXCollections.observableArrayList();
        for (Customer customer : customerList) {
            // System.out.println(customerList);
            CustomerTm customerTm = new CustomerTm(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getTel()
            );
            customerObservableList.add(customerTm);
            System.out.println(customerTm.toString());
        }
        tblCustomer.setItems(customerObservableList);
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        AnchorPane addCustomer  = null;
        try {
            addCustomer = FXMLLoader.load(this.getClass().getResource("/view/add_customer.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidepane.getChildren().clear();
        this.sidepane.getChildren().add(addCustomer);
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        AnchorPane deleteCustomer  = null;
        try {
            deleteCustomer = FXMLLoader.load(this.getClass().getResource("/view/delete_customer.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidepane.getChildren().clear();
        this.sidepane.getChildren().add(deleteCustomer);

    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        AnchorPane updateCustomer  = null;
        try {
            updateCustomer = FXMLLoader.load(this.getClass().getResource("/view/update_customer.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidepane.getChildren().clear();
        this.sidepane.getChildren().add(updateCustomer);
    }

}
