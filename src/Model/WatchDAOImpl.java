package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
                return true;
            else
                return false;
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

    //display Watches From DB
    public static List<WatchDAO> displayWatches(Connection connecction, WatchDAO watch) throws SQLException {
        String query = "SELECT * FROM watches";

        List<WatchDAO> list = new ArrayList<>();

        PreparedStatement preparedStatement = connecction.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            watch.setWatchId(resultSet.getString(1));
            watch.setName(resultSet.getString(2));
            watch.setBrand(resultSet.getString(3));
            watch.setPrice(resultSet.getDouble(4));
            watch.setDescription(resultSet.getString(5));
            watch.setNumber_of_stocks(resultSet.getInt(6));

            list.add(watch);
        }
        return list;
    } 

}
