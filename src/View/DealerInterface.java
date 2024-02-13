package View;

import java.sql.Connection;
import java.util.Scanner;

import Controller.AdminModule;

public class DealerInterface {

    public static void dealerInterface(Connection con, String[] args, Scanner sc, int entry) {
        while (true) {
            actionMenu();
            char operation = AdminModule.getWhatToDo(sc);
            AdminModule.performAdminTask(con, args, sc, operation);
        }
    }

    public static void displayDealers(String profile[]) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("--Dealer UID        : " + profile[0]);
        System.out.println("--Dealer Name       : " + profile[1]);
        System.out.println("--Mobile Number     : " + profile[2]);
        System.out.println("--Location          : " + profile[3]);
        System.out.println("--MailId            : " + profile[4]);
        System.out.println("-----------------------------------------------------------------");
    }

    public static void actionMenu() {
        System.out.print(
                "\n  _________________________________"
                        + "\n|| Enter                           ||"
                        + "\n|| U -> Update Shipment Status     ||"
                        + "\n|| S -> Show Orders To Deliver     ||"
                        + "\n||_________________________________||"
                        + "\n---------> ");
    }

    public static void showOrderDetails(String orders[]) {
        System.out.println("-----------------------------------------------------------------");
		System.out.println("--Order Id               : " + orders[0]);
		System.out.println("--User Id                : " + orders[1]);
		System.out.println("--Watch Id               : " + orders[2]);
		System.out.println("--Order date             : " + orders[3]);
		System.out.println("--Delivery Date          : " + orders[4]);
		System.out.println("--Quantity               : " + orders[5]);
		System.out.println("--Price                  : " + orders[6]);
		System.out.println("--Payment Mode           : " + orders[7]);
		System.out.println("--Status                 : " + orders[8]);
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
}
