package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.Cource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourceRepo {
    public static String currentId() {
        String sql = "SELECT courceId FROM Cource ORDER BY courceId DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static boolean courceSave(Cource cource) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO cource VALUES(?,?,?,?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setObject(1, cource.getCourceId());
        pstm.setObject(2, cource.getName());
        pstm.setObject(3, cource.getDuration());
        pstm.setObject(4, cource.getCost());

        return pstm.executeUpdate() > 0;

    }

    public static List<String> getAllCources() throws SQLException {

        String sql = "SELECT name FROM cource";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        List<String> cources = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            cources.add(name);
        }
        return cources;
    }

    public static Cource getCourceByCourceName(String cource) throws SQLException {
        String sql = "SELECT * FROM cource WHERE name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, cource);
        ResultSet resultSet = pstm.executeQuery();
        String courceId = null;
        String name = null;
        String duration = null;
        double cost = 0;
        if (resultSet.next()) {
            courceId = resultSet.getString(1);
            name = resultSet.getString(2);
            duration = resultSet.getString(3);
            cost = Double.parseDouble(resultSet.getString(4));

        }
        return new Cource(courceId, name, duration, cost);
    }
    public static int getCourceCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS cource_count FROM cource";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int courcesCount = 0;
        if (resultSet.next()) {
            courcesCount = resultSet.getInt("cource_count");
        }
        return courcesCount;
    }

    public static List<Cource> getAllDiscount() throws SQLException {
        String sql = "SELECT * FROM cource";
        PreparedStatement pstm =DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Cource> courceList = new ArrayList<>();
        while (resultSet.next()) {
            String courceId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String duration = resultSet.getString(3);
            double cost = Double.parseDouble(resultSet.getString(4));
            Cource cource = new Cource(courceId, name, duration, cost);
            courceList.add(cource);
        }
        return courceList;


    }

    public static Cource getCourceByCourcId(String id) {
        String sql = "SELECT * FROM cource WHERE courceId = ?";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setObject(1, id);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                Cource cource = new Cource(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                );
                return cource;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean courceUpdate(Cource cource) {
        String sql = "UPDATE cource SET name=?,duration=?,cost=? WHERE courceId=?";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setObject(1, cource.getName());
            pstm.setObject(2, cource.getDuration());
            pstm.setObject(3, cource.getCost());
            pstm.setObject(4, cource.getCourceId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean courceDelete(String courceId) {

        try {
            Connection con = DbConnection.getInstance().getConnection();
            String sql = "DELETE FROM cource WHERE courceId=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, courceId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

