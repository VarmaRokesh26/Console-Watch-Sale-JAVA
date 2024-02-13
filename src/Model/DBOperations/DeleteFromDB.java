package Model.DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DeleteFromDB {

    private static int rowsAffected;
    private static List<String> profile = CheckFromDB.profileList();

    // Delete Watch
    public static void deleteWatch(Connection connection, String wId) throws SQLException {
        String deleteQuery = "DELETE FROM watches WHERE watchId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, wId);

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("--Data Deleted.--");
            else
                System.out.println("Nothnig is there to delete");
        }
    }

    // Delete all records where number_of_stocks is zero
    public static void deleteWatchByNOS(Connection connection) throws SQLException {
        String deleteQuery = "DELETE FROM watches WHERE numberOfStocks = 0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1)
                System.out.println("--Datum Deleted.--");
            else
                System.out.println("Not Deleted");
        }
    }

    // Clear the profile when Loging Out
    public static void clearProfile(Connection connection) {
        profile.clear();
    }

}
