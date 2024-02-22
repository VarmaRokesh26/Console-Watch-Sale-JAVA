package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import Controller.UserController;
import DAO.*;
import Model.Validation;
import Model.UIDGenerator;

public class UserView {

	private static String email;
	private static String password;

	private static String userId;
	private static String name;
	private static String mobileNumber;
	private static String address;
	private static String confirmPassword;

	private static String watchId;
	private static int quantity;
	private static String payment;

	static WatchDAO watch;
	static OrderDAO order;
	static UserDAO user;
	static CartDAO cart;

	private static char operation;

	public static UserDAO getLoginDetails(Scanner sc) {

		while (true) {
			System.out.println("-----------------------------------------------------------------");
			System.out.print("Enter your EmailID : ");

			email = sc.nextLine();
			if (Validation.validateEmail(email)) {
				while (true) {
					System.out.print("Enter password     : ");
					password = sc.nextLine();
					if (Validation.validatePassword(password))
						return new UserDAO(email, password);
					else
						System.out.println("\nEnter a Valid Password");
				}
			} else
				System.out.println("\nEnter a proper Email Address");
		}
	}

	// Sign Up logic
	public static UserDAO getSignUpDetails(Connection con, Scanner sc) throws SQLException {

		userId = UIDGenerator.IdGenerator(con, "user");
		// System.out.println(userId);

		while (true) {
			// userName
			System.out.println("-----------------------------------------------------------");
			System.out.print("Enter Your Name              : ");
			name = sc.nextLine();

			if (Validation.validateName(name))
				break;
			else
				System.out.println("<---Name should contains only Characters--->");
		}

		while (true) {
			// Mobile Number
			System.out.print("Enter Your Mobile Number     : ");
			mobileNumber = sc.nextLine();
			if (Validation.validateMobileNumber(mobileNumber))
				break;
			else
				System.out.println("<---Mobile Number should should have 10 numbers--->");
		}

		while (true) {
			// Email Address
			System.out.print("Enter Your Email Address     : ");
			email = sc.nextLine();
			if (Validation.validateEmail(email))
				break;
			else
				System.out.println("<---Invalid email--->");
		}

		while (true) {
			// Home Address to Delivery
			System.out.print("Enter Your Location(Address) : ");
			address = sc.nextLine();
			if (!address.toString().isEmpty())
				break;
			else
				System.out.println("Address should not be empty!");

		}
		while (true) {
			// Password
			System.out.print("Password                     : ");
			password = sc.nextLine();
			if (Validation.validatePassword(password)) {
				while (true) {
					System.out.print("Re-enter Password            : ");
					confirmPassword = sc.nextLine();
					if (password.equals(confirmPassword))
						break;
					else
						System.out.println("<---Password doesn't match--->");
				}
				break;
			} else
				System.out.println("<---Password should contains atleast \n8 characters, \n1 UpperCase, "
						+ "\n1 LowerCase, \n1 Numbers, \n1 SpecialCharacters--->");
		}
		return new UserDAO(userId, name, mobileNumber, email, address, confirmPassword);
	}

	public static void userView(Connection connection, String[] args, Scanner sc, int entry) throws SQLException {
		if (entry == 0)
			welcomeMsg();

		while (true) {
			actionMenu();
			operation = sc.next().charAt(0);
			switch (operation) {
				case 'S':
				case 's': {
					displayWatches(UserController.displayWatches(connection, watch, 1));
					System.out.println("-------------------------------------------------------------------");
					break;
				}

				case 'O':
				case 'o': {
					System.out.print("Enter WatchId to Display Seperately : ");
					watchId = sc.next();
					watch = new WatchDAO(watchId);
					while (true) {
						if (UserController.checkWatchId(connection, watch)) {
							displayWatches(UserController.displayWatches(connection, watch, 2));
							System.out.println("-------------------------------------------------------------------");

							orderOrCart();
							operation = sc.next().charAt(0);

							if (operation == 'N' || operation == 'n') {

								System.out.print("Enter the quantity required         : ");
								quantity = sc.nextInt();
								System.out.print("Payment Method Cash On Deliviry Or Online Payment : ");
								payment = sc.next();

								if (UserController.placeOrder(connection, orderRequirements(connection,
										UserController.displayWatches(connection, watch, 2), quantity,
										payment))) {
									System.out.println("<----Order Placed Successfully---->");
									break;
								}

								else
									System.out.println("<--Something Went Wrong-->");
							} else if (operation == 'A' || operation == 'a') {

								System.out.print("Enter the quantity required         : ");
								quantity = sc.nextInt();

								if (UserController.addToCart(connection, cartRequitrements(connection,
										UserController.displayWatches(connection, watch, 2), quantity))) {
									System.out.println("<--- Item Added to Cart--->");
								}
							} else if (operation == 'B' || operation == 'b') {
								userView(connection, args, sc, 1);
							}
						} else {
							System.out.println("<---Enterd item is not in the inventory-->");
						}
					}
				}
					break;

				case 'H':
				case 'h': {
					// history(connection);
					System.out.println("History");
					break;
				}
				case 'C':
				case 'c': {
					// viewCart(connection, args, sc, operation);
					System.out.println("Cart");
					break;
				}

				case 'V':
				case 'v': {
					// profileView(connection, args, sc, operation);
					System.out.println("Profile");
					break;
				}

				case 'E':
				case 'e': {
					// editProfile(connection, sc);
					System.out.println("Edit");
					break;
				}

				case 'P':
				case 'p': {
					// passwordChange(connection, sc);
					System.out.println("Password");
					break;
				}

				case 'B':
				case 'b': {
					userView(connection, args, sc, 1);
				}

				case 'L':
				case 'l': {
					// logOut(connection, args);
					System.out.println("Loggout");
					break;
				}
			}
		}
	}

