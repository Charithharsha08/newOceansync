package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.SelectedStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SelectedStockRepo {
    public static boolean save(List<SelectedStock> selectedStock) throws SQLException, ClassNotFoundException {
        System.out.println(selectedStock.toString());
        PreparedStatement pstm = null;
        boolean b = false;
        for (SelectedStock selectedStocked : selectedStock) {
            Connection con = DbConnection.getInstance().getConnection();
            pstm = con.prepareStatement("INSERT INTO selectedstock VALUES(?,?,?,?,?)");
            pstm.setObject(1, selectedStocked.getItemId());
            pstm.setObject(2, selectedStocked.getQty());
            pstm.setObject(3, selectedStocked.getCustomerId());
            pstm.setObject(4, selectedStocked.getOrderId());
            pstm.setObject(5, selectedStocked.getDate());

            b = pstm.executeUpdate() > 0;

        }

        return b;
    }

}
