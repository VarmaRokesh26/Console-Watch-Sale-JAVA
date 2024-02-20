package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import DAO.*;
import Model.AdminDAOImpl;
import Model.DealerDAOImpl;
import Model.UserDAOImpl;
import Model.WatchDAOImpl;
import View.AdminView;
import View.DealerView;
import View.UserView;

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

    public static List<WatchDAO> displayWatches(Connection connection, WatchDAO watch) throws SQLException {
        return WatchDAOImpl.displayWatches(connection, 1);
    } 

}
