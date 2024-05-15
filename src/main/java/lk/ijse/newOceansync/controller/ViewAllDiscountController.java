package lk.ijse.newOceansync.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.newOceansync.model.Discount;
import lk.ijse.newOceansync.model.tm.DiscountTm;
import lk.ijse.newOceansync.repository.DiscountRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAllDiscountController {

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colDiscountId;

    @FXML
    private TableColumn<?, ?> colDiscountType;

    @FXML
    private TableView<DiscountTm> tblDiscount;

    private List<Discount> discountList = new ArrayList<>();

    public void initialize() {
        this.discountList =getAllDiscount();
        setCellValue();
        loadDiscountTable();


    }

    private void loadDiscountTable() {
        ObservableList<DiscountTm> discounts = FXCollections.observableArrayList();
        for (Discount discount1 : discountList) {
            DiscountTm discountTm = new DiscountTm(
                    discount1.getDiscountId(),
                    discount1.getType(),
                    discount1.getDiscount()
            );
            discounts.add(discountTm);
        }
        tblDiscount.setItems(discounts);
        //System.out.println(discount);
        DiscountTm selectedStock = tblDiscount.getSelectionModel().getSelectedItem();
        // System.out.println(selectedStock);
    }

    private void setCellValue() {
            colDiscountId.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        colDiscountType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

    }

    private List<Discount> getAllDiscount() {
        List<Discount> discountList = null;
        try {
            discountList = DiscountRepo.getAllDiscount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discountList;
    }

}
