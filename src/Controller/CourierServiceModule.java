package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Main.MainModule;
import Model.DBOperations.*;
import Model.*;

import View.CourierServiceInterface;

public class CourierServiceModule {

	private static List<String> profile = CheckFromDB.profileList();

	private static String courierServiceName;
	private static String courierServiceNumber;
	private static String couriermailId;
	private static String courierPasword;

	public static char getWhatToDo(Scanner sc) {
		return sc.next().charAt(0);
	}

	public static void courierServiceTask(Connection con, String[] args, Scanner sc, char operation)
			throws SQLException {
		switch (operation) {
			case 'D':
			case 'd': {
				showOrders(con);
				break;
			}

			case 'C':
			case 'c': {
				showDeliveredOrders(con);
				break;
			}

			case 'V':
			case 'v': {
				viewCourierServiceProfile(con, args, sc, operation);
				break;
			}

			case 'E':
			case 'e': {
				editCourierServiceProfile(con, sc);
				break;
			}

			case 'P':
			case 'p': {
				changePassword(con, sc);
				break;
			}

			case 'B':
			case 'b': {
				CourierServiceInterface.courierServiceInterface(con, args, sc, 1);
			}

			case 'L':
			case 'l': {
				logOut(con, args);
				break;
			}
		}
	}

	public static void showDeliveredOrders(Connection connection) {
		try {
			FetchAndDisplayFromDB.finishedOrder(connection, "Delivered");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void showOrders(Connection con) {

		try {
			FetchAndDisplayFromDB.viewOrders(con);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void viewCourierServiceProfile(Connection con, String[] args, Scanner sc, char operation) {
		try {
			String profile[] = FetchAndDisplayFromDB.displayProfile(con).split("_");
			CourierServiceInterface.courierDetailDisplay(profile);

			while (true) {
				CourierServiceInterface.profileAction();
				operation = getWhatToDo(sc);
				courierServiceTask(con, args, sc, operation);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void editCourierServiceProfile(Connection con, Scanner sc) {

		while (true) {
			System.out.print("Enter Your Name     : ");
			courierServiceName = sc.next().trim() + sc.nextLine();
			if (Validation.validateName(courierServiceName))
				break;
			else
				System.out.println("Enter a Valid name");
		}

		while (true) {
			System.out.print("Enter mobile Number : ");
			courierServiceNumber = sc.nextLine();
			if (Validation.validateMobileNumber(courierServiceNumber))
				break;
			else
				System.out.println("Enter a valid Mobile Numebr");
		}

		while (true) {
			System.out.print("Enter your Email Id : ");
			couriermailId = sc.nextLine();
			if (Validation.validateEmail(couriermailId))
				break;
			else
				System.out.println("Enter a valid emailId");
		}

		while (true) {
			System.out.print("Enter your Password : ");
			courierPasword = sc.nextLine();
			if (Validation.validatePassword(courierPasword)) {
				if (profile.get(1).equals(courierPasword))
					break;
			} else
				System.out.println("Password is Incorrect");
		}

		try {
			UpdateInDB.updateProfile(con, courierServiceName, courierServiceNumber, courierPasword, "");
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
