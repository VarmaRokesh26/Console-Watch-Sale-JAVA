package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WatchFunction {
    
    private static int rowsAffected;

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

}
