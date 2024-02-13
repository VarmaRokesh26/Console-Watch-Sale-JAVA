package Model.DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UpdateInDB {

    private static int rowsAffected;
    private static String status;
    private static String userIdString;
    private static List<String> profile = CheckFromDB.profileList();

    // Update Watches
    public static void updateWatch(Connection connection, String seriesName, String brand, double price, String desc,
            int numberOfStocks, String watchId) throws SQLException {
        String updateQuery = "UPDATE watches SET serieseriesName=?, brand=?, price=?, description=?, numberOfStocks=? WHERE watchId=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, seriesName);
            preparedStatement.setString(2, brand);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, desc);
            preparedStatement.setInt(5, numberOfStocks);
            preparedStatement.setString(6, watchId);
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Updation Unsuccessfull.");
            }
        }
    }

    // Place Order By the User
    public static void placeOrder(Connection connection, String watchId, int quantity, double price, String paymentMode)
            throws SQLException {
        userIdString = CheckFromDB.userIdForCartOrOrder(connection);
        status = "Processed";
        String orderId = "";
        String orderQuery = "INSERT INTO orders(orderId, userId, dealerId, watchId, quantity, totalAmount, paymentMode, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String deliveryDateQuery = "UPDATE orders SET deliveryDate = DATE_ADD(orderDate, INTERVAL 7 DAY) WHERE orderId = ?";
        String stockDecrease = "UPDATE watches SET numberOfStocks = numberOfStocks - ? WHERE watchId = ?";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {

            orderStatement.setString(1, orderId);
            orderStatement.setString(2, userIdString);
            orderStatement.setString(4, watchId);
            orderStatement.setInt(5, quantity);
            orderStatement.setDouble(6, (price * quantity));
            orderStatement.setString(7, paymentMode);
            orderStatement.setString(8, status);

            rowsAffected = orderStatement.executeUpdate();
            if (rowsAffected != 0) {
                System.out.println("Order Placed Successfull!!");
                String orderIdQuery = "SELECT orderId FROM orders WHERE userId = ? AND watchId = ?";
                try (PreparedStatement orderIdSmt = connection.prepareStatement(orderIdQuery)) {
                    orderIdSmt.setString(1, userIdString);
                    orderIdSmt.setString(2, watchId);
                    try (ResultSet resultSet = orderIdSmt.executeQuery()) {
                        while (resultSet.next()) {
                            orderId = resultSet.getString("orderId");
                        }
                    }
                }
            }

            try (PreparedStatement deliveryPreparedStatement = connection.prepareStatement(deliveryDateQuery)) {
                deliveryPreparedStatement.setString(1, orderId);
                deliveryPreparedStatement.executeUpdate();
            }

            try (PreparedStatement stockPreparedStatement = connection.prepareStatement(stockDecrease)) {
                stockPreparedStatement.setInt(1, quantity);
                stockPreparedStatement.setString(2, watchId);
                stockPreparedStatement.executeUpdate();
            }
        }
    }

    // Update Profile for both User and Admin
    public static void updateProfile(Connection connection, String name, String mobileNumber, String emailId,
            String address) throws SQLException {
        if (profile.get(2).equals("admin")) {
            String profileUpdateQueryAdmin = "UPDATE admin SET adminName = ?, mobileNumber = ?, mailId = ? WHERE mailId = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryAdmin)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, mobileNumber);
                preparedStatement.setString(3, emailId);
                preparedStatement.setString(4, profile.get(0));
                preparedStatement.setString(5, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Update successful.");
                    profile.set(0, emailId);
                } else {
                    System.out.println("Updation Unsuccessfull.");
                }
            }
        } else if (profile.get(2).equals("user")) {
            String profileUpdateQueryUser = "UPDATE user SET userName = ?, mobileNumber = ?, emailId = ?, address = ? WHERE emailId = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryUser)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, mobileNumber);
                preparedStatement.setString(3, emailId);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, profile.get(0));
                preparedStatement.setString(6, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Update successful.");
                    profile.set(0, emailId);
                } else {
                    System.out.println("Updation Unsuccessfull.");
                }
            }
        } else if (profile.get(2).equals("dealer")) {
            String profileUpdateQueryDealer = "UPDATE dealer SET dealerName = ?, location = ?, contactNumber = ?, dealerMailId = ? WHERE dealerMailId = ? AND dealerPassword = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryDealer)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, mobileNumber);
                preparedStatement.setString(3, emailId);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, profile.get(0));
                preparedStatement.setString(6, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Update successful.");
                    profile.set(0, emailId);
                } else {
                    System.out.println("Updation Unsuccessfull.");
                }
            }
        } else if (profile.get(2).equals("courierservice")) {
            String profileUpdateQueryCourier = "UPDATE courierservice SET courierServiceName = ?, contactNumber = ?, courierServiceMailId = ? WHERE courierServiceMailId = ? AND cSPassword = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryCourier)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, mobileNumber);
                preparedStatement.setString(3, emailId);
                preparedStatement.setString(5, profile.get(0));
                preparedStatement.setString(6, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Update successful.");
                    profile.set(0, emailId);
                } else {
                    System.out.println("Updation Unsuccessfull.");
                }
            }
        }
    }

    // New Password Update Logic for Both Admin and User
    public static void changePassword(Connection connection, String newPassword) throws SQLException {
        if (profile.get(2).equals("admin")) {
            String passwordChangeQuery = "UPDATE admin SET password = ? WHERE password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password changed Successfully");
                    profile.set(1, newPassword);
                } else {
                    System.out.println("Password is not channged");
                }
            }
        } else if (profile.get(2).equals("userdetails")) {
            String passwordChangeQuery = "UPDATE user SET password = ? WHERE password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password changed Successfully");
                    profile.set(1, newPassword);
                } else {
                    System.out.println("Password is not channged");
                }
            }
        } else if (profile.get(2).equals("dealer")) {
            String passwordChangeQuery = "UPDATE dealer SET dealerPassword = ? WHERE dealerPassword = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password changed Successfully");
                    profile.set(1, newPassword);
                } else {
                    System.out.println("Password is not channged");
                }
            }
        } else if (profile.get(2).equals("courierservice")) {
            String passwordChangeQuery = "UPDATE courierservice SET cSPassword = ? WHERE cSPpassword = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, profile.get(1));

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password changed Successfully");
                    profile.set(1, newPassword);
                } else {
                    System.out.println("Password is not channged");
                }
            }
        }
    }
}