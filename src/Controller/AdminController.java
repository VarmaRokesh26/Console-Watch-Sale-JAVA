package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.*;
import Model.*;

public class AdminController {

    public static boolean deleteWatch(Connection connection, WatchDAO watch) throws SQLException {
        return WatchDAOImpl.deleteWatch(connection, watch);
    }

    public static boolean updateWatch(Connection connection, WatchDAO watch) throws SQLException {
        return WatchDAOImpl.updateWatch(connection, watch);
    }

    public static boolean deletemanyWatches(Connection con) throws SQLException {
        return WatchDAOImpl.deleteWatchByNOS(con);
    }

    public static List<WatchDAO> displayWatches(Connection connection, WatchDAO watch) throws SQLException{
        return WatchDAOImpl.displayWatches(connection, watch, 0);
    }

    public static List<AdminDAO> displayAdmin(Connection connection) throws SQLException {
        return AdminDAOImpl.displayAdmin(connection);
    }

    public static boolean insertAdmin(Connection connection, AdminDAO admin) throws SQLException {
        return AdminDAOImpl.insertAdminDetails(connection, admin);
    }

    public static boolean insertWatch(Connection connection, WatchDAO watch) throws SQLException {
        return WatchDAOImpl.insertNewWatch(connection, watch);
    }

    public static boolean insertDealer(Connection connection, DealerDAO dealer) throws SQLException {
        return DealerDAOImpl.insertNewDealerDetails(connection, dealer);
    }

    public static AdminDAO adminProfile(Connection connection, AdminDAO admin) throws SQLException {
        if (admin == null) {
            admin = new AdminDAO(); // Instantiate admin if null
        }
        return AdminDAOImpl.getProfile(connection, admin);
    }

    public static boolean updateProfile(Connection connection, AdminDAO admin) throws SQLException {
        if(admin == null)
            admin = new AdminDAO();
        return AdminDAOImpl.updateProfile(connection, admin);
    }

    public static boolean checkPassword(AdminDAO admin) {
        return AdminDAOImpl.checkPassword(admin);
    }

    public static boolean updatePassword(Connection coneection, AdminDAO admin)  throws SQLException {
        return AdminDAOImpl.changePassword(coneection, admin);
    }

    public static void logOut(String[] args) throws SQLException {
		AdminDAOImpl.clearProfile();
	}
} 
