package lk.ijse.newOceansync.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.PipedReader;

public class StockFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane smallCenterNode;

    public void initialize() throws IOException {
        loadViewAllStock();
    }

    private void loadViewAllStock()  {

        AnchorPane loadViewAllStock = null;
        try {
            loadViewAllStock  = FXMLLoader.load(this.getClass().getResource("/view/view_all_stock.fxml"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.smallCenterNode.getChildren().clear();
        this.smallCenterNode.getChildren().add(loadViewAllStock);

    }

    @FXML
    void btnAddStockOnAction(ActionEvent event) throws IOException {
        AnchorPane addStock = FXMLLoader.load(this.getClass().getResource("/view/add_stock.fxml"));
        this.smallCenterNode.getChildren().clear();
        this.smallCenterNode.getChildren().add(addStock);
    }

    @FXML
    void btnAllStockOnAction(ActionEvent event) throws IOException {
        loadViewAllStock();
    }

    @FXML
    void btnDeleteStockOnAction(ActionEvent event) throws IOException {
        AnchorPane deleteStock = FXMLLoader.load(this.getClass().getResource("/view/delete_stock.fxml"));
        this.smallCenterNode.getChildren().clear();
        this.smallCenterNode.getChildren().add(deleteStock);
    }

    @FXML
    void btnStockUpdateOnAction(ActionEvent event) throws IOException {
        AnchorPane updateStock = FXMLLoader.load(this.getClass().getResource("/view/update_stock.fxml"));
        this.smallCenterNode.getChildren().clear();
        this.smallCenterNode.getChildren().add(updateStock);
    }



}
