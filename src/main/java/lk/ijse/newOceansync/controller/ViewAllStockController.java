package lk.ijse.newOceansync.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.newOceansync.model.Stock;
import lk.ijse.newOceansync.repository.StockRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAllStockController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<Stock> lblViewAllStock;

    private List<Stock> stockList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.stockList = getAllStock();
        setCellValue();
        loadStockTable();

    }
    private List<Stock> getAllStock() {
        List<Stock> stockList = null;
        try {
            stockList = StockRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockList;
    }



    private void loadStockTable() {

            ObservableList<Stock> stocks = FXCollections.observableArrayList();
            for (Stock stock : stockList) {
                Stock stockTm = new Stock(
                        stock.getStockId(),
                        stock.getName(),
                        stock.getPrice(),
                        stock.getQty(),
                        stock.getUserId()
                );
                stocks.add(stockTm);
            }
            lblViewAllStock.setItems(stocks);
            System.out.println(stocks);
            Stock selectedStock = lblViewAllStock.getSelectionModel().getSelectedItem();
        }




    private void setCellValue() {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

}


