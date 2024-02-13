package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import View.AdminInterface;
import View.CourierServiceInterface;
import View.DealerInterface;
import View.UserInterface;

public class FetchAndDisplayFromDB {

    private static String res;
    private static String query;
    private static String userIdString;
    private static List<String> profile = CheckFromDB.profileList();

    // Display Watches
    public static void selectAllWatches(Connection connection, String wId) throws SQLException {
        String selectQuery = "SELECT * FROM watches";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("watchId");
                String name = resultSet.getString("seriesName");
                String brand = resultSet.getString("brand");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int numberOfStocks = resultSet.getInt("numberOfStocks");

                if (id.equals(wId)) {
                    break;
                }
                if ("-1".equals(wId)) {
                    AdminInterface.displayWatches(id, name, brand, price, description, numberOfStocks);
                }
            }
            System.out.println("******************************************************************");
        }
    }

    public static String[] viewOrders(Connection connection, int entry) throws SQLException {
        query = "SELECT * FROM orders";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String orderId = resultSet.getString("orderId");
                String userId = resultSet.getString("userId");
                String dealerId = resultSet.getString("dealerId");
                String watchId = resultSet.getString("watchId");
                String orderDate = resultSet.getString("orderDate");
                String deliveryDate = resultSet.getString("deliveryDate");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("totalAmount");
                String paymentMode = resultSet.getString("paymentMode");
                String status = resultSet.getString("status");

                if (entry == 0)
                    res = orderId + "_" + userId + "_" + dealerId + "_" + watchId + "_" + orderDate + "_" + deliveryDate
                            + "_" + quantity
                            + "_" + price + "_" + paymentMode + "_" + status;
                else {
                    res = orderId + "_" + watchId + "_" + orderDate + "_" + deliveryDate + "_" + quantity + "_" + price
                            + "_" + status;
                    UserInterface.orderHistory(res.split("_"));
                }
            }
            System.out.println("____________________________________________");
        }
        return res.split("_");
    }

    public static void displayAdminDealerCourierDB(Connection connection, int slNo) throws SQLException {
        if (slNo == 2)
            query = "SELECT * FROM admin";
        else if (slNo == 3)
            query = "SELECT * FROM dealer";
        else if (slNo == 4)
            query = "SELECT * FROM courierservice";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                if (slNo == 2) {
                    String adminId = resultSet.getString("adminId");
                    String name = resultSet.getString("adminName");
                    String number = resultSet.getString("mobileNumber");
                    String mail = resultSet.getString("mailId");
                    String role = resultSet.getString("role");

                    res = adminId + "_" + name + "_" + number + "_" + mail + "_" + role;
                    AdminInterface.profile(res.split("_"));
                } else if (slNo == 3) {
                    String dealerId = resultSet.getString("dealerId");
                    String name = resultSet.getString("dealerName");
                    String location = resultSet.getString("location");
                    String number = resultSet.getString("contactNumber");
                    String mailId = resultSet.getString("dealerMailId");

                    res = dealerId + "_" + name + "_" + location + "_" + number + "_" + mailId;
                    DealerInterface.displayDealers(res.split("_"));
                } else if (slNo == 4) {
                    String courierServiceId = resultSet.getString("courierServiceId");
                    String name = resultSet.getString("courierServiceName");
                    String number = resultSet.getString("contactNumber");
                    String mailId = resultSet.getString("courierServiceMailId");
                    res = courierServiceId + "_" + name + "_" + number + "_" + mailId;
                    CourierServiceInterface.courierDetailDisplay(res.split("_"));
                }
            }
        }
    }

    // Display Watches if it is available
    public static String selectAllWatchesIfAvailable(Connection connection) throws SQLException {
        query = "SELECT * FROM watches WHERE numberOfStocks > 0";
        String res = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                String id = resultSet.getString("watchId");
                String name = resultSet.getString("seriesName");
                String brand = resultSet.getString("brand");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int numberOfStocks = resultSet.getInt("numberOfStocks");
                if (numberOfStocks != 0) {
                    AdminInterface.displayWatches(id, name, brand, price, description, numberOfStocks);
                }
            }
            System.out.println("******************************************************************");
        }
        return res;
    }

    // Specific Detail about Watch is to Cearly mention the required watch for the
    // user
    public static String specificWatchDetail(Connection connection, String watchId, int k) throws SQLException {
        String res = "";
        query = "SELECT * FROM watches WHERE watchId = " + watchId;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("seriesName");
                String brand = resultSet.getString("brand");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int numberOfStocks = resultSet.getInt("numberOfStocks");

                if (numberOfStocks != 0 && k == 0) {
                    AdminInterface.displayWatches(watchId, name, brand, price, description, numberOfStocks);
                    System.out.println("******************************************************************");
                } else {
                    res += watchId + "_" + name + "_" + price;
                }
            }
        }
        return res;
    }

    // Display Cart
    public static void showMyCart(Connection connection) throws SQLException {
        String cartViewQuery = "SELECT cartDetails FROM cart WHERE userId = ?";
        userIdString = CheckFromDB.userIdForCartOrOrder(connection);
        try (PreparedStatement cartViewPreparedStatement = connection.prepareStatement(cartViewQuery)) {
            cartViewPreparedStatement.setString(1, userIdString);
            try (ResultSet cartResultSet = cartViewPreparedStatement.executeQuery()) {
                while (cartResultSet.next()) {
                    UserInterface.shoppingCart(cartResultSet.getString("cartDetails"));
                }
                System.out.println("____________________________________________");
            }
        }
    }

    // Profile Display for Both Admin and User
    public static String displayProfile(Connection connection) throws SQLException {
        String res = "";
        String profEmail = profile.get(0);
        String profPass = profile.get(1);
        String profRole = profile.get(2);
        String query = "";

        if (profRole.equals("admindetails")) {
            query = "SELECT * FROM admin WHERE mailId = ? AND password = ?";
        } else if (profRole.equals("userdetails")) {
            query = "SELECT * FROM user WHERE emailId = ? AND password = ?";
        } else if (profRole.equals("dealer")) {
            query = "SELECT * FROM delear WHERE dealerMailId = ? AND password = ?";
        } else {
            query = "SELECT * FROM courierservice WHERE courierServiceMailId = ? AND cSPassword = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, profEmail);
            preparedStatement.setString(2, profPass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (profRole.equals("admin")) {
                        String profName = resultSet.getString("adminName");
                        String profmN = resultSet.getString("mobileNumber");
                        String role = resultSet.getString("role");
                        res = profName + "_" + profmN + "_" + profEmail + "_" + role;
                    } else if (profRole.equals("user")) {
                        String profName = resultSet.getString("userName");
                        String profmN = resultSet.getString("mobileNumber");
                        String profAdd = resultSet.getString("address");
                        res = profName + "_" + profmN + "_" + profEmail + "_" + profAdd;
                    } else if (profRole.equals("dealer")) {
                        String profName = resultSet.getString("dealerName");
                        String profmN = resultSet.getString("contactNumber");
                        String profAdd = resultSet.getString("location");
                        res = profName + "_" + profmN + "_" + profEmail + "_" + profAdd;
                    } else {
                        String profName = resultSet.getString("courierServiceName");
                        String profmN = resultSet.getString("contactNumber");
                        res = profName + "_" + profmN + "_" + profEmail;
                    }
                }
            }
        }

        return res;
    }

}