package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import DAO.OrderDAO;
import DAO.WatchDAO;

public class WatchDAOImpl {

    private static int rowsAffected;

    // Insert Watches
    public static boolean insertNewWatch(Connection connection, WatchDAO watch) throws SQLException {
        String insertQuery = "INSERT INTO watches (watchId, seriesName, brand, price, description, numberOfStocks, dealerId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, watch.getWatchId());
            preparedStatement.setString(2, watch.getName());
            preparedStatement.setString(3, watch.getBrand());
            preparedStatement.setDouble(4, watch.getPrice());
            preparedStatement.setString(5, watch.getDescription());
            preparedStatement.setInt(6, watch.getNumberOfStocks());
            preparedStatement.setString(7, watch.getDealerId());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Update Watches
    public static boolean updateWatch(Connection connection, WatchDAO watch) throws SQLException {
        String updateQuery = "UPDATE watches SET seriesName=?, brand=?, price=?, description=?, numberOfStocks=?, dealerId = ? WHERE watchId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, watch.getName());
            preparedStatement.setString(2, watch.getBrand());
            preparedStatement.setDouble(3, watch.getPrice());
            preparedStatement.setString(4, watch.getDescription());
            preparedStatement.setInt(5, watch.getNumberOfStocks());
            preparedStatement.setString(6, watch.getDealerId());
            preparedStatement.setString(7, watch.getWatchId());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Delete Watch
    public static boolean deleteWatch(Connection connection, WatchDAO watch) throws SQLException {
        String deleteQuery = "DELETE FROM watches WHERE watchId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, watch.getWatchId());

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                return true;
            } else {
                System.out.println("<---Nothnig is there to delete--->");
                return false;
            }
        }
    }

    // Delete all records where number_of_stocks is zero
    public static boolean deleteWatchByNOS(Connection connection) throws SQLException {
        String deleteQuery = "DELETE FROM watches WHERE numberOfStocks = 0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                return true;
            else
                return false;
        }
    }

    // display Watches From DB
    public static List<WatchDAO> displayWatches(Connection connection, WatchDAO watch, int entry) throws SQLException {
        String query;
        if (entry == 0) {
            query = "SELECT * FROM watches";
        } else if (entry == 1) {
            query = "SELECT * FROM watches WHERE numberOfStocks > 0";
        } else {
            query = "SELECT * FROM watches WHERE watchId = ?";
        }

        List<WatchDAO> list = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        if (entry != 1 && entry != 0)
            preparedStatement.setString(1, watch.getWatchId());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            WatchDAO watchItem = new WatchDAO();
            watchItem.setWatchId(resultSet.getString(1));
            watchItem.setName(resultSet.getString(2));
            watchItem.setBrand(resultSet.getString(3));
            watchItem.setPrice(resultSet.getDouble(4));
            watchItem.setDescription(resultSet.getString(5));
            watchItem.setNumber_of_stocks(resultSet.getInt(6));
            watchItem.setDealerId(resultSet.getString(7));

            list.add(watchItem);
        }
        return list;
    }

    public static boolean placeOrder(Connection connection, OrderDAO order) throws SQLException {

        String orderQuery = "INSERT INTO orders(orderId, userId, dealerId, watchId, quantity, totalAmount, paymentMode, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String deliveryDateQuery = "UPDATE orders SET deliveryDate = DATE_ADD(orderDate, INTERVAL 7 DAY) WHERE orderId = ?";
        String stockDecrease = "UPDATE watches SET numberOfStocks = numberOfStocks - ? WHERE watchId = ?";

        PreparedStatement orderStatement = connection.prepareStatement(orderQuery);

        orderStatement.setString(1, order.getOrderId());
        orderStatement.setString(2, order.getUserId());
        orderStatement.setString(3, order.getDealerId());
        orderStatement.setString(4, order.getWatchId());
        orderStatement.setInt(5, order.getQuantity());
        orderStatement.setDouble(6, order.getTotalAmount());
        orderStatement.setString(7, order.getPaymentMode());
        orderStatement.setString(8, order.getStatus());

        rowsAffected = orderStatement.executeUpdate();

        try (PreparedStatement deliveryPreparedStatement = connection.prepareStatement(deliveryDateQuery)) {
            deliveryPreparedStatement.setString(1, order.getOrderId());
            deliveryPreparedStatement.executeUpdate();
        }

        try (PreparedStatement stockPreparedStatement = connection.prepareStatement(stockDecrease)) {
            stockPreparedStatement.setInt(1, order.getQuantity());
            stockPreparedStatement.setString(2, order.getWatchId());
            stockPreparedStatement.executeUpdate();
        }

        return rowsAffected > 0;
    }

    public static boolean checkWatchId(Connection connection, WatchDAO watch) throws SQLException {
        String checkIdQuery = "SELECT watchId FROM watches WHERE watchId = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(checkIdQuery);
        preparedStatement.setString(1, watch.getWatchId());

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

}
