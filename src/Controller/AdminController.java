package Controller;

import java.sql.Connection;
import java.sql.SQLException;

import Model.DataAccessObject.WatchFunction;

public class AdminController {
    
    public static boolean deleteWatch(Connection connection, String watchId) throws SQLException {
        WatchFunction.deleteWatch(connection, watchId);
        return true;
    }

}
