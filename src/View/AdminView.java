package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.AdminController;
import Model.Watch;

// import Controller.AdminController;

public class AdminView {

	private static String watchId;
	private static String seriesName;
	private static String brand;
	private static double price;
	private static String description;
	private static int numberOfStocks;
	private static String dealerId;
	
	protected static  Watch watch;

	public static void adminView(Connection connection, String[] args, Scanner sc, int entry) throws SQLException {

		if (entry == 0)
			welcomeMsg();
		while (true) {
			actionDisply();
			actionMenu();
			char operation = sc.next().charAt(0);
			switch (operation) {

				case 'U':
				case 'u': {
					System.out.print("Enter the Watch Id to Update : ");
					watchId = sc.next();
					System.out.println("------Enter the watch details to be added:------");

					while (true) {
						System.out.print("Enter series Name                 : ");
						seriesName = sc.next().trim() + sc.nextLine();
						if (!seriesName.equals(""))
							break;
					}

					while (true) {
						System.out.print("Enter brand name                  : ");
						brand = sc.next() + sc.nextLine();
						if (!brand.trim().isEmpty()) {
							break;
						} else {
							System.out.print("Enter a valid brand Name");
						}
					}

					System.out.print("Enter price for the watches       : ");
					price = sc.nextDouble();

					System.out.print("'/' seperated description\n");
					while (true) {
						System.out.print("Enter description for the watches : ");
						description = sc.next() + sc.nextLine();
						if (!description.trim().isEmpty())
							break;
					}

					System.out.print("Enter amount of stocks available  : ");
					while (true) {
						numberOfStocks = sc.nextInt();
						sc.nextLine();
						if (numberOfStocks >= 0)
							break;
					}

					System.out.print("Enter the Dealer Id               : ");
					while (true) {
						dealerId = sc.next();
						if (!dealerId.isEmpty())
							break;
						else
							System.out.println("Enter a valid Dealer Id");
					}

					watch = new Watch(watchId, seriesName, brand, price, description, numberOfStocks, dealerId);
					if(AdminController.updateWatch(connection, watch))
						System.out.println("<---Update successful--->");
					else
						System.out.println("<---Updation Unsuccessfull--->");
					break;
				}

				case 'D':
				case 'd': {
					System.out.print("Enter the Watch Id to Delete : ");
					watchId = sc.next();
					watch = new Watch(watchId);
					if (AdminController.deleteWatch(connection, watch))
						System.out.println("<---Item Deleted From Inventory--->");
					else 
						System.out.println("<---Deletion Failed--->");
					break;
				}

				case 'C':
				case 'c': {
					System.out.println("Clear All");
					if(AdminController.deletemanyWatches(connection))
						System.out.println("<---Watches with Zero Stocks are Deleted--->");
					else
						System.out.println("<---All Stocks are more than Zero--->");
					break;
				}

				case 'S':
				case 's': {
					while (true) {
						displayOptionsForAdmin();
						char sh = sc.next().charAt(0);
						if (sh == '1') {
							AdminController.displayWatches(connection); 
						} else if (sh == '2') {
							// displayAdminDealerCourier(con, Character.getNumericValue(sh));
						} else if (sh == '3') {
							// displayAdminDealerCourier(con, Character.getNumericValue(sh));
						} else if (sh == '4') {
							// displayAdminDealerCourier(con, Character.getNumericValue(sh));
						} else if (sh == 'B' || sh == 'b') {
							// AdminInterface.adminInterface(con, args, sc, sh);
						} else
							System.out.println("Enter a valid Option");
						break;
					}
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
					adminView(con, args, sc, entry);
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

	public static void displayWatches(Watch watches, String dealerName) {
		System.out.println("-------------------------------------------------------------------");
		System.out.println(
				"--ID                             : " + watches.getWatchId()
						+ "\n--Name                           : " + watches.getName()
						+ "\n--Brand                          : " + watches.getBrand()
						+ "\n--Price                          : " + watches.getPrice()
						+ "\n--Description                    : " + watches.getDescription()
						+ "\n--Number of Stocks available     : " + watches.getNumberOfStocks()
						+ "\n--Dealer Id                      : " + watches.getDealerId()
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
