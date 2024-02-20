package Controller;

import java.sql.Connection;
import java.sql.SQLException;

import Model.Watch;
import Model.DataAccessObject.WatchDAO;

public class AdminController {

    public static boolean deleteWatch(Connection connection, Watch watch) throws SQLException {
        return WatchDAO.deleteWatch(connection, watch);
    }

    public static boolean updateWatch(Connection connection, Watch watch) throws SQLException 
    {
        return WatchDAO.updateWatch(connection, watch);
    }

    public static boolean deletemanyWatches(Connection con) throws SQLException {
        return WatchDAO.deleteWatchByNOS(con);
    }

    // public static List<Watches> displayWatches(Connection connection) {

    // }

}
