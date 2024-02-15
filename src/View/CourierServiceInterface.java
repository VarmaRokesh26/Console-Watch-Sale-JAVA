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
						+ "\n+ U -> Update Delivery Status           +"
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
		System.out.println("-----------------------------------------------------------------");
		System.out.println("--Order Id               : " + orders[0]);
		System.out.println("--User Id                : " + orders[1]);
		System.out.println("--Watch Id               : " + orders[2]);
		System.out.println("--Order date             : " + orders[3]);
		System.out.println("--Expected Date          : " + orders[4]);
		System.out.println("--Quantity               : " + orders[5]);
		System.out.println("--Price                  : " + orders[6]);
		System.out.println("--Payment Mode           : " + orders[7]);
		System.out.println("--Status                 : " + orders[8]);
		System.out.println("-----------------------------------------------------------------");
	}
}
