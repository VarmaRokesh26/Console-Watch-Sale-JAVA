package View;

import java.sql.*;
import java.util.*;
import Controller.*;

public class AdminInterface {

	public static void adminInterface(Connection con, String[] args, Scanner sc, int entry) throws SQLException {

		if (entry == 0)
			welcomeMsg();
		while (true) {
			actionMenu();
			char operation = AdminModule.getWhatToDo(sc);
			AdminModule.performAdminTask(con, args, sc, operation);
		}
	}

	public static void welcomeMsg() {
		System.out.print("\n---------Welcome back admin---------");
	}

	public static void actionMenu() {
		System.out.print(
				"\n  ____________________________________________________"
						+ "\n|| Enter                                              ||"
						+ "\n|| A -> Add new Watch/ Admin/ Dealer/ CourierServices ||"
						+ "\n|| C -> Delete Watch with 0 stocks                    ||"
						+ "\n|| D -> Delete watch                                  ||"
						+ "\n|| S -> Display Watch/ Admin/ Dealer/ CourierServices ||"
						+ "\n|| U -> Update details of watches                     ||"
						+ "\n|| V -> View Profile                                  ||"
						+ "\n||____________________________________________________||"
						+ "\n---------> ");
	}

	public static void profileAction() {
		System.out.print(
				"  _________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| V -> View Profile               ||"
						+ "\n|| E -> Edit Profile               ||"
						+ "\n|| P -> Change Password            ||"
						+ "\n|| B -> Back                       ||"
						+ "\n|| L -> Logout                     ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}

	public static void profile(String[] profile) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Admin UID         : " + profile[0]);
		System.out.println("--Name              : " + profile[1]);
		System.out.println("--Mobile Number     : " + profile[2]);
		System.out.println("--Email             : " + profile[3]);
		System.out.println("--Role              : " + profile[4]);
		System.out.println("-----------------------------------------------------------------");
	}

	public static void displayWatches(String id, String name, String brand, double price, String description,
			int number_of_stocks) {
		System.out.println("******************************************************************");
		System.out.println(
				"--ID                             : " + id
						+ "\n--Name                           : " + name
						+ "\n--Brand                          : " + brand
						+ "\n--Price                          : " + price
						+ "\n--Description                    : " + description
						+ "\n--Number of Stocks available     : " + number_of_stocks);
	}

	public static void insertOption() {
		System.out.print(
				"  _________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| 1 -> Add New Watch              ||"
						+ "\n|| 2 -> Add New Admin              ||"
						+ "\n|| 3 -> Add New Dealer Details     ||"
						+ "\n|| 4 -> Add NEw Courier Service    ||"
						+ "\n|| B -> Back                       ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}

	public static void optionsForAdmin() {
		System.out.print(
				"  _________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| 1 -> Display Watches            ||"
						+ "\n|| 2 -> Display Admin              ||"
						+ "\n|| 3 -> Display Dealer List        ||"
						+ "\n|| 4 -> Display Courier List       ||"
						+ "\n|| B -> Back                       ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}
}
