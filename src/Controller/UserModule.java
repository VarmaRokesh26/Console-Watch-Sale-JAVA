package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import Model.*;
import Main.MainModule;
import View.UserInterface;
import Model.DBOperations.*;

public class UserModule {

	private static String dealerId; 

	private static String watchId;
	private static int quantity;
	private static String payment;
	private static double price;

	private static String userId;
	private static String name;
	private static String mobileNumber;
	private static String email;
	private static String address;
	private static String password;
	private static String confirmPassword;

	public static char getWhatToDo(Scanner sc) {
		return sc.next().charAt(0);
	}

	// Login logic
	public static Login getLoginDetails(Scanner sc) {

		while (true) {
			System.out.println("-----------------------------------------------------------------");
			System.out.print("Enter your EmailID : ");

			email = sc.nextLine();
			if (Validation.validateEmail(email)) {
				while (true) {
					System.out.print("Enter password     : ");
					password = sc.nextLine();
					if (Validation.validatePassword(password))
						return new Login(email, password);
					else
						System.out.println("\nEnter a Valid Password");
				}
			} else
				System.out.println("\nEnter a proper Email Address");
		}
	}

	// Sign Up logic
	public static SignUp getSignUpDetails(Connection con, Scanner sc) throws SQLException {

		userId = UIDGenerator.IdGenerator(con, "user");
		// System.out.println(userId);

		while (true) {
			// userName
			System.out.println("-----------------------------------------------------------");
			System.out.print("Enter Your Name : ");

			name = sc.nextLine();
			if (Validation.validateName(name))
				break;
			else
				System.out.println("<---Name should contains only Characters--->");
		}

		while (true) {
			// Mobile Number
			System.out.print("Enter Your Mobile Number : ");

			mobileNumber = sc.nextLine();
			if (Validation.validateMobileNumber(mobileNumber))
				break;
			else
				System.out.println("<---Mobile Number should should have 10 numbers--->");
		}

		while (true) {
			// Email Address
			System.out.print("Enter Your Email Address : ");

			email = sc.nextLine();
			if (Validation.validateEmail(email))
				break;
			else
				System.out.println("<---Invalid email--->");
		}

		while (true) {
			// Home Address to Delivery
			System.out.print("Enter Your Location(Address) : ");

			address = sc.nextLine();
			if (!address.toString().isEmpty())
				break;
			else
				System.out.println("Address should not be empty!");

		}
		while (true) {

			// Password
			System.out.print("Password : ");
			password = sc.nextLine();

			if (Validation.validatePassword(password)) {
				while (true) {
					System.out.print("Re-enter Password : ");
					confirmPassword = sc.nextLine();
					if (password.equals(confirmPassword))
						break;
					else
						System.out.println("<---Password doesn't match--->");
				}
				break;
			} else
				System.out.println("<---Password should contains atleast \n8 characters, \n1 UpperCase, "
						+ "\n1 LowerCase, \n1 Numbers, \n1 SpecialCharacters--->");
		}
		return new SignUp(userId, name, mobileNumber, email, address, confirmPassword);
	}

	// Perform User task
	public static void performUserTask(Connection connection, String[] args, Scanner sc, char operation) throws SQLException {
		switch (operation) {
			case 'S':
			case 's': {
				displayWatches(connection);
				break;
			}

			case 'O':
			case 'o': {
				buyItem(connection, args, sc, operation);
				break;
			}

			case 'N':
			case 'n': {
				orderNow(connection, sc);
				break;
			}

			case 'A':
			case 'a': {
				adddToCart(connection);
				break;
			}

			case 'H':
			case 'h': {
				history(connection);
				break;
			}
			case 'C':
			case 'c': {
				viewCart(connection, args, sc, operation);
				break;
			}

			case 'V':
			case 'v': {
				profileView(connection, args, sc, operation);
				break;
			}

			case 'E':
			case 'e': {
				editProfile(connection, sc);
				break;
			}

			case 'P':
			case 'p': {
				passwordChange(connection, sc);
				break;
			}

			case 'B':
			case 'b': {
				UserInterface.userInterface(connection, args, sc, 1);
			}

			case 'L':
			case 'l': {
				logOut(connection, args);
				break;
			}
		}
	}

