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

    private static String id;
    private static String name;
    private static String brand;
    private static double price;
    private static String description;
    private static int numberOfStocks;
    private static String dealerId;

    private static String orderId;
    private static String userId;
    private static String watchId;
    private static String orderDate;
    private static String deliveryDate;
    private static int quantity;
    private static String paymentMode;
    private static String status;

    private static String dealerName;

    // Display Watches
    public static void selectAllWatches(Connection connection, String watchId) throws SQLException {
        String selectQuery = "SELECT * FROM watches";
        String dealerName = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                id = resultSet.getString("watchId");
                name = resultSet.getString("seriesName");
                brand = resultSet.getString("brand");
                price = resultSet.getDouble("price");
                description = resultSet.getString("description");
                numberOfStocks = resultSet.getInt("numberOfStocks");
                dealerId = resultSet.getString("dealerId");

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
            System.out.println("-------------------------------------------------------------------");
        }
    }

    public static String[] viewOrders(Connection connection) throws SQLException {
        query = "SELECT * FROM orders";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                orderId = resultSet.getString("orderId");
                watchId = resultSet.getString("watchId");
                orderDate = resultSet.getString("orderDate");
                deliveryDate = resultSet.getString("deliveryDate");
                quantity = resultSet.getInt("quantity");
                price = resultSet.getDouble("totalAmount");
                status = resultSet.getString("status");
                res = orderId + "_" + watchId + "_" + orderDate + "_" + deliveryDate + "_" + quantity + "_" + price
                        + "_" + status;
                UserInterface.orderHistory(res.split("_"));
            }
            System.out.println("-------------------------------------------------------------------");
        }
        return res.split("_");
    }

    public static boolean viewOrderForRespectiveDealer(Connection connection) throws SQLException {
        query = "SELECT * FROM orders WHERE status != ?";
        String dealerIdForCheck = CheckFromDB.getDealerId(connection);
        String checkStatus = "Delivered";
        boolean ordersFound = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, checkStatus);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ordersFound = true;

                    orderId = resultSet.getString("orderId");
                    userId = resultSet.getString("userId");
                    dealerId = resultSet.getString("dealerId");
                    watchId = resultSet.getString("watchId");
                    orderDate = resultSet.getString("orderDate");
                    deliveryDate = resultSet.getString("deliveryDate");
                    quantity = resultSet.getInt("quantity");
                    price = resultSet.getDouble("totalAmount");
                    paymentMode = resultSet.getString("paymentMode");
                    status = resultSet.getString("status");

                    res = orderId + "_" + userId + "_" + dealerId + "_" + watchId + "_" + orderDate + "_"
                            + deliveryDate
                            + "_" + quantity
                            + "_" + price + "_" + paymentMode + "_" + status;
                    if(dealerIdForCheck.equals(dealerId))
                        DealerInterface.showOrderDetails(res.split("_"));
                    else 
                        CourierServiceInterface.showOrderDetails(res.split("_"));
                }
            }
        }
        return ordersFound;
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
                    dealerId = resultSet.getString("dealerId");
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

                id = resultSet.getString("watchId");
                name = resultSet.getString("seriesName");
                brand = resultSet.getString("brand");
                price = resultSet.getDouble("price");
                description = resultSet.getString("description");
                numberOfStocks = resultSet.getInt("numberOfStocks");
                dealerId = resultSet.getString("dealerId");

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
            System.out.println("-------------------------------------------------------------------");
        }
        return res;
    }

    // Specific Detail about Watch is to Clearly mention the required watch for the
    // user
    public static String specificWatchDetail(Connection connection, String watchId, int k) throws SQLException {

        String query = "SELECT * FROM watches WHERE watchId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, watchId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getString("watchId");
                    name = resultSet.getString("seriesName");
                    brand = resultSet.getString("brand");
                    price = resultSet.getDouble("price");
                    description = resultSet.getString("description");
                    numberOfStocks = resultSet.getInt("numberOfStocks");
                    dealerId = resultSet.getString("dealerId");

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
                        System.out.println("-------------------------------------------------------------------");
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
                System.out.println("-------------------------------------------------------------------");
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

    public static void displayDealersWatches(Connection connection) throws SQLException {

        dealerId = CheckFromDB.getDealerId(connection);
        query = "SELECT * FROM watches WHERE dealerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dealerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getString("watchId");
                    name = resultSet.getString("seriesName");
                    brand = resultSet.getString("brand");
                    price = resultSet.getDouble("price");
                    description = resultSet.getString("description");
                    numberOfStocks = resultSet.getInt("numberOfStocks");

                    DealerInterface.displayWatches(id, name, brand, price, description, numberOfStocks);
                }
            }
        }

    }

    public static void finishedOrder(Connection connection, String status) throws SQLException {
        query = "SELECT * FROM orders WHERE status = ?";
        String dealerIdCheck = CheckFromDB.getDealerId(connection);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderId = resultSet.getString("orderId");
                    userId = resultSet.getString("userId");
                    dealerId = resultSet.getString("dealerId");
                    watchId = resultSet.getString("watchId");
                    orderDate = resultSet.getString("orderDate");
                    deliveryDate = resultSet.getString("deliveryDate");
                    quantity = resultSet.getInt("quantity");
                    price = resultSet.getDouble("totalAmount");
                    paymentMode = resultSet.getString("paymentMode");
                    status = resultSet.getString("status");

                    res = orderId + "_" + userId + "_" + dealerId + "_" + watchId + "_" + orderDate + "_"
                            + deliveryDate
                            + "_" + quantity
                            + "_" + price + "_" + paymentMode + "_" + status;
                    if(dealerId.equals(dealerIdCheck)) {
    
                        DealerInterface.showOrderDetails(res.split("_"));
                    } else {
                        CourierServiceInterface.showOrderDetails(res.split("_"));
                    }
                }
            }
        }

    }

}