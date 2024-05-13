package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.Employee;
import lk.ijse.newOceansync.model.EmployeeTm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.newOceansync.controller.LoginFormController.credential;

public class EmployeeRepo {
    public static boolean employeeSave(Employee employee) throws SQLException {

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getEmployeeId());
        pstm.setObject(3, employee.getName());
        pstm.setObject(4, employee.getActivity());
        pstm.setObject(5, employee.getMonth());
        pstm.setObject(6, employee.getSalary());
        pstm.setObject(7, employee.getDate());
        // pstm.setObject(8, employee.getUserId());
        pstm.setObject(8,credential[0]);
        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {

            String id = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String name = resultSet.getString(3);
            String activity = resultSet.getString(4);
            String month = resultSet.getString(5);
            double salary = Double.parseDouble(resultSet.getString(6));
            Date date = Date.valueOf(resultSet.getString(7));
            String userId = resultSet.getString(8);

            Employee employee = new Employee(id, employeeId, name, activity, month, salary, date, userId);
            System.out.println(employee);
            employeeList.add(employee);
        }
        return employeeList;
    }


    public static boolean employeeUpdate(Employee employee) throws SQLException {

        String sql = "UPDATE employee SET employeeId=?, name=?, activity=?, month=?, salary=?, date=?, userId=? WHERE id=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getActivity());
        pstm.setObject(4, employee.getMonth());
        pstm.setObject(5, employee.getSalary());
        pstm.setObject(6, employee.getDate());
        pstm.setObject(7, employee.getUserId());
        pstm.setObject(8, employee.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean employeeDelete(String id) throws SQLException {

        String sql = "DELETE FROM employee WHERE id=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, id);
        return pstm.executeUpdate() > 0;
    }

//    public static String currentId() throws SQLException {
//        String sql = "SELECT id FROM employee ORDER BY id desc LIMIT 1";
//
//        Connection connection = DbConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        ResultSet resultSet = pstm.executeQuery();
//
//        if (resultSet.next()) {
//            return resultSet.getString(1);
//        }
//        return null;
//    }

    public static Employee employeeSearchById(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String Uid = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String name = resultSet.getString(3);
            String activity = resultSet.getString(4);
            String month = resultSet.getString(5);
            double salary = Double.parseDouble(resultSet.getString(6));
            Date date = Date.valueOf(resultSet.getString(7));
            String userId = resultSet.getString(8);

            employee = new Employee(Uid, employeeId, name, activity, month, salary, date, userId);
        }
        //System.out.println(employee);
        return employee;
    }

    public static String currerntId() {
        String sql = "SELECT id FROM employee ORDER BY id desc LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