	public static void orderNow(Connection connection, Scanner sc) {
		try {
			String wd = FetchAndDisplayFromDB.specificWatchDetail(connection, watchId, 1);
			String wd1[] = wd.split("_");
			price = Double.parseDouble(wd1[2]);
			dealerId = wd1[wd1.length-1];
			String orderId = UIDGenerator.IdGenerator(connection, "orders");
			// System.out.println(orderId);
			System.out.print("Payment Method Cash On Deliviry Or Online Payment : ");
			payment = sc.next();
			UpdateInDB.placeOrder(connection, orderId, watchId, quantity, price, payment, dealerId);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Method for Display Watches
	public static void displayWatches(Connection connection) {
		try {
			FetchAndDisplayFromDB.selectAllWatchesIfAvailable(connection);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Method for buy item
	public static void buyItem(Connection connection, String[] args, Scanner sc, char operation) {
		System.out.print("Enter the item Id to Display Seperatly : ");
		watchId = sc.next();
		try {
			if (CheckFromDB.checkWatchId(connection, watchId)) {
				try {
					FetchAndDisplayFromDB.specificWatchDetail(connection, watchId, 0);
					System.out.print("Enter Quantity You required            : ");
					quantity = sc.nextInt();
					while (true) {
						UserInterface.orderOrCart();
						operation = UserModule.getWhatToDo(sc);
						performUserTask(connection, args, sc, operation);
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else {
				System.out.println("No item in the list");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Method for add to cart
	public static void adddToCart(Connection connection) {
		try {
			String wd = FetchAndDisplayFromDB.specificWatchDetail(connection, watchId, 1);
			String wd1[] = wd.split("_");
			price = Double.parseDouble(wd1[2]);
			wd = wd1[0] + "_" + wd1[1] + "_" + (price * quantity) + "_" + quantity;
			wd1 = wd.split("_");
			// System.out.println(Arrays.toString(wd1));
			
			if (InsertInDB.insertInCart(connection, watchId, wd)) {
				System.out.println("Item added to the cart Succesfully");
				// UserInterface.shoppingCart(wd1);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Method for display user order history
	public static void history(Connection connection) {
		try {
			FetchAndDisplayFromDB.viewOrders(connection);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Method for Logout
	public static void logOut(Connection connection, String[] args) throws SQLException {
		try {
			DeleteFromDB.clearProfile(connection);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Logging out. GoodBye!");
		System.out.println("-----------------------------------------------------------");
		MainModule.main(args);
	}

	// Method for View Cart
	public static void viewCart(Connection connection, String[] args, Scanner sc, char action) {
		try {
			FetchAndDisplayFromDB.showMyCart(connection);
			UserInterface.cartWork();

			while(true) {	
				action = sc.next().charAt(0);
				if(action == 'R' || action == 'r') {
					System.out.println("Enter the Watch Id to Remove Form Cart : ");
					watchId = sc.next();
					System.out.println("Item Remved");
				} else if(action == 'D' || action == 'd'){
					System.out.println("Remove All");
				} else if(action == 'B' || action == 'b') {
					UserInterface.userInterface(connection, null, sc, action);
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Method to Edit Profle
	public static void editProfile(Connection connection, Scanner sc) {

		while (true) {
			System.out.print("Enter Your Name     : ");
			name = sc.next().trim() + sc.nextLine();
			if (Validation.validateName(name))
				break;
			else
				System.out.println("Enter a Valid name");
		}
		while (true) {
			System.out.print("Enter mobile Number : ");
			mobileNumber = sc.nextLine();
			if (Validation.validateMobileNumber(mobileNumber))
				break;
			else
				System.out.println("Enter a valid Mobile Numebr");
		}

		while (true) {
			System.out.print("Enter your Email Id : ");
			email = sc.nextLine();
			if (Validation.validateEmail(email))
				break;
			else
				System.out.println("Enter a valid emailId");
		}

		while (true) {
			System.out.print("Enter your Location : ");
			address = sc.nextLine();
			if (!address.isEmpty())
				break;
			else
				System.out.println("Address cannot be Empty");
		}

		Profile profile = new Profile(name, mobileNumber, email, address);
		try {
			UpdateInDB.updateProfile(connection, profile.getName(), profile.getMobileNumber(), profile.getEmailId(),
					profile.getAddress());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void profileView(Connection connection, String[] args, Scanner sc, char operation) {
		try {
			String profile[] = FetchAndDisplayFromDB.displayProfile(connection).split("_");
			UserInterface.profile(profile);
			while (true) {
				UserInterface.profileAction();
				operation = getWhatToDo(sc);
				performUserTask(connection, args, sc, operation);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void passwordChange(Connection connection, Scanner sc) {
		while (true) {
			System.out.print("Enter your currrent password : ");
			String currentPassword = sc.next().trim();
			sc.nextLine();
			try {
				if (CheckFromDB.checkPassword(connection, currentPassword)) {
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
										UpdateInDB.changePassword(connection, newPassword);
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