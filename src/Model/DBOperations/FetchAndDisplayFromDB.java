package Model.DBOperations;

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
    public static void selectAllWatches(Connection connection, String watchId) throws SQLException {
        String selectQuery = "SELECT * FROM watches";
        String dealerName = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("watchId");
                String name = resultSet.getString("seriesName");
                String brand = resultSet.getString("brand");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int numberOfStocks = resultSet.getInt("numberOfStocks");
                String dealerId = resultSet.getString("dealerId");

                String query = "SELECT dealerName FROM dealer WHERE dealerId = ?";
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(query)) {
                    preparedStatement1.setString(1, dealerId);
                    try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                        while (resultSet1.next()) {
                            dealerName = resultSet1.getString("dealerName");
                        }
                        if ("-1".equals(watchId)) {
                            AdminInterface.displayWatches(id, name, brand, price, description, numberOfStocks, dealerId,
                                    dealerName);
                        }
                    }
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

                if (entry == 0) {
                    res = orderId + "_" + userId + "_" + dealerId + "_" + watchId + "_" + orderDate + "_" + deliveryDate
                            + "_" + quantity
                            + "_" + price + "_" + paymentMode + "_" + status;
                    DealerInterface.showOrderDetails(res.split("_"));
                }
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
                    if (res.isEmpty())
                        System.out.println("No Admins");
                    AdminInterface.viewProfile(res.split("_"));
                } else if (slNo == 3) {
                    String dealerId = resultSet.getString("dealerId");
                    String name = resultSet.getString("dealerName");
                    String location = resultSet.getString("location");
                    String number = resultSet.getString("contactNumber");
                    String mailId = resultSet.getString("dealerMailId");

                    res = dealerId + "_" + name + "_" + location + "_" + number + "_" + mailId;
                    if (res.isEmpty())
                        System.out.println("No Dealers");
                    DealerInterface.displayDealers(res.split("_"));
                } else if (slNo == 4) {
                    String courierServiceId = resultSet.getString("courierServiceId");
                    String name = resultSet.getString("courierServiceName");
                    String number = resultSet.getString("contactNumber");
                    String mailId = resultSet.getString("courierServiceMailId");
                    res = courierServiceId + "_" + name + "_" + number + "_" + mailId;
                    if (res.isEmpty())
                        System.out.println("No Courier Services");
                    CourierServiceInterface.courierDetailDisplay(res.split("_"));
                }
            }
        }
    }

    // Display Watches if it is available
    public static String selectAllWatchesIfAvailable(Connection connection) throws SQLException {
        String query = "SELECT * FROM watches WHERE numberOfStocks > 0";
        String res = null;
        String dealerName = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                String id = resultSet.getString("watchId");
                String name = resultSet.getString("seriesName");
                String brand = resultSet.getString("brand");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int numberOfStocks = resultSet.getInt("numberOfStocks");
                String dealerId = resultSet.getString("dealerId");

                String dealerQuery = "SELECT dealerName FROM dealer WHERE dealerId = ?";
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(dealerQuery)) {
                    preparedStatement1.setString(1, dealerId);
                    try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                        while (resultSet1.next()) {
                            dealerName = resultSet1.getString("dealerName");
                        }
                    }
                }

                if (numberOfStocks != 0) {
                    UserInterface.displayWatches(id, name, brand, price, description, numberOfStocks, dealerName);
                }
            }
            System.out.println("******************************************************************");
        }
        return res;
    }

    // Specific Detail about Watch is to Clearly mention the required watch for the
    // user
    public static String specificWatchDetail(Connection connection, String watchId, int k) throws SQLException {
        
        String query = "SELECT * FROM watches WHERE watchId = ?";
        String res = "";
        String dealerName = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, watchId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("watchId");
                    String name = resultSet.getString("seriesName");
                    String brand = resultSet.getString("brand");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");
                    int numberOfStocks = resultSet.getInt("numberOfStocks");
                    String dealerId = resultSet.getString("dealerId");

                    String dealerQuery = "SELECT dealerName FROM dealer WHERE dealerId = ?";
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(dealerQuery)) {
                        preparedStatement1.setString(1, dealerId);
                        try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                            while (resultSet1.next()) {
                                dealerName = resultSet1.getString("dealerName");
                            }
                        }
                    }
                    if (numberOfStocks != 0 && k == 0) {
                        UserInterface.displayWatches(id, name, brand, price, description, numberOfStocks,
                                dealerName);
                        System.out.println("******************************************************************");
                    } else {
                        res += watchId + "_" + name + "_" + price + "_" + dealerId;
                    }
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
        String profEmail = profile.get(0);
        String profPass = profile.get(1);
        String profRole = profile.get(2);

        if (profRole.equals("admin"))
            query = "SELECT * FROM admin WHERE mailId = ? AND password = ?";
        else if (profRole.equals("user"))
            query = "SELECT * FROM user WHERE emailId = ? AND password = ?";
        else if (profRole.equals("dealer"))
            query = "SELECT * FROM dealer WHERE dealerMailId = ? AND dealerPassword = ?";
        else if (profRole.equals("courierservice"))
            query = "SELECT * FROM courierservice WHERE courierServiceMailId = ? AND cSPassword = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, profEmail);
            preparedStatement.setString(2, profPass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (profRole.equals("admin")) {
                        String id = resultSet.getString("adminId");
                        String profName = resultSet.getString("adminName");
                        String profmN = resultSet.getString("mobileNumber");
                        String role = resultSet.getString("role");
                        res = id + "_" + profName + "_" + profmN + "_" + profEmail + "_" + role;
                    } else if (profRole.equals("user")) {
                        String id = resultSet.getString("userId");
                        String profName = resultSet.getString("userName");
                        String profmN = resultSet.getString("mobileNumber");
                        String profAdd = resultSet.getString("address");
                        res = id + "_" + profName + "_" + profmN + "_" + profEmail + "_" + profAdd;
                    } else if (profRole.equals("dealer")) {
                        String id = resultSet.getString("dealerId");
                        String profName = resultSet.getString("dealerName");
                        String profmN = resultSet.getString("contactNumber");
                        String profAdd = resultSet.getString("location");
                        res = id + "_" + profName + "_" + profmN + "_" + profEmail + "_" + profAdd;
                    } else if (profRole.equals("courierservice")) {
                        String id = resultSet.getString("courierServiceId");
                        String profName = resultSet.getString("courierServiceName");
                        String profmN = resultSet.getString("contactNumber");
                        res = id + "_" + profName + "_" + profmN + "_" + profEmail;
                    }
                }
            }
        }

        return res;
    }

}