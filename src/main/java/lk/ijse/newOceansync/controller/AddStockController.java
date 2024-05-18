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
import java.util.Arrays;

public class AddStockController {

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
    private JFXTextField txtname;

    public void initialize() {
        lblUserId.setText(LoginFormController.credential[0]);
        lblUserName.setText(LoginFormController.credential[1]);
        loadNextStockId();
    }
    private void loadNextStockId() {
        String currentId = StockRepo.currerntId();
        String nextId = nextId(currentId);
        lblStockId.setText(nextId);
    }

    private  String nextId(String currentId) {
        if (currentId != null) {
            // String[] split = currentId.split("O");
            String[] split = currentId.split("S");
            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            System.out.println("Arrays.toString(split) = " + split[0]);
            int id = Integer.parseInt(split[1]);    //2
            return "S" + ++id;

        }
        return "S1";
    }



    private void clearFields() {
       // lblUserId.setText("");
      //  lblStockId.setText("");
       // lblUserName.setText("");
        txtname.clear();
        txtPrice.clear();
        txtQty.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
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

        if (isValid()) {
            Stock stock = new Stock(stockId, name, price, qty, userId);
            try {
                boolean stockSave = StockRepo.stockSave(stock);
                if (stockSave) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock Save").show();
                    clearFields();
                    loadNextStockId();
                }
                clearFields();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Please add correct data").show();
                throw new RuntimeException(e);
            }


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
        btnSaveOnAction(event);

    }

    public void brnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }

    public void txtPriceReleaseOnAction(KeyEvent keyEvent) {
    Regex.setTextColor(TextField.UNIT_PRICE, txtPrice);

    }

    public void txtQtyReleaseOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY, txtQty);

    }
    public boolean isValid(){
        if (
                !Regex.setTextColor(TextField.UNIT_PRICE, txtPrice)) return false ;
                if (!Regex.setTextColor(TextField.QTY, txtQty)) return false;
        return true;
    }
}
