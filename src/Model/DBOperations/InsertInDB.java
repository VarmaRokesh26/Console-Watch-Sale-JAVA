package Model.DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertInDB {

    private static int rowsAffected;
    private static String userIdString;

    // Insert Watches
    public static void insertNewWatch(Connection connection, String watchId, String name, String brand, double price,
            String description, int numberOfStocks) throws SQLException {
        String insertQuery = "INSERT INTO watches (watchId, seriesName, brand, price, description, numberOfStocks) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, watchId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, brand);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, description);
            preparedStatement.setInt(6, numberOfStocks);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("<---Data Inserted--->");
            } else {
                System.out.println("Data haven't Inserted");
            }
        }
    }

    // Inserting Logic for new Admin
    public static void insertAdminDetails(Connection connection, String adminId, String adminName, String mobileNumber, String mailId,
            String adminRole, String password) throws SQLException {
        String insertQuery = "INSERT INTO admin (adminId, adminName, mobileNumber, mailId, role, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, adminId);
            preparedStatement.setString(2, adminName);
            preparedStatement.setString(3, mobileNumber);
            preparedStatement.setString(4, mailId);
            preparedStatement.setString(5, adminRole);
            preparedStatement.setString(6, password);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Admin Added succesfully!!");
            }
        }
    }

    // Inserting Logic for New User
    public static void insertUserDetails(Connection connection, String userId, String userName, String mobileNumber, String emailId,
            String address, String password) throws SQLException {

        String insertQuery = "INSERT INTO user (userId, userName, mobileNumber, emailId, address, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, mobileNumber);
            preparedStatement.setString(4, emailId);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, password);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("\n<--Signed Up succesfully!!-->");
        }
    }

    // Insert Logic for New Dealer
    public static void insertNewDealerDetails(Connection connection, String dealerId, String dealername, String dealerLocation,
            String contactNumber, String dealerMailId, String dealerPassword) throws SQLException {
        String insertQuery = "INSERT INTO dealer (dealerId, dealerName, location, contactNumber, dealerMailId, dealerPassword) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
           
            preparedStatement.setString(1, dealerId);
            preparedStatement.setString(2, dealername);
            preparedStatement.setString(3, dealerLocation);
            preparedStatement.setString(4, contactNumber);
            preparedStatement.setString(5, dealerMailId);
            preparedStatement.setString(6, dealerPassword);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("Dealer's Detail add Successfully");
        }
    }

    // Insert Logic for New Courier Service
    public static void insertNewCourierServiceDetails(Connection connection, String courierServiceId, String courierServiceName,
            String courierServiceNumber, String courierServicemailId, String courierServicePassword)
            throws SQLException {
        String insertQuery = "INSERT INTO courierservice (courierServiceId, courierServiceName, contactNumber, courierServiceMailId, cSpassword) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            
            preparedStatement.setString(1, courierServiceId);
            preparedStatement.setString(2, courierServiceName);
            preparedStatement.setString(3, courierServiceNumber);
            preparedStatement.setString(4, courierServicemailId);
            preparedStatement.setString(5, courierServicePassword);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("<---Courier Services Details are Added Successfully--->");
        }
    }

    // Users selected item will be added in the cart
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
            else
                System.out.println("No Items in The cart");
        }
        return false;
    }
}
