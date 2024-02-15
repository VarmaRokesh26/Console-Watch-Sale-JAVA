package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import Model.*;
import View.AdminInterface;
import View.DealerInterface;
import Main.MainModule;
import Model.DBOperations.*;

public class AdminModule {

	private static List<String> profile = CheckFromDB.profileList();
	// Variables for Watch Details
	private static String watchId;
	private static String seriesName;
	private static String brand;
	private static double price;
	private static String description;
	private static int numberOfStocks;

	// Variables for Dealer Details
	private static String dealerId;
	private static String dealerName;
	private static String dealerLocation;
	private static String contactNumber;
	private static String dealerMailId;
	private static String dealerPassword;

	// Variables for Admin Details
	private static String adminId;
	private static String name;
	private static String mobileNumber;
	private static String mailId;
	private static String password;
	private static String confirmPassword;
	private static String adminRole;
	
	// variables for CourierService Details
	private static String courierServiceId;
	private static String courierServiceName;
	private static String courierServiceNumber;
	private static String couriermailId;
	private static String courierPasword;
	
	public static char getWhatToDo(Scanner sc) {
		return sc.next().charAt(0);
	}

	// New watch Details inserting logic
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

	public static String getIdToUpdateOrDelete(Scanner sc) {

		while (true) {
			System.out.print("Enter the required Id to update : ");
			watchId = sc.next();
			if (!watchId.isEmpty())
				break;
			else
				System.out.println("Enter the valid Id");
		}
		return watchId;
	}

	public static Watch getwacthDetailsToUpdate(Scanner sc) {
		System.out.println("------Enter the watch details to be added:------");

		while (true) {
			System.out.print("Enter series Name                 :");
			seriesName = sc.next().trim() + sc.nextLine();
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
				System.out.println("Enter a valid Dealer Id");
		}

		return new Watch(watchId, seriesName, brand, price, description, numberOfStocks, dealerId);
	}

	public static void performAdminTask(Connection con, String[] args, Scanner sc, char operation) throws SQLException {

		switch (operation) {

			case 'U':
			case 'u': {
				updateWatchDetails(con, sc);
				break;
			}

			case 'D':
			case 'd': {
				deleteWatch(con, sc);
				break;
			}

			case 'C':
			case 'c': {
				deletemanyWatches(con);
				break;
			}

			case 'S':
			case 's': {
				displayAsRequired(con, args, sc);
				break;
			}

			case 'A':
			case 'a': {
				addRespectiveDetails(con, args, sc, operation);
				break;
			}

			case 'V':
			case 'v': {
				viewAdminProfile(con, args, sc, operation);
				break;
			}

			case 'E':
			case 'e': {
				editAdminProfile(con, sc);
				break;
			}

			case 'P':
			case 'p': {
				changePassword(con, sc);
				break;
			}

			case 'B':
			case 'b': {
				AdminInterface.adminInterface(con, args, sc, operation);
			}
			
			case 'L':
			case 'l': {
				logOut(con, args);
				break;
			}

		}
	}

