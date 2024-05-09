package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.SelectedCource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SelectedCourceRepo {
    public static boolean saveSelectedCource(List<SelectedCource> selectedCources)  {
        System.out.println("selected cource repo eke save ekt awa");
        for (SelectedCource selectedCource : selectedCources) {
            System.out.println(selectedCource);
        }
        String sql = "INSERT INTO selectedcource VALUES(?,?,?)";
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            for (SelectedCource selectedCource : selectedCources) {
                stm.setObject(1, selectedCource.getCustomerId());
                stm.setObject(2, selectedCource.getCourceId());
                stm.setObject(3,selectedCource.getDate());
                if (stm.executeUpdate() != 1) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
