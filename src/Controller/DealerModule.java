package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Main.MainModule;
import Model.DBOperations.*;
import View.DealerInterface;
import Model.*;

public class DealerModule {

	private static List<String> profile = CheckFromDB.profileList();

	private static String dealerName;
	private static String dealerLocation;
	private static String contactNumber;
	private static String dealerMailId;
	private static String dealerPassword;
    
    public static char getWhatToDo(Scanner sc) {
        return sc.next().charAt(0);
    }
    public static void dealerTask(Connection con, String[] args, Scanner sc, char operation) throws SQLException {
        switch(operation) {
            case 'S':
			case 's': {
				showOrders(con);
				break;
			}

			case 'U':
			case 'u': {
				// updateWatchDetails(con, sc);
				break;
			}

            case 'V':
			case 'v': {
				viewDealerProfile(con, args, sc, operation);
				break;
			}

			case 'E':
			case 'e': {
				editDealerProfile(con, sc);
				break;
			}

			case 'P':
			case 'p': {
				changePassword(con, sc);
				break;
			}

			case 'B':
			case 'b': {
				DealerInterface.dealerInterface(con, args, sc, operation);
			}

			case 'L':
			case 'l': {
				logOut(con, args);
				break;
			}
        }
    }

    public static void showOrders(Connection con) {

        try {
            FetchAndDisplayFromDB.viewOrders(con, 0); 
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void viewDealerProfile(Connection con, String[] args, Scanner sc, char operation) {
		try {
			String profileDealer[] = FetchAndDisplayFromDB.displayProfile(con).split("_");
			DealerInterface.displayDealers(profileDealer);

			while (true) {
				DealerInterface.profileAction();
				operation = getWhatToDo(sc);
				dealerTask(con, args, sc, operation);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
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

	public static void editDealerProfile(Connection con, Scanner sc) {

		while (true) {
			System.out.print("Enter Dealer Name     : ");
			dealerName = sc.next().trim() + sc.nextLine();
			if (Validation.validateName(dealerName))
				break;
			else
				System.out.println("Enter a Valid name");
		}

		while(true) {
			System.out.print("Enter your Location   :");
			dealerLocation = sc.nextLine();
			if(!dealerLocation.isEmpty())
				break;
			else	
				System.out.println("Address cannot be empty");
		}

		while (true) {
			System.out.print("Enter mobile Number   : ");
			contactNumber = sc.nextLine();
			if (Validation.validateMobileNumber(contactNumber))
				break;
			else
				System.out.println("Enter a valid Mobile Numebr");
		}

		while (true) {
			System.out.print("Enter your Email Id   : ");
			dealerMailId = sc.nextLine();
			if (Validation.validateEmail(dealerMailId))
				break;
			else
				System.out.println("Enter a valid emailId");
		}

		while (true) {
			System.out.print("Enter your Password   : ");
			dealerPassword = sc.nextLine();
			if (Validation.validatePassword(dealerPassword)) {
				if(profile.get(1).equals(dealerPassword))
					break;
			}
			else
				System.out.println("Password is Incorrect");
		}

		try {
			UpdateInDB.updateProfile(con, dealerName, contactNumber, dealerMailId, dealerLocation);
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

}
