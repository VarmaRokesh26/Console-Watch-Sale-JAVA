package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.UserController;
import DAO.User;
import Model.Connectivity;
import View.UserView;

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
                UserController.performLogin(con, login, args, sc);
            } else if (loginOrSignUp == 0) {
                // Sign-up logic
                User signUp = UserView.getSignUpDetails(con, sc);
                System.out.println("-----------------------------------------------------------------");
                UserController.performSignUp(con, signUp);

                loginOrSignUp = 1;
            } else {
                System.out.println("Enter only 0 and 1-->>!!!");
            }
        }
    }

    
}