	public static void insertWatches(Connection con, String[] args, Scanner sc, char operation) throws SQLException {
		Watch newWatch = getNewWatchDetails(con, sc);

		if (con != null) {
			try {
				InsertInDB.insertNewWatch(con, newWatch.getWatchId(), newWatch.getName(), newWatch.getBrand(),
						newWatch.getPrice(), newWatch.getDescription(), newWatch.getNumberOfStocks(), newWatch.getDealerId());
				while (true) {
					addRespectiveDetails(con, args, sc, operation);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void updateWatchDetails(Connection con, Scanner sc) {
		watchId = getIdToUpdateOrDelete(sc);
		
		Watch toUpdate = getwacthDetailsToUpdate(sc);
		if (con != null && !watchId.isEmpty()) {
			try {
				System.out.println("Enter Watch series name, brand, price, number of stocks");
				UpdateInDB.updateWatch(con, toUpdate.getName(), toUpdate.getBrand(),
						toUpdate.getPrice(), toUpdate.getDescription(), toUpdate.getNumberOfStocks(), toUpdate.getDealerId(), watchId);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void deleteWatch(Connection con, Scanner sc) {
		watchId = getIdToUpdateOrDelete(sc);

		if (con != null && !watchId.isEmpty()) {
			try {
				DeleteFromDB.deleteWatch(con, watchId);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void deletemanyWatches(Connection con) {
		try {
			DeleteFromDB.deleteWatchByNOS(con);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void displayAsRequired(Connection con, String[] args, Scanner sc) throws SQLException {
		while (true) {
			AdminInterface.displayOptionsForAdmin();
			char sh = sc.next().charAt(0);
			if (sh == '1') {
				try {
					FetchAndDisplayFromDB.selectAllWatches(con, "-1");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else if (sh == '2') {
				displayAdminDealerCourier(con, Character.getNumericValue(sh));
			} else if (sh == '3') {
				displayAdminDealerCourier(con, Character.getNumericValue(sh));
			} else if (sh == '4') {
				displayAdminDealerCourier(con, Character.getNumericValue(sh));
			} else if (sh == 'B' || sh == 'b') {
				AdminInterface.adminInterface(con, args, sc, sh);
			} else
				System.out.println("Enter a valid Option");
		}
	}

	public static void addRespectiveDetails(Connection con, String[] args,Scanner sc, char operation) throws SQLException {
		while (true) {
			AdminInterface.insertOption();
			char add = sc.next().charAt(0);
			if (add == '1') {
				insertWatches(con, args, sc, operation);
				break;
			} else if (add == '2') {
				addNewAdmin(con, sc);
				break;
			} else if (add == '3') {
				addNewDealer(con, sc);
				break;
			} else if (add == '4') {
				addNewCourierService(con, sc);
				break;
			} else if (add == 'B' || add == 'b') {
				AdminInterface.adminInterface(con, null, sc, add);
			} else
				System.out.println("Enter a valid Option");
		}
	}

	public static void displayAdminDealerCourier(Connection con, int sh) {
		try {
			FetchAndDisplayFromDB.displayAdminDealerCourierDB(con, sh);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void addNewAdmin(Connection con, Scanner sc) throws SQLException {
		System.out.println("<---Enter the Details of the Employee to Add as Admin--->");
		
		adminId = UIDGenerator.IdGenerator(con, "admin");

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
		Admin admin = new Admin(name, adminId, mobileNumber, mailId, adminRole, confirmPassword);
		if (con != null) {
			try {
				InsertInDB.insertAdminDetails(con, admin.getAdminId(), admin.getAdminName(), admin.getAdminMobileNumber(),
						admin.getAdminMailid(), admin.getAdminRole(), admin.getPassword());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void addNewDealer(Connection connection, Scanner sc) throws SQLException {

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

		Dealer dealer = new Dealer(dealerId, dealerName, dealerMailId, dealerLocation, contactNumber, dealerPassword);

		try {
			InsertInDB.insertNewDealerDetails(connection, dealer.getDealerId(), dealer.getDealerName(), dealer.getDealerLocation(),
					dealer.getContactNumer(), dealer.getDealerMailid(), dealer.getPassword());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void addNewCourierService(Connection connection, Scanner sc) throws SQLException {
		
		courierServiceId = UIDGenerator.IdGenerator(connection, "courierservice");

		while (true) {
			System.out.print("Enter Courier Service Name  :");
			courierServiceName = sc.next() + sc.nextLine();
			if (Validation.validateName(courierServiceName))
				break;
			else
				System.out.println("Enter a valid Name");
		}

		while (true) {
			System.out.print("Enter Contact Number        : ");
			courierServiceNumber = sc.nextLine();
			if (Validation.validateMobileNumber(courierServiceNumber))
				break;
			else
				System.out.println("Enter a valid Contact Number");
		}

		while (true) {
			System.out.print("Enter the Dealer MailId     : ");
			couriermailId = sc.nextLine();
			if (Validation.validateEmail(couriermailId))
				break;
			else
				System.out.println("<---Enter a valid Email--->");
		}

		while (true) {
			System.out.print("Enter Password              : ");
			courierPasword = sc.nextLine();
			if (Validation.validatePassword(courierPasword)) {
				while (true) {
					System.out.print("Re-enter Password           : ");
					confirmPassword = sc.nextLine();
					if (courierPasword.equals(confirmPassword)) {
						break;
					}
				}
				break;
			} else
				System.out.println(
						"<---Password should contain at least 8 characters, 1 UpperCase, 1 LowerCase, 1 Numbers, 1 SpecialCharacters--->");
		}

		CourierService courierService = new CourierService(courierServiceId, courierServiceName, courierServiceNumber, couriermailId,
				courierPasword);

		try {
			InsertInDB.insertNewCourierServiceDetails(connection, courierService.getCourierServiceId(), courierService.getCourierServiceName(),
					courierService.getCourierServiceNumber(), courierService.getCourierServiceMailId(),
					courierService.getPassword());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void viewAdminProfile(Connection con, String[] args, Scanner sc, char operation) {
		try {
			String profile[] = FetchAndDisplayFromDB.displayProfile(con).split("_");
			AdminInterface.viewProfile(profile);

			while (true) {
				AdminInterface.profileAction();
				operation = AdminModule.getWhatToDo(sc);
				performAdminTask(con, args, sc, operation);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void editAdminProfile(Connection con, Scanner sc) {

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

		while (true) {
			System.out.print("Enter your Password : ");
			password = sc.nextLine();
			if (Validation.validatePassword(password)) {
				if(profile.get(1).equals(password))
					break;
			}
			else
				System.out.println("Password is Incorrect");
		}

		try {
			UpdateInDB.updateProfile(con, name, mobileNumber, mailId, "");
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
}