package View;

import java.sql.*;
import java.util.*;
import Controller.*;

public class AdminInterface {

	public static void adminInterface(Connection con, String[] args, Scanner sc, int entry) {

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
				"\n  _________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| I -> Insert watches             ||"
						+ "\n|| U -> Update details of watches  ||"
						+ "\n|| D -> Delete watch               ||"
						+ "\n|| C -> Delete Watch with 0 stocks ||"
						+ "\n|| S -> Display the list           ||"
						+ "\n|| A -> Add new Admin              ||"
						+ "\n|| V -> View Profile               ||"
						+ "\n||_________________________________||"
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
		System.out.println("--Name              : " + profile[0]);
		System.out.println("--Mobile Number     : " + profile[1]);
		System.out.println("--Email             : " + profile[2]);
		System.out.println("--Role              : " + profile[3]);
		System.out.println("-----------------------------------------------------------------");
	}

	public static void displayWatches(int id, String name, String brand, double price, String description,
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
	
}
