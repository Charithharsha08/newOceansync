package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Stock;
import lk.ijse.newOceansync.repository.StockRepo;

import java.sql.SQLException;

public class DeleteStockController {

    public Label lblName;
    public Label lblPrice;
    public Label lblQty;
    @FXML
    private Label lblStockId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

 

    @FXML
    private JFXTextField txtSearchId;


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
       lblStockId.setText("");
       lblUserId.setText("");
       lblName.setText("");
       lblUserName.setText("");
       lblPrice.setText("");
       lblQty.setText("");
       txtSearchId.clear();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        try {
           boolean isDeleted = StockRepo.deleteStock(txtSearchId.getText());

           if (isDeleted) {
               new Alert(Alert.AlertType.INFORMATION, "Item Deleted Successfully!").show();
           }else {
               new Alert(Alert.AlertType.WARNING, "Item Not Deleted!").show();
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clearFields();

    }


    @FXML
    void txtSearchIconOnAction(ActionEvent event) {
        Stock stock = StockRepo.searchStockById(txtSearchId.getText());
        if (stock != null) {
            lblStockId.setText(stock.getStockId());
            lblUserId.setText(stock.getUserId());
            lblUserName.setText(stock.getName());
            lblName.setText(stock.getName());
            lblPrice.setText(String.valueOf(stock.getPrice()));
            lblQty.setText(String.valueOf(stock.getQty()));
        }

    }
}
