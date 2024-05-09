package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.Activity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepo {
    public static boolean activitySave(Activity activity) throws SQLException {
        String sql = "INSERT INTO activity VALUES (?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,activity.getActivityId());
        pstm.setObject(2,activity.getName());
        pstm.setObject(3,activity.getType());
        pstm.setObject(4,activity.getLocation());
        pstm.setObject(5,activity.getCost());

        return pstm.executeUpdate()>0;
    }
    public static boolean activityUpdate(Activity activity) throws SQLException {

        String sql = "UPDATE activity SET name=?, type=?, location=?, cost=? WHERE activityId=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,activity.getName());
        pstm.setObject(2,activity.getType());
        pstm.setObject(3,activity.getLocation());
        pstm.setObject(4,activity.getCost());
        pstm.setObject(5,activity.getActivityId());

        return pstm.executeUpdate()>0;
    }
    public static Activity activitySearchById(String id) throws SQLException {
        String sql = "SELECT * FROM activity WHERE activityId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();
        Activity activity = null;

        if (resultSet.next()) {
            String activityId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String type = resultSet.getString(3);
            String location = resultSet.getString(4);
            double cost = resultSet.getDouble(5);

            activity = new Activity(activityId, name, type, location, cost);
        }


        return activity;
    }

    public static boolean activityDelete(String activityId) throws SQLException {
        System.out.println(activityId);

        String sql = "DELETE FROM activity WHERE activityId = ?";
        PreparedStatement pstm = null;
        try {
            pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, activityId);
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstm.executeUpdate() > 0;
    }
    public static List<String> getAllActivities() throws SQLException {

        String sql = "SELECT name FROM activity";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        List<String> acivities = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            acivities.add(name);
        }
        return acivities;
    }

    public static Activity getActivityByActivityName(String activity) throws SQLException {
        String sql = "SELECT * FROM activity WHERE name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, activity);
        ResultSet resultSet = pstm.executeQuery();

        String activityId = null;
        String name = null;
        String type = null;
        String location = null;
        double cost = 0;
        if (resultSet.next()) {
            activityId = resultSet.getString(1);
            name = resultSet.getString(2);
            type = resultSet.getString(3);
            location = resultSet.getString(4);
            cost = resultSet.getDouble(5);

        }
        return new Activity(activityId, name, type, location, cost);
    }

    public static String currentId() {
        String sql = "SELECT activityId FROM activity ORDER BY activityId DESC LIMIT 1";
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
}
