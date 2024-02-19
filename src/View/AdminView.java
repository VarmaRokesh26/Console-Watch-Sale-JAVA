package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

// import Controller.AdminController;

public class AdminView {

    public static void adminInterface(Connection con, String[] args, Scanner sc, int entry) throws SQLException {

		if (entry == 0)
			welcomeMsg();
		while (true) {
			actionDisply();
			actionMenu();
			char operation = sc.next().charAt(0);
			switch (operation) {

                case 'U':
                case 'u': {
					System.out.println("Update");
                    // updateWatchDetails(con, sc);
                    break;
                }
				
                case 'D':
                case 'd': {
					String watchId = sc.next();
					// AdminController.
                    break;
                }
				
                case 'C':
                case 'c': {
					System.out.println("Clear All");
                    // deletemanyWatches(con);
                    break;
                }
				
                case 'S':
                case 's': {
					System.out.println("Show");
                    // displayAsRequired(con, args, sc);
                    break;
                }
				
                case 'A':
                case 'a': {
					System.out.println("Add ");
                    // addRespectiveDetails(con, args, sc, operation);
                    break;
                }
				
                case 'V':
                case 'v': {
					System.out.println("View Profile");
                    // viewAdminProfile(con, args, sc, operation);
                    break;
                }
				
                case 'E':
                case 'e': {
					System.out.println("Edit Profile");
					// editAdminProfile(con, sc);
                    break;
                }
				
                case 'P':
                case 'p': {
					System.out.println("Pass");
					// changePassword(con, sc);
                    break;
                }
				
                case 'B':
                case 'b': {
					adminInterface(con, args, sc, operation);
                }
                
                case 'L':
                case 'l': {
					System.out.println("Logout");
					// logOut(con, args);
                    break;
                }
				
            }
		}
	}
	
	public static void deleteWatch(Connection con, Scanner sc) {
		String watchId = sc.next();

	}

	public static void welcomeMsg() {
		System.out.print("\n+-------------------------------------------------------+\n"
		+ "+                 Welcome back admin                    +");
			}

	public static void actionDisply() {
		System.out.print("\n+-------------------------------------------------------+"
				+ "\n+                    Action Menu                        +");
	}

	public static void actionMenu() {
		System.out.print(
				"\n+-------------------------------------------------------+"
						+ "\n+ Enter                                                 +"
						+ "\n+-------------------------------------------------------+"
						+ "\n+ --A -> Add new Watch/ Admin/ Dealer/ CourierServices  +"
						+ "\n+ --C -> Delete Watch with 0 stocks                     +"
						+ "\n+ --D -> Delete watch                                   +"
						+ "\n+ --S -> Display Watch/ Admin/ Dealer/ CourierServices  +"
						+ "\n+ --U -> Update details of watches                      +"
						+ "\n+ --V -> View Profile                                   +"
						+ "\n+-------------------------------------------------------+"
						+ "\n---------> ");
	}

	public static void profileAction() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+              Profile Menu             +"
						+ "\n+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --V -> View Profile                   +"
						+ "\n+ --E -> Edit Profile                   +"
						+ "\n+ --P -> Change Password                +"
						+ "\n+ --B -> Back                           +"
						+ "\n+ --L -> Logout                         +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void viewProfile(String[] profile) {
		System.out.println("+---------------------------------------------------------------+"
				+ "\n+                             Profile                           +"
				+ "\n+---------------------------------------------------------------+"
				+ "\n--Admin UID         : " + profile[0]
				+ "\n--Name              : " + profile[1]
				+ "\n--Mobile Number     : " + profile[2]
				+ "\n--Email             : " + profile[3]
				+ "\n--Role              : " + profile[4]
				+ "\n+---------------------------------------------------------------+");
	}

	public static void displayWatches(String id, String name, String brand, double price, String description,
			int number_of_stocks, String dealerId, String dealerName) {
		System.out.println("-------------------------------------------------------------------");
		System.out.println(
				"--ID                             : " + id
						+ "\n--Name                           : " + name
						+ "\n--Brand                          : " + brand
						+ "\n--Price                          : " + price
						+ "\n--Description                    : " + description
						+ "\n--Number of Stocks available     : " + number_of_stocks
						+ "\n--Dealer Id                      : " + dealerId
						+ "\n--Dealer of the Watch            : " + dealerName);
	}

	public static void insertOption() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+              Insert Menu              +"
						+ "\n+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --1 -> Add New Watch                  +"
						+ "\n+ --2 -> Add New Admin                  +"
						+ "\n+ --3 -> Add New Dealer Details         +"
						+ "\n+ --4 -> Add NEw Courier Service        +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void displayOptionsForAdmin() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+             Display Menu              +"
						+ "\n+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --1 -> Display Watches                +"
						+ "\n+ --2 -> Display Admin                  +"
						+ "\n+ --3 -> Display Dealer List            +"
						+ "\n+ --4 -> Display Courier List           +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

}
