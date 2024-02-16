package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import Controller.CourierServiceModule;

public class CourierServiceInterface {

	public static void courierServiceInterface(Connection con, String[] args, Scanner sc, int entry)
			throws SQLException {
		while (true) {
			actionMenu();
			char operation = CourierServiceModule.getWhatToDo(sc);
			CourierServiceModule.courierServiceTask(con, args, sc, operation);
		}
	}

	public static void courierDetailDisplay(String profile[]) {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Courier Service UID    : " + profile[0]);
		System.out.println("--Courier Service Name   : " + profile[1]);
		System.out.println("--Contact Number         : " + profile[2]);
		System.out.println("--Email ID               : " + profile[3]);
		System.out.println("-----------------------------------------------------------------");
	}

	public static void actionMenu() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ C -> Orders Delivered                 +"
						+ "\n+ S -> Show Orders To Deliver           +"
						+ "\n+ V -> View Profile                     +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}

	public static void profileAction() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ V -> View Profile                     +"
						+ "\n+ E -> Edit Profile                     +"
						+ "\n+ P -> Change Password                  +"
						+ "\n+ B -> Back                             +"
						+ "\n+ L -> Logout                           +"
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

	public static void noItemStatement() {
		System.out.println("+------------------------------------------+");
		System.out.println("+          No Orders to Deliver            +");
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

	public static void orderActions() {
		System.out.print(
				"+---------------------------------------+"
						+ "\n+ Enter                                 +"
						+ "\n+---------------------------------------+"
						+ "\n+ --1 -> Order Reached Your Region      +"
						+ "\n+ --2 -> Delivered                      +"
						+ "\n+ --B -> Back                           +"
						+ "\n+---------------------------------------+"
						+ "\n---------> ");
	}
}
