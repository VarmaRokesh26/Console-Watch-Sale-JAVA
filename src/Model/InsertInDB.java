package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertInDB {

    private static int rowsAffected;
    private static String userIdString;

    // Insert Watches
    public static void insertNewWatch(Connection connection, String name, String brand, double price,
            String description, int number_of_stocks) throws SQLException {
        String insertQuery = "INSERT INTO watches (seriesName, brand, price, description, numberOfStocks) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, brand);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, number_of_stocks);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("<---Data Inserted--->");
            }
        }
    }

    // Inserting Logic for new Admin
    public static void insertAdminDetails(Connection connection, String userName, String mobileNumber, String mailid,
            String adminRole,
            String passCode) throws SQLException {
        String insertQuery = "INSERT INTO admin (adminName, mobileNumber, mailId, role, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, mobileNumber);
            preparedStatement.setString(3, mailid);
            preparedStatement.setString(4, adminRole);
            preparedStatement.setString(5, passCode);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Admin Added succesfully!!");
            }
        }
    }

    // Inserting Logic for New User
    public static void insertUserDetails(Connection connection, String userName, String mobileNumber, String email,
            String address, String passCode) throws SQLException {

        String insertQuery = "INSERT INTO user (userName, mobileNumber, emailId, address, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, mobileNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, passCode);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("\n<--Signed Up succesfully!!-->");
        }
    }

    // Insert Logic for New Dealer
    public static void insertNewDealerDetails(Connection connection, String dealername, String dealerLocation,
            String contactNumber, String dealerMailId, String dealerPassword) throws SQLException {
        String insertQuery = "INSERT INTO dealer (dealerName, location, contactNumber, dealerMailId, dealerPassword) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, dealername);
            preparedStatement.setString(2, dealerLocation);
            preparedStatement.setString(3, contactNumber);
            preparedStatement.setString(4, dealerMailId);
            preparedStatement.setString(5, dealerPassword);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("Dealer's Detail add Successfully");
        }
    }

    // Insert Logic for New Courier Service
    public static void insertNewCourierServiceDetails(Connection connection, String courierServiceName,
            String courierServiceNumber, String courierServicemailId, String courierServicePassword)
            throws SQLException {
        String insertQuery = "INSERT INTO courierservice (courierServiceName, contactNumber, courierServiceMailId, cSpassword) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, courierServiceName);
            preparedStatement.setString(2, courierServiceNumber);
            preparedStatement.setString(3, courierServicemailId);
            preparedStatement.setString(4, courierServicePassword);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("<---Courier Services Details are Added Successfully--->");
        }
    }

    public static boolean insertInCart(Connection connection, String watchIdForCart, String cartDetails)
            throws SQLException {
        String cartQuery = "INSERT INTO cart (userId, cartDetails, watchId) VALUES (?, ?, ?)";
        userIdString = CheckFromDB.userIdForCartOrOrder(connection);

        try (PreparedStatement preparedStatement = connection.prepareStatement(cartQuery)) {
            preparedStatement.setString(1, userIdString);
            preparedStatement.setString(2, cartDetails);
            preparedStatement.setString(3, watchIdForCart);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;
        }
        return false;
    }

}
