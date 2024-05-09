package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.SelectedStock;
import lk.ijse.newOceansync.model.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static boolean stockSave(Stock stock) throws SQLException {

        String sql = "INSERT INTO stock VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, stock.getStockId());
        pstm.setObject(2, stock.getName());
        pstm.setObject(3, stock.getPrice());
        pstm.setObject(4, stock.getQty());
        pstm.setObject(5, stock.getUserId());


        return pstm.executeUpdate() > 0;
    }

    public static boolean stockUpdate(Stock stock) throws SQLException {

        String sql = "UPDATE stock SET name=?, type=?, qty=?, userId=? WHERE itemId=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, stock.getName());
        pstm.setObject(2, stock.getPrice());
        pstm.setObject(3, stock.getQty());
        pstm.setObject(4, stock.getUserId());
        pstm.setObject(5, stock.getStockId());

        return pstm.executeUpdate() > 0;
    }

    public static List<Stock> getAll() throws SQLException {
        String sql = "SELECT * FROM stock";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Stock> stockList = new ArrayList<>();
        while (resultSet.next()) {

            String itemId = resultSet.getString(1);
            String name = resultSet.getString(2);
            double price = Double.parseDouble(resultSet.getString(3));
            int qty = resultSet.getInt(4);
            String userId = resultSet.getString(5);

            Stock stock = new Stock(itemId, name, price, qty, userId);
            stockList.add(stock);
        }
        return stockList;
    }

    public static List<Stock> getAllStock() throws SQLException {
        String sql = "SELECT name FROM stock";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        List<Stock> stockList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            String name = pstm.getResultSet().getString(1);
           // stockList.add(name);
        }
        return stockList;
    }

    public static List<String> getAllStockName() throws SQLException {
        String sql = "SELECT name FROM stock";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        List<String> stockList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            String name = pstm.getResultSet().getString(1);
            stockList.add(name);
        }
        return stockList;
    }



    public static Stock getStockByStockName(String stockName) throws SQLException {
        String sql = "SELECT * FROM stock WHERE name=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, stockName);
        ResultSet resultSet = pstm.executeQuery();
        String itemId = null;
        String name = null;
        double price = 0;
        int qty = 0;
        String userId = null;
        if (resultSet.next()) {
            itemId = resultSet.getString(1);
            name = resultSet.getString(2);
            price = Double.parseDouble(resultSet.getString(3));
            qty = resultSet.getInt(4);
            userId = resultSet.getString(5);

        }
        return new Stock(itemId, name, price, qty, userId);
    }
    public static boolean updateStockQty(List<Stock> stocks) {
        for (Stock stock : stocks) {
            if(updateStockQty(stock)){
                return true;
            }
        }
        return true;
    }

    public static boolean updateStockQty(Stock stocks) {
        System.out.println(stocks.toString());

        String sql = "UPDATE stock SET qty=? WHERE itemId=?";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, stocks.getQty());
            pstm.setObject(2, stocks.getStockId());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String currerntId() {
        String sql = "SELECT itemId FROM stock ORDER BY itemId DESC LIMIT 1";
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return rst.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Stock searchStockById(String text) {
        String sql = "SELECT * FROM stock WHERE itemId=?";
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setObject(1, text);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return new Stock(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4), rst.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean deleteStock(String text) throws SQLException {
        String sql = "DELETE FROM stock WHERE itemId=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, text);
        return pstm.executeUpdate() > 0;
    }
    public static boolean updateStockQtyOnHand(List<SelectedStock> selectedStocks){
        System.out.println("update stock qty on hand "+selectedStocks);
        for (SelectedStock selectedStocked : selectedStocks) {
        String sql = "UPDATE stock SET qty = qty - ? WHERE itemId = ?";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, selectedStocked.getQty());
            pstm.setObject(2, selectedStocked.getItemId());


            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

    } return false;
        }
}
//    public static boolean updateStockQtyOnHand(SelectedStock selectedStocks){
//        System.out.println(selectedStocks);
//        String sql = "UPDATE stock SET qty = qty - ? WHERE itemId = ?";
//        try {
//            PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                    .prepareStatement(sql);
//            pstm.setObject(1, selectedStocks.getQty());
//            pstm.setObject(2, selectedStocks.getItemId());
//
//
//            return pstm.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
  //}
