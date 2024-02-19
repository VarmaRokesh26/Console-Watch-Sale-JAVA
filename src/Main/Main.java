package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Model.AdminFunction;
import Model.Connectivity;
import Model.User;
import View.AdminView;
import View.UserView;

public class Main {

    private static int loginOrSignUp;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        Connection con = Connectivity.getInstance().getConnection(); // Obtain the connection

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

    private static void performLogin(Connection con, User login, String[] args) {
        try {
            if (AdminFunction.checkAdminDetails(con, login.getEmailId(), login.getPassword()))
                AdminView.adminInterface(con, args, sc, 0);
            // else if (CheckFromDB.checkUserDetails(con, login.getEmailId(), login.getPassword()))
            //     UserInterface.userInterface(con, args, sc, 0);
            // else if (CheckFromDB.checkCourierServiceDetails(con, login.getEmailId(), login.getPassword()))
            //     CourierServiceInterface.courierServiceInterface(con, args, sc, 0);
            // else if (CheckFromDB.checkDealerDetails(con, login.getEmailId(), login.getPassword()))
            //     DealerInterface.dealerInterface(con, args, sc, 0);
            // else
                System.out.println("Invalid Combinations");
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.toString());
        }
        // System.out.println("Hi");
    }

    private static void performSignUp(Connection con, User signUp) {

        // if (con != null) {
        //     try {
        //         InsertInDB.insertUserDetails(con, signUp.getUserId(), signUp.getUserName(), signUp.getMobileNumber(),
        //                 signUp.getEmailId(),
        //                 signUp.getAddress(), signUp.getPassword());
        //     } catch (Exception e) {
        //         System.out.println(e.toString());
        //     }
        // }
        System.out.println("Signed Success");
    }
}
