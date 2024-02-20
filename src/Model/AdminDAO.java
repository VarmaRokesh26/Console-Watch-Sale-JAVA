package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Admin;
import DAO.User;

public class AdminDAO {

    public static List<String> profile = new ArrayList<>();
    private static String query;

    // Check Admin Details for Login
    public static boolean checkAdminDetails(Connection connection, User login) throws SQLException {
        boolean isAdmin = false;
        query = "SELECT * FROM admin";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
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

    // Inserting Logic for new Admin
    public static boolean insertAdminDetails(Connection connection, Admin admin) throws SQLException {
        String insertQuery = "INSERT INTO admin (adminId, adminName, mobileNumber, mailId, role, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, admin.getAdminId());
            System.out.println(admin.getAdminId());
            System.out.println(admin.getAdminName());
            preparedStatement.setString(2, admin.getAdminName());
            preparedStatement.setString(3, admin.getAdminMobileNumber());
            preparedStatement.setString(4, admin.getAdminMailid());
            preparedStatement.setString(5, admin.getAdminRole());
            preparedStatement.setString(6, admin.getPassword());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static Admin getProfile(Connection connection, Admin admin) throws SQLException {
        String profEmail = profile.get(0);
        String profPass = profile.get(1);

        query = "SELECT * FROM admin WHERE mailId = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profEmail);
        preparedStatement.setString(2, profPass);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            admin.setAdminId(resultSet.getString("adminId"));
            admin.setAdminName(resultSet.getString("adminName"));
            admin.setAdminMobileNumber(resultSet.getString("mobileNumber"));
            admin.setAdminMailid(profEmail);
            admin.setAdminRole(resultSet.getString("role"));
            admin.setPassword(profPass);
        }
        return admin;
    }

    public static boolean updateProfile(Connection connection, Admin admin) throws SQLException {
        String profileUpdateQueryAdmin = "UPDATE admin SET adminName = ?, mobileNumber = ?, mailId = ? WHERE mailId = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryAdmin);
        preparedStatement.setString(1, admin.getAdminName());
        preparedStatement.setString(2, admin.getAdminMobileNumber());
        preparedStatement.setString(3, admin.getAdminMailid());
        preparedStatement.setString(4, profile.get(0));
        preparedStatement.setString(5, profile.get(1));

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkPassword(Admin admin) {
        return admin.getPassword().equals(profile.get(1));
    }

    public static boolean changePassword(Connection connection, Admin admin) throws SQLException {
        String passwordChangeQuery = "UPDATE admin SET password = ? WHERE password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
            preparedStatement.setString(1, admin.getPassword());
            preparedStatement.setString(2, profile.get(1));

            profile.set(1, admin.getPassword());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static void clearProfile() {
        profile.clear();
    }
}
