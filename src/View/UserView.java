package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import Controller.AdminController;
import Controller.UserController;
import DAO.*;
import Main.Main;
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
	static DealerDAO dealer;

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
					while (true) {
						System.out.print("Enter WatchId to Display Seperately : ");
						watchId = sc.next();
						watch = new WatchDAO(watchId);
						if (UserController.checkWatchId(connection, watch)) {
							displayWatches(UserController.displayWatches(connection, watch, 2));
							System.out.println("-------------------------------------------------------------------");
							orderNowOrCart(connection, sc, args, operation);
							break;
						} else {
							System.out.println("<---Enterd item is not in the inventory-->");
						}
					}
					// break;
				}

				case 'H':
				case 'h': {
					user = UserController.getUserId(connection);
					orderHistory(UserController.historyOfOrders(connection, user, dealer, 0));
					break;
				}
				case 'C':
				case 'c': {
					user = UserController.getUserId(connection);
					shoppingCart(UserController.viewCart(connection, user));
					System.out.println("+---------------------------------------+");
					while (true) {
						cartWork();
						char n = sc.next().charAt(0);
						if (n == '1') {
							System.out.print("Enter the Watch Id to remove from Cart : ");
							watchId = sc.next();
							cart = new CartDAO(watchId, UserController.getUserId(connection).getUserId());
							if (UserController.removeItemFromCart(connection, cart)) {
								System.out.println("<---Item Removed from Cart--->");
							} else {
								System.out.println("<---Something Went Wrong--->");
							}
						} else if (n == '2') {
							cart = new CartDAO(UserController.getUserId(connection).getUserId());
							if (UserController.removeAllItemFromCart(connection, cart)) {
								noItemStatement();
							} else {
								System.out.println("<---Something Went Wrong--->");
							}
						} else if (n == 'B' || n == 'b') {
							userView(connection, args, sc, 1);
						}
					}
				}

				case 'V':
				case 'v': {
					user = UserController.userProfile(connection, user);
					viewProfile(user);
					while (true) {
						profileAction();
						operation = sc.next().charAt(0);
						if (operation == 'E' || operation == 'e') {
							user = editUserProfile(connection, sc);
							if (UserController.updateProfile(connection, user))
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
							user = UserController.userProfile(connection, user);
							viewProfile(user);
						} else if (operation == 'B' || operation == 'b') {
							userView(connection, args, sc, 1);
						}
					}
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

	public static void orderNowOrCart(Connection connection, Scanner sc, String[] args, char operation)
			throws SQLException {
		while (true) {
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
			} else if (operation == 'B' || operation == 'b')
				userView(connection, args, sc, 1);
		}
	}

	// Method to Edit Profle
	public static UserDAO editUserProfile(Connection connection, Scanner sc) {

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
			email = sc.nextLine();
			if (Validation.validateEmail(email))
				break;
			else
				System.out.println("Enter a valid emailId");
		}

		while (true) {
			System.out.print("Enter your Location : ");
			address = sc.nextLine();
			if (!address.isEmpty())
				break;
			else
				System.out.println("Address cannot be Empty");
		}

		return new UserDAO(name, mobileNumber, email, address);
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

	public static void viewProfile(UserDAO user) {
		String s = "                      ";
		System.out.println("+---------------------------------------------------------------+"
				+ "\n+                             Profile                           +"
				+ "\n-----------------------------------------------------------------"
				+ "\n--User UID          : " + user.getUserId()
				+ "\n--Name              : " + user.getUserName()
				+ "\n--Mobile Number     : " + user.getMobileNumber()
				+ "\n--Email             : " + user.getEmailId());
				displayAddress(user.getAddress(), s);
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
			String cartDetail = watch.getWatchId() + "_" + watch.getName() + "_" + (watch.getPrice() * quantity) + "_"
					+ quantity;
			cart = new CartDAO(cartId, user.getUserId(), watch.getWatchId(), cartDetail);
		}
		return cart;
	}

	public static void shoppingCart(List<CartDAO> list) {
		Iterator<CartDAO> itr = list.iterator();
		boolean hasItems = false;
		while (itr.hasNext()) {
			CartDAO cartItem = itr.next();
			String cartDetails = cartItem.getCartDetails();
			if (cartDetails != null) {
				String[] cart = cartDetails.split("_");
				double price = Double.parseDouble(cart[2]);
				int quantity = Integer.parseInt(cart[3]);
				System.out.println(
						"+---------------------------------------+" +
								"\n--Item Id     : " + cart[0]
								+ "\n--Series Name : " + cart[1]
								+ "\n--Quantity    : " + quantity
								+ "\n--Price       : " + price);
				hasItems = true;
			}
		}
		if (!hasItems) {
			noItemStatement();
		}
	}
	

	public static void orderHistory(List<OrderDAO> orders) {
		for (OrderDAO order : orders) {
			System.out.println(
					"+---------------------------------------+" +
							"\n--Order Id       : " + order.getOrderId() +
							"\n--User Id        : " + order.getUserId() +
							"\n--Dealer Id      : " + order.getDealerId() +
							"\n--Watch Id       : " + order.getWatchId() +
							"\n--Quantity       : " + order.getQuantity() +
							"\n--Ordered Date   : " + order.getOrderDate() +
							"\n--Delivery Date  : " + order.getDeliveryDate() +
							"\n--Total Amount   : " + order.getTotalAmount() +
							"\n--Payment Mode   : " + order.getPaymentMode() +
							"\n--Status         : " + order.getStatus());
		}
	}
	

	public static void cartWork() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --1 -> Remove an Item in Cart         +"
						+ "\n+ --2 -> Delete all item in Cart        +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
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

	public static void noItemStatement() {
		System.out.println("+------------------------------------------+");
		System.out.println("+          No Items to Display             +");
		System.out.println("+------------------------------------------+");
	}

	public static void changePassword(Connection connection, Scanner sc) {

		while (true) {
			System.out.print("Enter your currrent password : ");
			String currentPassword = sc.next().trim();
			sc.nextLine();
			try {
				user = new UserDAO(0, currentPassword);
				if (UserController.checkPassword(user)) {
					while (true) {
						System.out.print("Enter new Password           : ");
						String newPassword = sc.next();
						sc.nextLine();
						if (!newPassword.equals(currentPassword) && Validation.validatePassword(newPassword)) {
							while (true) {
								System.out.print("Re-enter new Password        : ");
								String reenterPassword = sc.nextLine();
								if (newPassword.equals(reenterPassword)) {
									user = new UserDAO(0, newPassword);
									if (UserController.updatePassword(connection, user)) {
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
