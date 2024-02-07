package View;

import java.sql.Connection;
import java.util.*;

import Controller.*;

public class UserInterface {
	
	public static void userInterface(Connection connection, String[] args, Scanner sc, int entry) {
		if(entry==0)
			welcomeMsg();
		
		while(true) {
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
				  "\n  _________________________________"
				+ "\n|| Enter                           ||"
				+ "\n|| D -> Display Watches            ||"
				+ "\n|| O -> Place Order                ||"
				+ "\n|| H -> History of watches         ||"
				+ "\n|| C -> View Cart                  ||"
				+ "\n|| V -> View Profile               ||"
				+ "\n||_________________________________||"
				+ "\n---------> ");
	}
	
//	public static void placeOrderRemoveFromCart() {
//		System.out.println(
//				  "\n  _________________________________"
//				+ "\n|| Enter                           ||"
//				+ "\n|| O -> Place Order                ||"
//				+ "\n|| B -> Back                       ||"
//				+ "\n||_________________________________||"
//				+"\n--------->");
//	}
	
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
	
	public static void orderOrCart() {
		System.out.print(
				    "  _________________________________"
			    + "\n|| Enter                           ||"
			    + "\n|| O -> Place Order                ||"
			    + "\n|| A -> Add to Cart                ||"
			    + "\n|| B -> Back                       ||"
			    + "\n||_________________________________||"
			    + "\n---------> ");
	}
	
	public static void profile(String[] profile) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Name              : "+profile[0]);
		System.out.println("--Mobile Number     : "+profile[1]);
		System.out.println("--Email             : "+profile[2]);
		System.out.println("--Address           : "+profile[3]);
		System.out.println("-----------------------------------------------------------------");
	}
	
	public static void displayWatches(int id, String name, String brand, double price, String description, int number_of_stocks) {
		System.out.println("**************************************************************");
    	System.out.println(
    			    "--ID                             : " + id
    			+ "\n--Name                           : " + name
    			+ "\n--Brand                          : " + brand
    			+ "\n--Price                          : " + price
    			+ "\n--Description                    : " + description
    			+ "\n--Number of Stocks available     : " + number_of_stocks);
    	System.out.println("**************************************************************");
    }
	public static void shoppingCart(String cart[]) {
		double price = Double.parseDouble(cart[2]);
		int quantity = Integer.parseInt(cart[3]);
		System.out.println("**************************************************************");
		System.out.println("Id : " + cart[0] +"|| Series Name : "+cart[1]+"|| Quantity : "+ quantity +"||Price : "+price*quantity);
	}
}
