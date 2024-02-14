package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import Controller.*;

public class UserInterface {

	public static void userInterface(Connection connection, String[] args, Scanner sc, int entry) throws SQLException {
		if (entry == 0)
			welcomeMsg();

		while (true) {
			actionMenu();
			char userOperation = UserModule.getWhatToDo(sc);
			UserModule.performUserTask(connection, args, sc, userOperation);
		}
	}

	public static void welcomeMsg() {
		System.out.println("Welcome TO Watch World!!");
	}

	public static void actionMenu() {
		System.out.print(
				"\n ___________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| D -> Display Watches            ||"
						+ "\n|| O -> Place Order                ||"
						+ "\n|| H -> History of Orders          ||"
						+ "\n|| C -> View Cart                  ||"
						+ "\n|| V -> View Profile               ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}

	public static void profileAction() {
		System.out.print(
				" ___________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| V -> View Profile               ||"
						+ "\n|| E -> Edit Profile               ||"
						+ "\n|| P -> Change Password            ||"
						+ "\n|| B -> Back                       ||"
						+ "\n|| L -> Logout                     ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}

	public static void orderOrCart() {
		System.out.print(
				" ___________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| N -> Buy Now                    ||"
						+ "\n|| A -> Add to Cart                ||"
						+ "\n|| B -> Back                       ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}

	public static void profile(String[] profile) {
		String s = "                      ";
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--User UID          : " + profile[0]);
		System.out.println("--Name              : " + profile[1]);
		System.out.println("--Mobile Number     : " + profile[2]);
		System.out.println("--Email             : " + profile[3]);
		displayAddress(profile[4], s);
		System.out.println("-----------------------------------------------------------------");
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

	public static void shoppingCart(String cartDetails) {
		String[] cart = cartDetails.split("_");
		double price = Double.parseDouble(cart[2]);
		int quantity = Integer.parseInt(cart[3]);
		System.out.println(
				"____________________________________________" +
						"\nItem Id     : " + cart[0]
						+ "\nSeries Name : " + cart[1]
						+ "\nQuantity    : " + quantity
						+ "\nPrice       : " + price);
	}

	public static void orderHistory(String order[]) {
		String s[] = order[3].split(" ");
		String t[] = order[2].split(" ");
		System.out.println(
				"____________________________________________" +
						"\nOrder Id       : " + order[0]
						+ "\nItem Id        : " + order[1]
						+ "\nOrdered Date   : " + t[0]
						+ "\nExpected Date  : " + s[0]
						+ "\nQuantity       : " + order[4]
						+ "\nStatus         : " + order[5]
						+ "\nPrice          : " + order[6]);				
	}

	public static void cartWork() {
		System.out.print(
				" ___________________________________"
						+ "\n|| Enter                           ||"
						+ "\n|| R -> Remove an Item in Cart     ||"
						+ "\n|| D -> Delete all item in Cart    ||"
						+ "\n|| B -> Back                       ||"
						+ "\n||_________________________________||"
						+ "\n---------> ");
	}

	public static void displayWatches(String id, String name, String brand, double price, String description,
			int number_of_stocks, String dealerName) {
		System.out.println("******************************************************************");
		System.out.println(
				"--ID                             : " + id
						+ "\n--Name                           : " + name
						+ "\n--Brand                          : " + brand
						+ "\n--Price                          : " + price
						+ "\n--Description                    : " + description
						+ "\n--Number of Stocks available     : " + number_of_stocks
						+ "\n--Dealer of the Watch            : " + dealerName);
	}

}
