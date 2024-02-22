package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import Controller.DealerController;
import DAO.DealerDAO;
import DAO.OrderDAO;
import DAO.UserDAO;
import Main.Main;
import Model.Validation;

public class DealerView {

	// private static String dealerId;
	private static String dealerName;
	private static String dealerLocation;
	private static String contactNumber;
	private static String dealerMailId;
	// private static String dealerPassword;

	private static String orderId;
	private static String status;

	static DealerDAO dealer;
	static UserDAO user;
	static OrderDAO order;

	public static void dealerView(Connection connection, String[] args, Scanner sc, int entry) throws SQLException {
		while (true) {
			actionMenu();
			char operation = sc.next().charAt(0);
			switch (operation) {

				case 'D':
				case 'd': {
					dealer = DealerController.getDealerId(connection);
					List<OrderDAO> dealerOrders = DealerController.historyOfOrders(connection, user, dealer, 1);
					if (dealerOrders.isEmpty()) {
						noItemStatement();
					} else {
						displayOrders(dealerOrders);
						while (true) {
							updateStatus();
							char action = sc.next().charAt(0);
				
							if (action == 'U' || action == 'u') {
								if(updateOrderStatus(connection, sc))
									System.out.println("<---Updated Succesfully--->");
								else
									System.out.println("Something Went Wrong");
							} else if (action == 'B' || action == 'b') {
								dealerView(connection, args, sc, action);
							} else
								System.out.println("Enter a valid Option");
						}
					}
					break;
				}

				case 'C':
				case 'c': {
					dealer = DealerController.getDealerId(connection);
					List<OrderDAO> dealerOrders = DealerController.historyOfOrders(connection, user, dealer, 2);
					if (dealerOrders.isEmpty()) {
						noCompletedItemStatement();
					} else {
						displayOrders(dealerOrders);
					}
					break;
				}

				case 'V':
				case 'v': {
					dealer = DealerController.dealerProfile(connection, dealer);
					viewProfile(dealer);
					while (true) {
						profileAction();
						operation = sc.next().charAt(0);
						if (operation == 'E' || operation == 'e') {
							dealer = editDealerProfile(connection, sc);
							if (DealerController.updateProfile(connection, dealer))
							System.out.println("<---Your Profile is Updated--->");
							else
							System.out.println("<--- Profile Updation Failed --->");
							break;
						} else if (operation == 'P' || operation == 'p') {
							changePassword(connection, sc);
						} else if (operation == 'L' || operation == 'l') {
							DealerController.logOut(args);
							System.out.println("Logging out. GoodBye!");
							System.out.println("-----------------------------------------------------------------");
							Main.main(args);
						} else if (operation == 'V' || operation == 'v') {
							dealer = DealerController.dealerProfile(connection, dealer);
							viewProfile(dealer);
						} else if (operation == 'B' || operation == 'b') {
							dealerView(connection, args, sc, 1);
						}
					}
				}
				
				case 'W':
				case 'w': {
					dealer = DealerController.getDealerId(connection);
					break;
				}
				
				case 'B':
				case 'b': {
					dealerView(connection, args, sc, operation);
				}
				
			}
		}
	}
	
	public static boolean updateOrderStatus(Connection connection, Scanner sc) throws SQLException {
		System.out.print("Enter the Orderid to Update the order Status : ");
		orderId = sc.next();
		orderActions();
		System.out.print("\nEnter the valid option to update the Order status : ");
		char action = sc.next().charAt(0);

		if (action == '1')
			status = "Order is Ready for Shipment";
		else if (action == '2')
			status = "Order is Shipped";
		else if (action == '3')
			status = "On Hold";
		else if(action == '4')
			status = "Reached Your Region";
		else if(action == '5')
			status = "Delivered";
		else
			System.out.println("Enter a Valid Option");

		order = new OrderDAO(orderId, status);
		
		return DealerController.updateOrderStatus(connection, order);
	}

