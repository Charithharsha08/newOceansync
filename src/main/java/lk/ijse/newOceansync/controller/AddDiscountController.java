package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.newOceansync.model.Discount;
import lk.ijse.newOceansync.repository.DiscountRepo;
import lk.ijse.newOceansync.util.Regex;
import lk.ijse.newOceansync.util.TextField;

import java.sql.SQLException;

public class AddDiscountController {

    @FXML
    private JFXComboBox<DiscountType> cmbDiscountType;

    @FXML
    private Label lblDiscountId;

    @FXML
    private JFXTextField txtDiscount;
    public void initialize(){
        loadDiscountType();
        loadNextDiscountId();
        getDiscountTypes();
    }

    void getDiscountTypes() {
        cmbDiscountType.getItems().clear();
        for (DiscountType type : DiscountType.values()) {
            cmbDiscountType.getItems().add(type);
        }
    }


    private void loadNextDiscountId() {
        String currentId = null;
        currentId =DiscountRepo.currentId();
        String nextId = nextId(currentId);
        lblDiscountId.setText(nextId);

    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("D");
            //  System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
//System.out.println("Arrays.toString(split) = " + split[0]);
            int id = Integer.parseInt(split[1]);    //2
            return "D" + ++id;

        }
        return "D1";

    }

    private void loadDiscountType() {
        cmbDiscountType.getItems().clear();
        for (DiscountType type : DiscountType.values()) {
            cmbDiscountType.getItems().add(type);
        }
    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        double discount;
        String discountId  = lblDiscountId.getText();
        String type = cmbDiscountType.getValue().toString();
        try {
            discount = Double.parseDouble(txtDiscount.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid discount").show();
            txtDiscount.requestFocus();
            return;
        }
        if (!isValid()){



        Discount discount1 = new Discount(discountId,type,discount);
        try {
            boolean isdiscountSaved = DiscountRepo.discountSave(discount1);
            if (isdiscountSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                clearFields();
                loadNextDiscountId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
        }

        }
    }

    private void clearFields() {
        lblDiscountId.setText("");
        txtDiscount.clear();
        cmbDiscountType.getSelectionModel().clearSelection();
    }

    @FXML
    void cmbDiscountTypeOnAction(ActionEvent event) {
        txtDiscount.requestFocus();

    }

    @FXML
    void txtDiscountOnAction(ActionEvent event) {
        btnSaveOnAction(event);

    }

    public void txtDiscountKeyReleaseOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.DISCOUNT, txtDiscount);
    }

    public boolean isValid() {
        if (!Regex.setTextColor(TextField.DISCOUNT, txtDiscount)) return false;
        return true;
    }

    enum DiscountType {
        LOCAL, FOREIGN
    }

}
