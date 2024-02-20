package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.UserController;
import DAO.User;
import Model.AdminDAO;
import Model.Connectivity;
import Model.DealerDAO;
import Model.UserDAO;
import View.AdminView;
import View.DealerView;
import View.UserView;
import Model.DataAccessObject.*;

public class Main {

    private static int loginOrSignUp;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        Connection con = Connectivity.getInstance().getConnection();

        while (true) {
            System.out.println("-----------------------------------------------------------------");
            System.out.print("For New User Type 0 or Type 1 if already has an account? : ");
            loginOrSignUp = sc.nextInt();
            sc.nextLine();

            if (loginOrSignUp == 1) {
                // Login logic
                User login = UserView.getLoginDetails(sc);
                System.out.println("-----------------------------------------------------------------");
                performLogin(con, login, args);
            } else if (loginOrSignUp == 0) {
                // Sign-up logic
                User signUp = UserView.getSignUpDetails(con, sc);
                System.out.println("-----------------------------------------------------------------");
                performSignUp(con, signUp);

                loginOrSignUp = 1;
            } else {
                System.out.println("Enter only 0 and 1-->>!!!");
            }
        }
    }

    private static void performLogin(Connection con, User login, String[] args) throws SQLException {
        if (AdminDAO.checkAdminDetails(con, login))
            AdminView.adminView(con, args, sc, 0);
        else if (UserDAO.checkUserDetails(con, login))
            UserView.userView(con, args, sc, 0);
        else if (DealerDAO.checkDealerDetails(con, login))
            DealerView.dealerView(con, args, sc, 0);
        else
            System.out.println("Invalid Combinations");
    }

    private static void performSignUp(Connection con, User signUp) throws SQLException{
        UserController.insertInuser(con, signUp);
        System.out.println("Signed Success");
    }
}
