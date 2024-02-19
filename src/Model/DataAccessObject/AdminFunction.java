package Model.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.User;

public class AdminFunction {

    private static List<String> profile = new ArrayList<>();

    // Check Admin Details for Login
    public static boolean checkAdminDetails(Connection connection, User login) throws SQLException {
        boolean isAdmin = false;
        String checkQueryBySelect = "SELECT * FROM admin";

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String emailFromDB = resultSet.getString("mailId");
                String passwordFromDB = resultSet.getString("password");
                if (emailFromDB.equals(login.getEmailId()) && passwordFromDB.equals(login.getPassword())) {
                    isAdmin = true;
                    break;
                }
            }
        }
        if (isAdmin) {
            profile.add(login.getEmailId());
            profile.add(login.getPassword());
            profile.add("admin");
        }
        return isAdmin;
    }
}
