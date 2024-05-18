package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.newOceansync.model.Stock;
import lk.ijse.newOceansync.repository.StockRepo;
import lk.ijse.newOceansync.util.Regex;
import lk.ijse.newOceansync.util.TextField;

import java.sql.SQLException;

public class UpdateStockController {

    @FXML
    private Label lblStockId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtname;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();

    }

    private void clearField() {
        txtname.clear();
        txtPrice.clear();
        txtQty.clear();
        txtSearchId.clear();
        lblStockId.setText("");
        lblUserId.setText("");
        lblUserName.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        double price;
        int qty;

        String stockId = lblStockId.getText();
        String name = txtname.getText();
         try {
             price = Double.parseDouble(txtPrice.getText());
         }catch (NumberFormatException e){
             new Alert(Alert.AlertType.ERROR, "Invalid price").show();
             txtPrice.requestFocus();
             return;
         }
       try {
           qty = Integer.parseInt(txtQty.getText());
       }catch (NumberFormatException e){
           new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
           txtQty.requestFocus();
           return;
       }
        String userId = lblUserId.getText();

        if (txtname.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Stock Name").show();
        }
        if (txtPrice.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Stock Price").show();
        }
        if (txtQty.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Stock Quantity").show();
        }

        Stock stock = new Stock(stockId, name, price, qty, userId);

        try {
            boolean isUpdated = StockRepo.stockUpdate(stock);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Updated").show();
                clearField();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void txtQtyonAction(ActionEvent event) {
        btnUpdateOnAction(event);
    }

    @FXML
    void txtSearchIconOnAction(ActionEvent event) {
        String stockId = txtSearchId.getText();
        try {
            Stock stock = StockRepo.searchStockById(stockId);
            if (stock != null) {
                lblStockId.setText(stock.getStockId());
                txtname.setText(stock.getName());
                txtPrice.setText(String.valueOf(stock.getPrice()));
                txtQty.setText(String.valueOf(stock.getQty()));
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid Stock Id").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void txtPriceKeyRelealeOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.AMOUNT, txtPrice);
    }

    public void txtQtyKeyReleasedOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY, txtQty);
    }
    public boolean isValid() {
        if (!Regex.setTextColor(TextField.AMOUNT, txtPrice)) return false;
        if (!Regex.setTextColor(TextField.QTY, txtQty)) return false;
        return true;
    }
}