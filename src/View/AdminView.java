package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import Controller.AdminController;
import Controller.DealerController;
import DAO.*;
import Main.Main;
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

	private static WatchDAO watch;
	private static AdminDAO admin;
	private static DealerDAO dealer;

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

					watch = new WatchDAO(watchId, seriesName, brand, price, description, numberOfStocks, dealerId);
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
					watch = new WatchDAO(watchId);
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
							displayWatches(AdminController.displayWatches(connection, watch));
							System.out.println("-------------------------------------------------------------------");
						} else if (sh == '2') {
							admin = new AdminDAO();
							displayAdmin(AdminController.displayAdmin(connection));
							System.out.println("-------------------------------------------------------------------");
						} else if (sh == '3') {
							DealerView.displayDealer(DealerController.displayDealer(connection));
							System.out.println("-------------------------------------------------------------------");
						} else if (sh == 'B' || sh == 'b') {
							adminView(connection, args, sc, sh);
						} else
							System.out.println("Enter a valid Option");
					}
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
							} else {
								System.out.println("<---Data Insertion failed--->");
							}
						} else if (add == '2') {
							admin = getAdminDetails(connection, sc);
							if (AdminController.insertAdmin(connection, admin)) {
								System.out.println("Admin Added succesfully!!");
							} else {
								System.out.println("Inserting Admin is Failed");
							}
						} else if (add == '3') {
							dealer = getdealerDetails(connection, sc);
							if (AdminController.insertDealer(connection, dealer)) {
								System.out.println("Dealer Added succesfully!!");
							} else {
								System.out.println("Inserting Dealer is Failed");
							}
						} else if (add == 'B' || add == 'b') {
							adminView(connection, args, sc, 1);
						} else
							System.out.println("Enter a valid Option");
					}
				}

				case 'V':
				case 'v': {
					admin = AdminController.adminProfile(connection, admin);
					viewProfile(admin);
					while (true) {
						profileAction();
						operation = sc.next().charAt(0);
						if (operation == 'E' || operation == 'e') {
							admin = editAdminProfile(connection, sc);
							if (AdminController.updateProfile(connection, admin))
								System.out.println("<---Your Profile is Updated--->");
							else
								System.out.println("<--- Profile Updation Failed --->");
							break;
						} else if (operation == 'P' || operation == 'p') {
							changePassword(connection, sc);
						} else if (operation == 'L' || operation == 'l') {
							AdminController.logOut(args);
							System.out.println("Logging out. GoodBye!");
							System.out.println("-----------------------------------------------------------------");
							Main.main(args);
						} else if (operation == 'V' || operation == 'v') {
							admin = AdminController.adminProfile(connection, admin);
							viewProfile(admin);
						} else if (operation == 'B' || operation == 'b') {
							adminView(connection, args, sc, 1);
						}
					}
				}

				case 'B':
				case 'b': {
					adminView(connection, args, sc, 1);
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
						+ "\n+ --A -> Add new Watch/ Admin/ Dealer                   +"
						+ "\n+ --C -> Delete Watch with 0 stocks                     +"
						+ "\n+ --D -> Delete watch                                   +"
						+ "\n+ --S -> Display Watch/ Admin/ Dealer                   +"
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

	public static void viewProfile(AdminDAO admin) {
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

	public static void displayAdmin(List<AdminDAO> list) {
		Iterator<AdminDAO> itr = list.iterator();
		while (itr.hasNext()) {
			AdminDAO admin = itr.next();
			System.out.println("-------------------------------------------------------------------");
			System.out.println(
							    "--Admin ID                       : " + admin.getAdminId()
							+ "\n--Admin Name                     : " + admin.getAdminName()
							+ "\n--Mobile Number                  : " + admin.getAdminMobileNumber()
							+ "\n--Email                          : " + admin.getAdminMailid()
							+ "\n--Role                           : " + admin.getAdminRole());
		}
	}

	public static void displayWatches(List<WatchDAO> list) {
		Iterator<WatchDAO> itr = list.iterator();
		while (itr.hasNext()) {
			WatchDAO watch = itr.next();
			System.out.println("-------------------------------------------------------------------");
			System.out.println(
					"--ID                             : " + watch.getWatchId()
							+ "\n--Name                           : " + watch.getName()
							+ "\n--Brand                          : " + watch.getBrand()
							+ "\n--Price                          : " + watch.getPrice()
							+ "\n--Description                    : " + watch.getDescription()
							+ "\n--Number of Stocks available     : " + watch.getNumberOfStocks()
							+ "\n--Dealer Id                      : " + watch.getDealerId());
		}
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
						+ "\n+ --2 -> Display Admin List             +"
						+ "\n+ --3 -> Display Dealer List            +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static AdminDAO getAdminDetails(Connection connection, Scanner sc) throws SQLException {
		System.out.println("<---Enter the Details of the Employee to Add as Admin--->");

		adminId = AdminController.generateAdminId(connection);

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
		return new AdminDAO(adminId, name, mobileNumber, mailId, adminRole, confirmPassword);
	}

	public static WatchDAO getWatchDeatils(Connection connection, Scanner sc) throws SQLException {
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

		return new WatchDAO(watchId, seriesName, brand, price, description, numberOfStocks, dealerId);
	}

	public static DealerDAO getdealerDetails(Connection connection, Scanner sc) throws SQLException {
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

		return new DealerDAO(dealerId, dealerName, dealerMailId, dealerLocation,
				contactNumber, dealerPassword);
	}

	public static AdminDAO editAdminProfile(Connection connection, Scanner sc) {
		System.out.println("<---- Enter your Details to Edit Profile --->");
		while (true) {
			System.out.print("Enter Your Name     : ");
			name = sc.next().trim() + sc.nextLine();
			if (Validation.validateName(name))
				break;
			else
				System.out.println("Enter a Valid name");
		}
		while (true) {
			System.out.print("Enter mobile Number : ");
			mobileNumber = sc.nextLine();
			if (Validation.validateMobileNumber(mobileNumber))
				break;
			else
				System.out.println("Enter a valid Mobile Numebr");
		}

		while (true) {
			System.out.print("Enter your Email Id : ");
			mailId = sc.nextLine();
			if (Validation.validateEmail(mailId))
				break;
			else
				System.out.println("Enter a valid emailId");
		}
		return new AdminDAO(name, mobileNumber, mailId);
	}

	public static void changePassword(Connection connection, Scanner sc) {

		while (true) {
			System.out.print("Enter your currrent password : ");
			String currentPassword = sc.next().trim();
			sc.nextLine();
			try {
				admin = new AdminDAO(currentPassword);
				if (AdminController.checkPassword(admin)) {
					while (true) {
						System.out.print("Enter new Password           : ");
						String newPassword = sc.next();
						sc.nextLine();
						if (!newPassword.equals(currentPassword) && Validation.validatePassword(newPassword)) {
							while (true) {
								System.out.print("Re-enter new Password        : ");
								String reenterPassword = sc.nextLine();
								if (newPassword.equals(reenterPassword)) {
									admin = new AdminDAO(newPassword);
									if (AdminController.updatePassword(connection, admin)) {
										System.out.println("<---Password changed Successfully--->");
										break;
									} else
										System.out.println("<---Password is Not changed--->");
								} else {
									System.out.println("New Password and Confirm Password whuld be same!");
								}
							}
							break;
						} else {
							System.out.println("Current Password should not equal to New Password");
						}
					}
					break;
				} else {
					System.out.println("Enter correct password");
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
}