	public static void welcomeMsg() {
		System.out.println("Welcome TO Watch World!!");
	}

	public static void actionMenu() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+               User Menu               +"
						+ "\n+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --S -> Display Watches                +"
						+ "\n+ --O -> Place Order                    +"
						+ "\n+ --H -> History of Orders              +"
						+ "\n+ --C -> View Cart                      +"
						+ "\n+ --V -> View Profile                   +"
						+ "\n+---------------------------------------+"
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

	public static void orderOrCart() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+              Order Menu               +"
						+ "\n+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --N -> Buy Now                        +"
						+ "\n+ --A -> Add to Cart                    +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void profile(String[] profile) {
		String s = "                      ";
		System.out.println("+---------------------------------------------------------------+"
				+ "\n+                             Profile                           +"
				+ "\n-----------------------------------------------------------------"
				+ "\n--User UID          : " + profile[0]
				+ "\n--Name              : " + profile[1]
				+ "\n--Mobile Number     : " + profile[2]
				+ "\n--Email             : " + profile[3]);
		displayAddress(profile[4], s);
		System.out.println("\n-----------------------------------------------------------------");
	}

	public static void displayAddress(String s, String dec) {
		String address[] = s.split(",");
		System.out.print("--Address           : ");
		for (int i = 0; i < address.length; i++) {
			if (i != address.length - 1 && i != 0) {
				System.out.print(dec + "" + address[i].trim() + ",\n");
			} else if (i == 0)
				System.out.print(address[i].trim() + ",\n");
			else
				System.out.print(dec + "" + address[i].trim() + ".\n");
		}
	}

	public static OrderDAO orderRequirements(Connection connection, List<WatchDAO> list, int quantity, String payment)
			throws SQLException {
		Iterator<WatchDAO> itr = list.iterator();
		String orderId = UserController.generateOrderId(connection);
		user = UserController.getUserId(connection);

		while (itr.hasNext()) {
			WatchDAO watch = itr.next();
			order = new OrderDAO(orderId, user.getUserId(), watch.getDealerId(), watch.getWatchId(), quantity,
					watch.getPrice() * quantity, payment, "Processed");
		}
		return order;
	}

	public static CartDAO cartRequitrements(Connection connection, List<WatchDAO> list, int quantity)
			throws SQLException {
		String cartId = UserController.generateCartID(connection);
		user = UserController.getUserId(connection);
		Iterator<WatchDAO> itr = list.iterator();
		while (itr.hasNext()) {

			WatchDAO watch = itr.next();
			String cartDetail = watch.getName() + "_" + (watch.getPrice() * quantity) + "_" + quantity;
			System.out.println(cartId);
			System.out.println(user.getUserId());
			System.out.println(watch.getWatchId());
			System.out.println(cartDetail);
			cart = new CartDAO(cartId, user.getUserId(), watch.getWatchId(), cartDetail);
		}
		return cart;
	}

	public static void shoppingCart(String cartDetails) {
		String[] cart = cartDetails.split("_");
		double price = Double.parseDouble(cart[2]);
		int quantity = Integer.parseInt(cart[3]);
		System.out.println(
				"+---------------------------------------+" +
						"\n--Item Id     : " + cart[0]
						+ "\n--Series Name : " + cart[1]
						+ "\n--Quantity    : " + quantity
						+ "\n--Price       : " + price);
	}

	public static void orderHistory(String order[]) {
		String s[] = order[3].split(" ");
		String t[] = order[2].split(" ");
		System.out.println(
				"+---------------------------------------+" +
						"\n--Order Id       : " + order[0]
						+ "\n--Item Id        : " + order[1]
						+ "\n--Ordered Date   : " + t[0]
						+ "\n--Expected Date  : " + s[0]
						+ "\n--Quantity       : " + order[4]
						+ "\n--Status         : " + order[5]
						+ "\n--Price          : " + order[6]);
	}

	public static void cartWork() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --R -> Remove an Item in Cart         +"
						+ "\n+ --D -> Delete all item in Cart        +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	// // Method for View Cart
	// public static void viewCart(Connection connection, String[] args, Scanner sc, char action) {
	// 	try {
	// 		if (!FetchAndDisplayFromDB.showMyCart(connection)) {
	// 			UserInterface.noItemStatement();
	// 		} else {
	// 			while (true) {
	// 				UserInterface.cartWork();
	// 				action = sc.next().charAt(0);
	// 				if (action == 'R' || action == 'r') {
	// 					System.out.print("Enter the Watch Id to Remove Form Cart : ");
	// 					watchId = sc.next();
	// 					DeleteFromDB.removeAnItemInCart(connection, watchId);
	// 					if (!FetchAndDisplayFromDB.showMyCart(connection)) {
	// 						UserInterface.noItemStatement();
	// 					}
	// 				} else if (action == 'D' || action == 'd') {
	// 					DeleteFromDB.removeAllItemsFromCart(connection);
	// 					if (!FetchAndDisplayFromDB.showMyCart(connection)) {
	// 						noItemStatement();
	// 					}
	// 				} else if (action == 'B' || action == 'b') {
	// 					UserInterface.userInterface(connection, null, sc, action);
	// 				}
	// 			}
	// 		}
	// 	} catch (Exception e) {
	// 		System.out.println(e.toString());
	// 	}
	// }

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

	public static void noItemStatement() {
		System.out.println("+------------------------------------------+");
		System.out.println("+          No Items to Display             +");
		System.out.println("+------------------------------------------+");
	}
}
