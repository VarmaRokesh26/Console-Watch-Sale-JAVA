package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import DAO.User;
import Model.AdminDAO;
import Model.DealerDAO;
import Model.UserDAO;
import View.AdminView;
import View.DealerView;
import View.UserView;

public class UserController {
    
    public static boolean insertInuser(Connection connection, User signUp) throws SQLException {
        return UserDAO.insertUserDetails(connection, signUp);
    }

    public  static void performLogin(Connection con, User login, String[] args, Scanner sc) throws SQLException {
        if (AdminDAO.checkAdminDetails(con, login))
            AdminView.adminView(con, args, sc, 0);
        else if (UserDAO.checkUserDetails(con, login))
            UserView.userView(con, args, sc, 0);
        else if (DealerDAO.checkDealerDetails(con, login))
            DealerView.dealerView(con, args, sc, 0);
        else
            System.out.println("Invalid Combinations");
    }

    public static void performSignUp(Connection con, User signUp) throws SQLException{
        UserController.insertInuser(con, signUp);
        System.out.println("Signed Success");
    }

}
