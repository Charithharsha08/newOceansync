package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.PaymentDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PaymentDetailRepo {
    public static boolean savePaymentDetail(List<PaymentDetail> paymentDetails) {
        System.out.println(" paydsgfadgsag "+paymentDetails);
        String sql = "INSERT INTO paymentDetail VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            for (PaymentDetail paymentDetail : paymentDetails) {
                stm.setObject(1, paymentDetail.getPaymentId());
                stm.setObject(2, paymentDetail.getCustomerId());
                stm.setObject(3, paymentDetail.getName());
                stm.setObject(4, paymentDetail.getId());
                stm.setObject(5, paymentDetail.getDescription());
                stm.setObject(6, paymentDetail.getUnitPrice());
                stm.setObject(7, paymentDetail.getQty());
                stm.setObject(8, paymentDetail.getDiscount());
                stm.setObject(9, paymentDetail.getTotal());
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
