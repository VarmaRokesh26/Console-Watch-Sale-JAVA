package Model.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Watch;

public class WatchFunction {

    private static int rowsAffected;

    // Update Watches
    public static boolean updateWatch(Connection connection, Watch watch) throws SQLException {
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
    public static boolean deleteWatch(Connection connection, Watch watch) throws SQLException {
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

}
