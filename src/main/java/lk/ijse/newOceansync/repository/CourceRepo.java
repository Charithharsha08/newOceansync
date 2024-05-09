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
    }

