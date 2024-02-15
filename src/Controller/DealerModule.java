package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Main.MainModule;
import Model.DBOperations.*;
import View.DealerInterface;
import Model.*;

public class DealerModule {

	private static List<String> profile = CheckFromDB.profileList();

	private static String dealerId;
	private static String dealerName;
	private static String dealerLocation;
	private static String contactNumber;
	private static String dealerMailId;
	private static String dealerPassword;

	// Variables for Watch Details
	private static String watchId;
	private static String seriesName;
	private static String brand;
	private static double price;
	private static String description;
	private static int numberOfStocks;

	private static String status;
    
    public static char getWhatToDo(Scanner sc) {
        return sc.next().charAt(0);
    }
    public static void dealerTask(Connection con, String[] args, Scanner sc, char operation) throws SQLException {
        switch(operation) {
            case 'S':
			case 's': {
				showOrders(con, args, sc);
				break;
			}

			case 'C':
			case 'c': {
				showDeliveredOrders(con, sc);
				break;
			}

			case 'A':
			case 'a': {
				insertWatches(con, args, sc, operation);
				break;
			}

            case 'V':
			case 'v': {
				viewDealerProfile(con, args, sc, operation);
				break;
			}

			case 'E':
			case 'e': {
				editDealerProfile(con, sc);
				break;
			}

			case 'P':
			case 'p': {
				changePassword(con, sc);
				break;
			}

			case 'B':
			case 'b': {
				DealerInterface.dealerInterface(con, args, sc, operation);
			}

			case 'L':
			case 'l': {
				logOut(con, args);
				break;
			}
        }
    }

public static void showDeliveredOrders(Connection connection, Scanner sc) {

}

	public static void optionsForOrders(Connection con, String[] args, Scanner sc) throws SQLException {
		System.out.print("Enter the Orderid to Update the order Status :");
		String orderId = sc.next();
		System.out.print("Enter the valid option to update the Order status : ");
		char ch = sc.next().charAt(0);
		while(true) {
			DealerInterface.orderActions();
			if(ch == '1') {
				status = "Order Ready For Shipment";
			} else if(ch == '2'){
				status = "Your Order is Shipped";
			} else if(ch == '3') {
				status = "Your Order Reached Your region";
			} else if(ch == '4') {
				status = "Delivered";
			} else if(ch == '5') {
				status = "On Hold";
			} else if (ch == 'B' || ch == 'b') {
				DealerInterface.dealerInterface(con, args, sc, ch);
			} else
				System.out.println("Enter a valid Option");
			updateStatusInDb(con, status, orderId);
		}
	}

	public static void updateStatusInDb(Connection con, String status, String orderId) {
		try{
			UpdateInDB.updateOrderStatus(con, status, orderId);
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
    public static void showOrders(Connection con, String[] args, Scanner sc) {
        try {
            FetchAndDisplayFromDB.viewOrders(con, 0);
			optionsForOrders(con, args, sc);

        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void viewDealerProfile(Connection con, String[] args, Scanner sc, char operation) {
		try {
			String profileDealer[] = FetchAndDisplayFromDB.displayProfile(con).split("_");
			DealerInterface.displayDealers(profileDealer);

			while (true) {
				DealerInterface.profileAction();
				operation = getWhatToDo(sc);
				dealerTask(con, args, sc, operation);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void logOut(Connection con, String[] args) throws SQLException {

		try {
			DeleteFromDB.clearProfile(con);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		System.out.println("Logging out. GoodBye!");
		System.out.println("-----------------------------------------------------------------");
		MainModule.main(args);
	}

	public static void editDealerProfile(Connection con, Scanner sc) {

		while (true) {
			System.out.print("Enter Dealer Name     : ");
			dealerName = sc.next().trim() + sc.nextLine();
			if (Validation.validateName(dealerName))
				break;
			else
				System.out.println("Enter a Valid name");
		}

		while(true) {
			System.out.print("Enter your Location   :");
			dealerLocation = sc.nextLine();
			if(!dealerLocation.isEmpty())
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

		while (true) {
			System.out.print("Enter your Password   : ");
			dealerPassword = sc.nextLine();
			if (Validation.validatePassword(dealerPassword)) {
				if(profile.get(1).equals(dealerPassword))
					break;
			}
			else
				System.out.println("Password is Incorrect");
		}

		try {
			UpdateInDB.updateProfile(con, dealerName, contactNumber, dealerMailId, dealerLocation);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void changePassword(Connection con, Scanner sc) {

		while (true) {
			System.out.print("Enter your currrent password : ");
			String currentPassword = sc.next().trim();
			sc.nextLine();
			try {
				if (CheckFromDB.checkPassword(con, currentPassword)) {
					while (true) {
						System.out.print("Enter new Password           : ");
						String newPassword = sc.next();
						sc.nextLine();
						if (!newPassword.equals(currentPassword) && Validation.validatePassword(newPassword)) {
							while (true) {
								System.out.print("Re-enter new Password        : ");
								String reenterPassword = sc.nextLine();
								if (newPassword.equals(reenterPassword)) {
									try {
										UpdateInDB.changePassword(con, newPassword);
									} catch (Exception e) {
										System.out.println(e.toString());
									}
									break;
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

	public static void insertWatches(Connection con, String[] args, Scanner sc, char operation) throws SQLException {
		Watch newWatch = getNewWatchDetails(con, sc);

		if (con != null) {
			try {
				InsertInDB.insertNewWatch(con, newWatch.getWatchId(), newWatch.getName(), newWatch.getBrand(),
						newWatch.getPrice(), newWatch.getDescription(), newWatch.getNumberOfStocks(), newWatch.getDealerId());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static Watch getNewWatchDetails(Connection con, Scanner sc) throws SQLException {
		System.out.println("------Enter the watch details to be added:------");

		watchId = UIDGenerator.IdGenerator(con, "watches");

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
		while(true) {
			dealerId = sc.next();
			if(!dealerId.isEmpty())
				break;
			else	
				System.out.println("Enter the Dealer ID for Inserting Watch");
		}

		return new Watch(watchId, seriesName, brand, price, description, numberOfStocks, dealerId);
	}
}
