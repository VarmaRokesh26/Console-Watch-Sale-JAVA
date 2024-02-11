package Controller;

import java.sql.Connection;
import java.util.*;
import Model.*;
import View.AdminInterface;
import View.MainModule;

public class AdminModule {
	public static String sname;
	public static String brand;
	public static double price;
	public static String description;
	public static int number_of_stocks;

	private static String name;
	private static String mobileNumber;
	private static String mailId;
	private static String password;
	private static String confirmPassword;
	private static String address = "Nil";

	private static int wId;

	public static char getWhatToDo(Scanner sc) {
		return sc.next().charAt(0);
	}

	// New watch Details inserting logic
	public static Watch getNewWatchDetails(Scanner sc) {
		System.out.println("------Enter the watch details to be added:------");

		while (true) {
			System.out.print("Enter series Name                 :");
			sname = sc.next() + sc.nextLine();
			if (!sname.equals(""))
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
			number_of_stocks = sc.nextInt();
			sc.nextLine();
			if (number_of_stocks >= 0)
				break;
		}

		return new Watch(sname, brand, price, description, number_of_stocks);
	}

	public static int getIdToUpdateOrDelete(Scanner sc) {

		while (true) {
			System.out.print("Enter the required Id to update : ");
			wId = sc.nextInt();
			if (wId != 0)
				break;
			else
				System.out.println("Enter the valid Id");
		}
		return wId;
	}

	public static Watch getwacthDetailsToUpdate(Scanner sc) {
		System.out.println("------Enter the watch details to be added:------");

		while (true) {
			System.out.print("Enter series Name                 :");
			sname = sc.next().trim() + sc.nextLine();
			if (!sname.equals(""))
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
			number_of_stocks = sc.nextInt();
			sc.nextLine();
			if (number_of_stocks >= 0)
				break;
		}

		return new Watch(sname, brand, price, description, number_of_stocks);
	}

	public static void performAdminTask(Connection con, String[] args, Scanner sc, char operation) {
		
		switch (operation) {

			case 'I':
			case 'i': {
				insertWatches(con, sc);
				break;
			}

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
				displayWatches(con);
				break;
			}

			case 'A':
			case 'a': {
				addNewAdmin(con, sc);
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
				
				break;
			}

			case 'B':
			case 'b': {
				AdminInterface.adminInterface(con, args, sc, 1);
			}

			case 'L':
			case 'l': {
				logOut(con, args);
				break;
			}
		}
	}

	public static void insertWatches(Connection con, Scanner sc) {
		Watch newWatch = getNewWatchDetails(sc);

		if (con != null) {
			try {
				DBOperations.insertNewWatch(con, newWatch.getName(), newWatch.getBrand(),
						newWatch.getPrice(), newWatch.getDescription(), newWatch.getNumberOfStocks());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void updateWatchDetails(Connection con, Scanner sc) {
		wId = AdminModule.getIdToUpdateOrDelete(sc);

		Watch toUpdate = AdminModule.getNewWatchDetails(sc);
		if (con != null && wId != 0) {
			try {
				System.out.println("Enter Watch series name, brand, price, number of stocks");
				DBOperations.updateWatch(con, toUpdate.getName(), toUpdate.getBrand(),
						toUpdate.getPrice(), toUpdate.getDescription(), toUpdate.getNumberOfStocks(), wId);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void deleteWatch(Connection con, Scanner sc) {
		wId = AdminModule.getIdToUpdateOrDelete(sc);

		if (con != null && wId != 0) {
			try {
				DBOperations.deleteWatch(con, wId);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void deletemanyWatches(Connection con) {
		try {
			DBOperations.deleteWatchByNOS(con);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void displayWatches(Connection con) {
		System.out.println();

		if (con != null) {
			try {
				DBOperations.selectAllWatches(con, -1);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public static void addNewAdmin(Connection con, Scanner sc) {

		while (true) {
			System.out.print("Name                : ");
			name = sc.next() + sc.nextLine();
			if (Validation.validateName(name))
				break;
			else
				System.out.println("Enter a valid Name");
		}

		while (true) {
			System.out.print("Enter Mobile Number : ");
			mobileNumber = sc.nextLine();
			if (Validation.validateMobileNumber(mobileNumber))
				break;
			else
				System.out.println("<---Enter a valid mobile Number--->");
		}

		while (true) {
			System.out.print("Enter Email Address : ");
			mailId = sc.nextLine();
			if (Validation.validateEmail(mailId))
				break;
			else
				System.out.println("<---Enter a valid Email--->");
		}

		while (true) {
			System.out.print("Enter Password      : ");
			password = sc.nextLine();
			if (Validation.validatePassword(password)) {
				while (true) {
					System.out.print("Re-enter Password   : ");
					confirmPassword = sc.nextLine();
					if (password.equals(confirmPassword)) {
						if (con != null) {
							try {
								DBOperations.insertAdminDetails(con, name, mobileNumber, mailId, confirmPassword);
							} catch (Exception e) {
								System.out.println(e.toString());
							}
						}
						break;
					}
				}
				break;
			} else
				System.out.println(
						"<---Password should contain at least 8 characters, 1 UpperCase, 1 LowerCase, 1 Numbers, 1 SpecialCharacters--->");
		}
	}

	public static void viewAdminProfile(Connection con, String[] args, Scanner sc, char operation) {
		try {
			String profile[] = DBOperations.displayProfile(con).split("_");
			AdminInterface.profile(profile);

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
		try {
			DBOperations.updateProfile(con, name, mobileNumber, mailId, address);
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
				if (DBOperations.checkPassword(con, currentPassword)) {
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
										DBOperations.changePassword(con, newPassword);
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

	public static void logOut(Connection con, String[] args) {

		try {
			DBOperations.clearProfile(con);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println("Logging out. GoodBye!");
		System.out.println("-----------------------------------------------------------------");
		MainModule.main(args);
	}
}