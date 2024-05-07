package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    public JFXButton btnHome;
    public JFXButton btnStock;
    public JFXButton btnEmployee;
    public JFXButton btnCustomer;
    public JFXButton btnActivity;
    public JFXButton btnCource;
    public JFXButton btnPayment;
    public JFXButton btnDiscount;
    @FXML
    private AnchorPane centerNode;

    @FXML
    private Pane innerPane;

    private JFXButton selectedButton;

    public void initialize(){
        loadHomeForm();

    }

    private void loadHomeForm()  {
        handleSelection(btnHome);
        AnchorPane homePane = null;
        try {
            homePane = FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(homePane);
    }



    @FXML
    void btnActivityOnAction(ActionEvent event) {

    }

    @FXML
    void btnCourceOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        handleSelection(btnCustomer);
        AnchorPane customerPane = null;
        try {
            customerPane = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(customerPane);
    }

    @FXML
    void btnDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmoloyeeOnAction(ActionEvent event) {
        handleSelection(btnEmployee);
        AnchorPane homePane = null;
        try {
            homePane = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(homePane);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
    loadHomeForm();
    }

    @FXML
    void btnMenuBarOnAction(MouseEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        handleSelection(btnPayment);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Payment Form");

        stage.show();
    }

    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        handleSelection(btnStock);
        AnchorPane stockPane = FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml"));
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(stockPane);
    }

    private void handleSelection(JFXButton button) {
        if(selectedButton != null){
            selectedButton.setStyle(""); // Deselect the previously selected button
        }
        selectedButton = button; // Set the new selected button
        selectedButton.setStyle("-fx-background-radius: 20px 0px 0px 20px;\n" +
                "    -fx-background-color: #97aeed;\n" +
                "    -fx-text-fill: #ffffff;"); // Apply the selected style
    }

}
