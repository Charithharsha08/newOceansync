package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;

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
}
