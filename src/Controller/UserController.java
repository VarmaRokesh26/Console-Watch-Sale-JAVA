package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import DAO.*;
import Model.*;
import View.*;

public class UserController {
    
    public static boolean insertInuser(Connection connection, UserDAO signUp) throws SQLException {
        return UserDAOImpl.insertUserDetails(connection, signUp);
    }

    public  static void performLogin(Connection con, UserDAO login, String[] args, Scanner sc) throws SQLException {
        if (AdminDAOImpl.checkAdminDetails(con, login))
            AdminView.adminView(con, args, sc, 0);
        else if (UserDAOImpl.checkUserDetails(con, login))
            UserView.userView(con, args, sc, 0);
        else if (DealerDAOImpl.checkDealerDetails(con, login))
            DealerView.dealerView(con, args, sc, 0);
        else
            System.out.println("Invalid Combinations");
    }

    public static void performSignUp(Connection con, UserDAO signUp) throws SQLException{
        UserController.insertInuser(con, signUp);
        System.out.println("Signed Success");
    }

    public static List<WatchDAO> displayWatches(Connection connection, WatchDAO watch, int entry) throws SQLException {
        return WatchDAOImpl.displayWatches(connection, watch, entry);
    } 

    public static boolean placeOrder(Connection connection, OrderDAO order) throws SQLException {
        return WatchDAOImpl.placeOrder(connection, order);
    }

    public static String generateOrderId(Connection connection) throws SQLException {
        return UIDGenerator.IdGenerator(connection, "orders");
    }

    public static UserDAO getUserId(Connection connection) throws SQLException {
        return UserDAOImpl.userIdForCartOrOrder(connection);
    }

    public static boolean checkWatchId(Connection connection, WatchDAO watch) throws SQLException {
        return WatchDAOImpl.checkWatchId(connection, watch);
    }

    public static String generateCartID(Connection connection) throws SQLException {
        return UIDGenerator.IdGenerator(connection, "cart");
    }

    public static boolean addToCart(Connection connection, CartDAO cart) throws SQLException {
        return CartDAOImpl.insertInCart(connection, cart);
    }

}
