package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.AdminController;
import DAO.Admin;
import DAO.Dealer;
import DAO.Watch;
import Model.UIDGenerator;
import Model.Validation;

// import Controller.AdminController;

public class AdminView {

	private static String watchId;
	private static String seriesName;
	private static String brand;
	private static double price;
	private static String description;
	private static int numberOfStocks;
	private static String dealerId;

	// Variables for Admin Details
	private static String adminId;
	private static String name;
	private static String mobileNumber;
	private static String mailId;
	private static String password;
	private static String confirmPassword;
	private static String adminRole;

	// Variables for Dealer Details
	private static String dealerName;
	private static String dealerLocation;
	private static String contactNumber;
	private static String dealerMailId;
	private static String dealerPassword;

	private static Watch watch;
	private static Admin admin;
	private static Dealer dealer;

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
					if (AdminController.updateWatch(connection, watch))
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
					if (AdminController.deletemanyWatches(connection))
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
							// AdminController.displayWatches(connection);
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
					while (true) {
						insertOption();
						char add = sc.next().charAt(0);
						if (add == '1') {
							watch = getWatchDeatils(connection, sc);
							if (AdminController.insertWatch(connection, watch)) {
								System.out.println("<---Data Inserted--->");
								break;
							} else {
								System.out.println("<---Data Insertion failed--->");
							}
						} else if (add == '2') {
							admin = getAdminDetails(connection, sc);
							if (AdminController.insertAdmin(connection, admin)) {
								System.out.println("Admin Added succesfully!!");
								break;
							} else {
								System.out.println("Inserting Admin is Failed");
							}
						} else if (add == '3') {
							dealer = getdealerDetails(connection, sc);
							if (AdminController.insertDealer(connection, dealer)) {
								System.out.println("Dealer Added succesfully!!");
								break;
							} else {
								System.out.println("Inserting Dealer is Failed");
							}
							break;
						} else if (add == 'B' || add == 'b') {
							adminView(connection, args, sc, add);
						} else
							System.out.println("Enter a valid Option");
					}
					break;
				}

				case 'V':
				case 'v': {
					System.out.println("View Profile");
					admin = AdminController.adminProfile(connection, admin);
					viewProfile(admin);
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
					adminView(connection, args, sc, entry);
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

	public static void viewProfile(Admin admin) {
		System.out.println("+---------------------------------------------------------------+"
				+ "\n+                             Profile                           +"
				+ "\n+---------------------------------------------------------------+"
				+ "\n--Admin UID         : " + admin.getAdminId()
				+ "\n--Name              : " + admin.getAdminName()
				+ "\n--Mobile Number     : " + admin.getAdminMobileNumber()
				+ "\n--Email             : " + admin.getAdminMailid()
				+ "\n--Role              : " + admin.getAdminRole()
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

	public static Admin getAdminDetails(Connection connection, Scanner sc) throws SQLException {
		System.out.println("<---Enter the Details of the Employee to Add as Admin--->");

		adminId = UIDGenerator.IdGenerator(connection, "admin");

		while (true) {
			System.out.print("Name                 : ");
			name = sc.next() + sc.nextLine();
			if (Validation.validateName(name))
				break;
			else
				System.out.println("Enter a valid Name");
		}

		while (true) {
			System.out.print("Enter Mobile Number  : ");
			mobileNumber = sc.nextLine();
			if (Validation.validateMobileNumber(mobileNumber))
				break;
			else
				System.out.println("<---Enter a valid mobile Number--->");
		}

		while (true) {
			System.out.print("Enter Email Address  : ");
			mailId = sc.nextLine();
			if (Validation.validateEmail(mailId))
				break;
			else
				System.out.println("<---Enter a valid Email--->");
		}

		while (true) {
			System.out.println("||Role should only be \"Executive\" and \"Manager\"||");
			System.out.print("Enter the Admin Role : ");
			adminRole = sc.nextLine().toLowerCase();
			if (adminRole.equals("executive") || adminRole.equals("manager"))
				break;
			else
				System.out.println("Other emplyees cannoot have account in admin Dashboard");
		}

		while (true) {
			System.out.print("Enter Password       : ");
			password = sc.nextLine();
			if (Validation.validatePassword(password)) {
				while (true) {
					System.out.print("Re-enter Password    : ");
					confirmPassword = sc.nextLine();
					if (password.equals(confirmPassword)) {
						break;
					}
				}
				break;
			} else
				System.out.println(
						"<---Password should contain at least 8 characters, 1 UpperCase, 1 LowerCase, 1 Numbers, 1 SpecialCharacters--->");

		}
		return new Admin(name, adminId, mobileNumber, mailId, adminRole, confirmPassword);
	}

	public static Watch getWatchDeatils(Connection connection, Scanner sc) throws SQLException {
		System.out.println("------Enter the watch details to be added:------");

		watchId = UIDGenerator.IdGenerator(connection, "watches");

		while (true) {
			System.out.print("Enter series Name                 :");
			seriesName = sc.next() + sc.nextLine();
			if (!seriesName.equals(""))
				break;
		}

		while (true) {
			System.out.print("Enter brand name                  :");
			brand = sc.next() + sc.nextLine();
			if (!brand.trim().isEmpty()) {
				break;
			} else {
				System.out.print("Enter a valid brand Name");
			}
		}

		System.out.print("Enter price for the watches       :");
		price = sc.nextDouble();

		System.out.print("'/' seperated description\n");
		while (true) {
			System.out.print("Enter description for the watches :");
			description = sc.next() + sc.nextLine();
			if (!description.trim().isEmpty())
				break;
		}

		System.out.print("Enter amount of stocks available  :");
		while (true) {
			numberOfStocks = sc.nextInt();
			sc.nextLine();
			if (numberOfStocks >= 0)
				break;
		}

		System.out.print("Enter the Dealer Id               :");
		while (true) {
			dealerId = sc.next();
			if (!dealerId.isEmpty())
				break;
			else
				System.out.println("Enter the Dealer ID for Inserting Watch");
		}

		return new Watch(watchId, seriesName, brand, price, description, numberOfStocks, dealerId);
	}

	public static Dealer getdealerDetails(Connection connection, Scanner sc) throws SQLException {
		System.out.println("------Enter the dealer details to be added:------");
		dealerId = UIDGenerator.IdGenerator(connection, "dealer");
		while (true) {
			System.out.print("Enter Dealer Name           : ");
			dealerName = sc.next() + sc.nextLine();
			if (Validation.validateName(dealerName))
				break;
			else
				System.out.println("Enter a valid Dealer Name");
		}

		while (true) {
			System.out.print("Enter Dealer Contact Number : ");
			contactNumber = sc.nextLine();
			if (Validation.validateMobileNumber(contactNumber))
				break;
			else
				System.out.println("<---Enter a valid mobile Number--->");
		}

		while (true) {
			System.out.print("Enter Dealer Location       : ");
			dealerLocation = sc.nextLine();
			if (!dealerLocation.isEmpty())
				break;
			else
				System.out.println("<---Enter a Location--->");
		}

		while (true) {
			System.out.print("Enter the Dealer MailId     : ");
			dealerMailId = sc.nextLine();
			if (Validation.validateEmail(dealerMailId))
				break;
			else
				System.out.println("<---Enter a valid Email--->");
		}

		while (true) {
			System.out.print("Enter Password              : ");
			dealerPassword = sc.nextLine();
			if (Validation.validatePassword(dealerPassword)) {
				while (true) {
					System.out.print("Re-enter Password           : ");
					confirmPassword = sc.nextLine();
					if (dealerPassword.equals(confirmPassword)) {
						break;
					}
				}
				break;
			} else
				System.out.println(
						"<---Password should contain at least 8 characters, 1 UpperCase, 1 LowerCase, 1 Numbers, 1 SpecialCharacters--->");
		}

		return new Dealer(dealerId, dealerName, dealerMailId, dealerLocation,
				contactNumber, dealerPassword);
	}
}