package lk.ijse.newOceansync.repository;

import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean customerSave(Customer customer) throws SQLException {
//System.out.println(customer);

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getTel());

        return pstm.executeUpdate() > 0;

    }

    public static Customer searchCustomer(String customerId) {

        try {
            String sql = "SELECT * FROM customer WHERE customerId = ?";
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setObject(1, customerId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean customerDelete(String customerId) throws SQLException {

        String sql = "DELETE FROM customer WHERE customerId=?";
        PreparedStatement pstm = null;
        try {
            pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, customerId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);



        ResultSet resultSet = pstm.executeQuery();
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()){

            String customerId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            Customer customer = new Customer(customerId,name,address,contact);
            customerList.add(customer);
        }
        return customerList;
    }

    public static Customer getCustomerByTel(String contact) throws SQLException {
        String sql = "SELECT * FROM customer WHERE tel = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, contact);

        ResultSet resultSet = pstm.executeQuery();
        Customer customer = null;

        if (resultSet.next()) {
            String customerId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);

            customer = new Customer(customerId, name, address, tel);
        }
        return customer;
    }
    public static int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int customerCount = 0;
        if (resultSet.next()) {
            customerCount = resultSet.getInt("customer_count");
        }
        return customerCount;
    }

    public static boolean customerUpdate(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name=?, address=?, tel=? WHERE customerId=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getTel());
        pstm.setObject(4, customer.getCustomerId());


        return pstm.executeUpdate() > 0;

    }


    public static List<String> getAllCustomerPhoneNumbers() throws SQLException {
        List<String> phoneNumbers = new ArrayList<>();
        String sql = "SELECT tel FROM customer";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            phoneNumbers.add(rs.getString("tel"));
        }
        return phoneNumbers;
    }
}
