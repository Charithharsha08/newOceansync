package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.PlacePayment;

import java.sql.Connection;
import java.sql.SQLException;

public class PlacePaymentRepo {
    public static boolean placePayment(PlacePayment pp) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isPaymentSaved = PaymentRepo.savePayment(pp.getPayment());
            if (isPaymentSaved) {
                boolean isPaymentDetailSave = PaymentDetailRepo.savePaymentDetail(pp.getPaymentDetails());
                if (isPaymentDetailSave) {
                    if (pp.getSelectedStocks() != null) {
                        boolean isStockSelected = SelectedStockRepo.save(pp.getSelectedStocks());
                        System.out.println("Selected Stock save");
                        if (isStockSelected) {
                            boolean isStockQtyUpdate = StockRepo.updateStockQtyOnHand(pp.getSelectedStocks());
                            if (isStockQtyUpdate) {
                                connection.commit();
                                return true;
                            }
                        }

                    }
                    if (pp.getSelectedCources() != null) {
                        boolean isSelectedCourceSave = SelectedCourceRepo.saveSelectedCource(pp.getSelectedCources());
                        System.out.println("Selected Cource save");
                        if (isSelectedCourceSave) {
                            connection.commit();
                            return true;
                        }
                    }
                    if (pp.getSelectedActivities() != null) {
                        boolean isSelectedActivity = SelectedActivityRepo.saveSelectedActivity(pp.getSelectedActivities());
                        System.out.println("Selected Activity save ");
                        if (isSelectedActivity) {
                            connection.commit();
                            return true;
                        }
                    }


                    connection.rollback();
                    return false;
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}
