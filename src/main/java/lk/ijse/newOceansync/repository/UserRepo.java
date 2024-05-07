package lk.ijse.newOceansync.repository;

import javafx.scene.control.Alert;
import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {

    public static User checkCredential(String userId, String pw) throws Exception {
        String sql = "SELECT * FROM user WHERE userId = ?";

        Connection connection = lk.ijse.newOceansync.db.DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();

        User user = null;
        if (resultSet.next()) {
            String dbPw = resultSet.getString(3);
            String dbUn = resultSet.getString(2);
            String dbUid = resultSet.getString(1);

            user = new User(dbUid, dbUn, dbPw);

        }

        return user;
    }

    public static boolean saveUser(String userId, String name, String pw) throws SQLException {

        String sql = "INSERT INTO user VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, name);
        pstm.setObject(3, pw);

        return pstm.executeUpdate() > 0;

    }
}
