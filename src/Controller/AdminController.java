package Controller;

import java.sql.Connection;
import java.sql.SQLException;

import Model.Watch;
import Model.DataAccessObject.WatchFunction;

public class AdminController {

    public static boolean deleteWatch(Connection connection, Watch watch) throws SQLException {
        return WatchFunction.deleteWatch(connection, watch);
    }

    public static boolean updateWatch(Connection connection, Watch watch) throws SQLException 
    {
        return WatchFunction.updateWatch(connection, watch);
    }

    public static boolean deletemanyWatches(Connection con) throws SQLException {
        return WatchFunction.deleteWatchByNOS(con);
    }

}