	public static void changePassword(Connection connection, Scanner sc) {

		while (true) {
			System.out.print("Enter your currrent password : ");
			String currentPassword = sc.next().trim();
			sc.nextLine();
			try {
				dealer = new DealerDAO(currentPassword);
				if (DealerController.checkPassword(dealer)) {
					while (true) {
						System.out.print("Enter new Password           : ");
						String newPassword = sc.next();
						sc.nextLine();
						if (!newPassword.equals(currentPassword) && Validation.validatePassword(newPassword)) {
							while (true) {
								System.out.print("Re-enter new Password        : ");
								String reenterPassword = sc.nextLine();
								if (newPassword.equals(reenterPassword)) {
									dealer = new DealerDAO(newPassword);
									if (DealerController.updatePassword(connection, dealer)) {
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

	public static void viewProfile(DealerDAO dealer) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Dealer UID        : " + dealer.getDealerId());
		System.out.println("--Dealer Name       : " + dealer.getDealerName());
		System.out.println("--MailId            : " + dealer.getDealerMailid());
		System.out.println("--Mobile Number     : " + dealer.getContactNumer());
		System.out.println("--Location          : " + dealer.getDealerLocation());
		System.out.println("-----------------------------------------------------------------");
	}

	public static void actionMenu() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --D -> Display Orders                 +"
						+ "\n+ --C -> Completed Orders               +"
						+ "\n+ --W -> Display Watches                +"
						+ "\n+ --V -> View Profile                   +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void showOrderDetails(String orders[]) {
		System.out.println("+----------------------------------------------------------------"
				+ "\n+ --Order Id               : " + orders[0]
				+ "\n+ --User Id                : " + orders[1]
				+ "\n+ --Dealer Id              : " + orders[2]
				+ "\n+ --Watch Id               : " + orders[3]
				+ "\n+ --Order date             : " + orders[4]
				+ "\n+ --Delivery Date          : " + orders[5]
				+ "\n+ --Quantity               : " + orders[6]
				+ "\n+ --Price                  : " + orders[7]
				+ "\n+ --Payment Mode           : " + orders[8]
				+ "\n+ --Status                 : " + orders[9]
				+ "\n+----------------------------------------------------------------");
	}

	public static void profile(String[] profile) {
		String s = "                      ";
		System.out.println("+----------------------------------------------------------------");
		System.out.println("+ --Name              : " + profile[0]);
		System.out.println("+ --Mobile Number     : " + profile[1]);
		System.out.println("+ --Email             : " + profile[2]);
		displayAddress(profile[3], s);
		System.out.println("+----------------------------------------------------------------");
	}

	public static void profileAction() {
		System.out.print(
				"+---------------------------------------+"
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

	public static void orderActions() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --1 -> Order is Ready for Shippig     +"
						+ "\n+ --2 -> Order is Shipped               +"
						+ "\n+ --3 -> On Hold                        +"
						+ "\n+ --4 -> Reached Your Region            +"
						+ "\n+ --5 -> Delivered                      +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void displayAddress(String s, String dec) {
		String address[] = s.split(",");
		System.out.print("+ --Location          : ");
		for (int i = 0; i < address.length; i++) {
			if (i != address.length - 1 && i != 0) {
				System.out.print(dec + "" + address[i].trim() + ",\n");
			} else if (i == 0)
				System.out.print(address[i].trim() + ",\n");
			else
				System.out.print(dec + "" + address[i].trim() + ".\n");
		}
	}

	public static void displayOrders(List<OrderDAO> orders) {
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

	public static void noItemStatement() {
		System.out.println("+------------------------------------------+");
		System.out.println("+          No Orders to Display            +");
		System.out.println("+------------------------------------------+");
	}

	public static void noCompletedItemStatement() {
		System.out.println("+------------------------------------------+");
		System.out.println("+     No Completed Orders to Display       +");
		System.out.println("+------------------------------------------+");
	}

	public static void updateStatus() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --U -> Update Status                  +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void displayWatches(String id, String name, String brand, double price, String description,
			int number_of_stocks) {
		System.out.println("-------------------------------------------------------------------");
		System.out.println(
				"--ID                             : " + id
						+ "\n--Name                           : " + name
						+ "\n--Brand                          : " + brand
						+ "\n--Price                          : " + price
						+ "\n--Description                    : " + description
						+ "\n--Number of Stocks available     : " + number_of_stocks);
	}

	public static void displayDealer(List<DealerDAO> list) {
		Iterator<DealerDAO> itr = list.iterator();
		while (itr.hasNext()) {
			DealerDAO dealer = itr.next();
			System.out.println("-------------------------------------------------------------------");
			System.out.println(
					"--Dealer ID                      : " + dealer.getDealerId()
							+ "\n--Name                           : " + dealer.getDealerName()
							+ "\n--Dealer Location                : " + dealer.getDealerLocation()
							+ "\n--Contact Number                 : " + dealer.getContactNumer()
							+ "\n--Mail Id                        : " + dealer.getDealerMailid());
		}
	}

	public static DealerDAO editDealerProfile(Connection con, Scanner sc) {

		while (true) {
			System.out.print("Enter Dealer Name     : ");
			dealerName = sc.nextLine().trim() + sc.nextLine();
			if (Validation.validateName(dealerName))
				break;
			else
				System.out.println("Enter a Valid name");
		}

		while (true) {
			System.out.print("Enter your Location   :");
			dealerLocation = sc.nextLine();
			if (!dealerLocation.isEmpty())
				break;
			else
				System.out.println("Address cannot be empty");
		}

		while (true) {
			System.out.print("Enter mobile Number   : ");
			contactNumber = sc.nextLine();
			if (Validation.validateMobileNumber(contactNumber))
				break;
			else
				System.out.println("Enter a valid Mobile Numebr");
		}

		while (true) {
			System.out.print("Enter your Email Id   : ");
			dealerMailId = sc.nextLine();
			if (Validation.validateEmail(dealerMailId))
				break;
			else
				System.out.println("Enter a valid emailId");
		}

		return new DealerDAO(dealerName, dealerLocation, contactNumber, dealerMailId);
	}
}
