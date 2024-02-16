package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import Controller.DealerModule;

public class DealerInterface {

	public static void dealerInterface(Connection con, String[] args, Scanner sc, int entry) throws SQLException {
		while (true) {
			actionMenu();
			char operation = DealerModule.getWhatToDo(sc);
			DealerModule.dealerTask(con, args, sc, operation);
		}
	}

	public static void displayDealers(String profileDealers[]) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Dealer UID        : " + profileDealers[0]);
		System.out.println("--Dealer Name       : " + profileDealers[1]);
		System.out.println("--MailId            : " + profileDealers[3]);
		System.out.println("--Mobile Number     : " + profileDealers[2]);
		System.out.println("--Location          : " + profileDealers[4]);
		System.out.println("-----------------------------------------------------------------");
	}

	public static void actionMenu() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --A -> Add New Watch                  +"
						+ "\n+ --D -> Display Orders                 +"
						+ "\n+ --C -> Completed Orders               +"
						+ "\n+ --V -> View Profile                   +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void showOrderDetails(String orders[]) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Order Id               : " + orders[0]);
		System.out.println("--User Id                : " + orders[1]);
		System.out.println("--Dealer Id              : " + orders[2]);
		System.out.println("--Watch Id               : " + orders[3]);
		System.out.println("--Order date             : " + orders[4]);
		System.out.println("--Delivery Date          : " + orders[5]);
		System.out.println("--Quantity               : " + orders[6]);
		System.out.println("--Price                  : " + orders[7]);
		System.out.println("--Payment Mode           : " + orders[8]);
		System.out.println("--Status                 : " + orders[9]);
		System.out.println("-----------------------------------------------------------------");
	}

	public static void profile(String[] profile) {
		String s = "                      ";
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Name              : " + profile[0]);
		System.out.println("--Mobile Number     : " + profile[1]);
		System.out.println("--Email             : " + profile[2]);
		displayAddress(profile[3], s);
		System.out.println("-----------------------------------------------------------------");
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
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void displayAddress(String s, String dec) {
		String address[] = s.split(",");
		System.out.print("--Location          : ");
		for (int i = 0; i < address.length; i++) {
			if (i != address.length - 1 && i != 0) {
				System.out.print(dec + "" + address[i].trim() + ",\n");
			} else if (i == 0)
				System.out.print(address[i].trim() + ",\n");
			else
				System.out.print(dec + "" + address[i].trim() + ".\n");
		}
	}

	public static void noItemStatement() {
		System.out.println("+------------------------------------------+");
		System.out.println("+          No Orders to Display            +");
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
}
