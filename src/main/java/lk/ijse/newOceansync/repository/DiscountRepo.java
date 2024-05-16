package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.Discount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepo {
    public static List<Double> getDiscount() throws SQLException {
        String sql = "SELECT discount FROM discount";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Double> discountList = new ArrayList<>();
        while (resultSet.next()) {
            double discount = resultSet.getInt(1);
            discountList.add(discount);
        }
        return discountList;
    }

    public static boolean discountSave(Discount discount) throws SQLException {
        PreparedStatement pstm = null;
        try {
            String sql = "INSERT INTO discount VALUES(?, ?, ?)";

            pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, discount.getDiscountId());
            pstm.setObject(2, discount.getType());
            pstm.setObject(3, discount.getDiscount());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pstm.executeUpdate() > 0;

    }

    public static String currentId() {
        String sql = "SELECT discountId FROM discount ORDER BY discountId DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<Discount> getAllDiscount() throws SQLException {
        String sql = "SELECT * FROM discount";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Discount> discountList = new ArrayList<>();
        while (resultSet.next()) {
            String discountId = resultSet.getString(1);
            String type = resultSet.getString(2);
            double discount = resultSet.getInt(3);

            Discount discount1 = new Discount(discountId, type, discount);
            // Discount discount1 = new Discount(discount);
            discountList.add(discount1);
        }
        return discountList;
    }

    public static Discount discountSearchById(String discountId) throws SQLException {
        String sql = "SELECT * FROM discount WHERE discountId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, discountId);
        ResultSet resultSet = pstm.executeQuery();
        Discount discount = null;

        if (resultSet.next()) {
            String discountId1 = resultSet.getString(1);
            String type = resultSet.getString(2);
            double discounts = resultSet.getInt(3);

            discount = new Discount(discountId1, type, discounts);

        }
        return discount;
    }


    public static boolean discountDelete(String discountId) {
        String sql = "DELETE FROM discount WHERE discountId = ?";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, discountId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

