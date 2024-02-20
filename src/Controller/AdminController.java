package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.*;
import Model.*;

public class AdminController {

    public static boolean deleteWatch(Connection connection, Watch watch) throws SQLException {
        return WatchDAO.deleteWatch(connection, watch);
    }

    public static boolean updateWatch(Connection connection, Watch watch) throws SQLException {
        return WatchDAO.updateWatch(connection, watch);
    }

    public static boolean deletemanyWatches(Connection con) throws SQLException {
        return WatchDAO.deleteWatchByNOS(con);
    }

    public static List<Watch> displayWatches(Connection connection, Watch watch) throws SQLException{
        return WatchDAO.displayWatches(connection, watch);
    }

    public static List<Admin> displayAdmin(Connection connection, Admin admin) throws SQLException {
        return AdminDAO.displayAdmin(connection, admin);
    }

    public static boolean insertAdmin(Connection connection, Admin admin) throws SQLException {
        return AdminDAO.insertAdminDetails(connection, admin);
    }

    public static boolean insertWatch(Connection connection, Watch watch) throws SQLException {
        return WatchDAO.insertNewWatch(connection, watch);
    }

    public static boolean insertDealer(Connection connection, Dealer dealer) throws SQLException {
        return DealerDAO.insertNewDealerDetails(connection, dealer);
    }

    public static Admin adminProfile(Connection connection, Admin admin) throws SQLException {
        if (admin == null) {
            admin = new Admin(); // Instantiate admin if null
        }
        return AdminDAO.getProfile(connection, admin);
    }

    public static boolean updateProfile(Connection connection, Admin admin) throws SQLException {
        if(admin == null)
            admin = new Admin();
        return AdminDAO.updateProfile(connection, admin);
    }

    public static boolean checkPassword(Admin admin) {
        return AdminDAO.checkPassword(admin);
    }

    public static boolean updatePassword(Connection coneection, Admin admin)  throws SQLException {
        return AdminDAO.changePassword(coneection, admin);
    }

    public static void logOut(String[] args) throws SQLException {
		AdminDAO.clearProfile();
	}
} 
