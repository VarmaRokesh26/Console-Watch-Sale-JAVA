package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Model.*;
import Controller.*;
import View.*;

public class MainModule {

    private static int loginOrSignUp;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Connection con = Connectivity.connectivity();

        while (true) {
            System.out.println("-----------------------------------------------------------------");
            System.out.print("For New User Type 0 or Type 1 for Login : ");
            loginOrSignUp = sc.nextInt();
            sc.nextLine();

            if (loginOrSignUp == 1) {
                // Login logic
                Login login = UserModule.getLoginDetails(sc);
                System.out.println("-----------------------------------------------------------------");
                performLogin(con, login, args);
            } else if (loginOrSignUp == 0) {
                // Sign-up logic
                SignUp signUp = UserModule.getSignUpDetails(sc);
                System.out.println("-----------------------------------------------------------------");
                performSignUp(con, signUp);

                loginOrSignUp = 1;
            } else {
                System.out.println("Enter only 0 and 1-->>!!!");
            }
        }
    }

    private static void performLogin(Connection con, Login login, String[] args) {
        try {
            if (DBOperations.checkAdminDetails(con, login.getEmailId(), login.getPassword()))
                AdminInterface.adminInterface(con, args, sc, 0);
            else if (DBOperations.checkUserDetails(con, login.getEmailId(), login.getPassword()))
                UserInterface.userInterface(con, args, sc, 0);
            else if (DBOperations.checkCourierServiceDetails(con, login.getEmailId(), login.getPassword()))
                CourierServiceInterface.courierServiceInterface(con, args, sc, 0);
            else if (DBOperations.checkDealerDetails(con, login.getEmailId(), login.getPassword()))
                DealerInterface.dealerInterface(con, args, sc, 0);
            else
                System.out.println("Invalid Combinations");
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.toString());
        }
    }

    private static void performSignUp(Connection con, SignUp signUp) {

        if (con != null) {
            try {
                DBOperations.insertUserDetails(con, signUp.getUserName(), signUp.getMobileNumber(), signUp.getEmailId(),
                        signUp.getAddress(), signUp.getPassword());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}