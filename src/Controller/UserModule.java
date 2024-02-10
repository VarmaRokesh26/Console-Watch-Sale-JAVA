package Controller;

import java.sql.Connection;
import java.util.*;
import Model.*;
import View.MainModule;
import View.UserInterface;

public class UserModule {

	private static int id;
	private static int quantity;
	
	private static String name;
	private static String mobileNumber;
	private static String email;
	private static String address;
	private static String password;
	private static String confirmPassword;
	private static Map<Integer, Integer> itemCart;
	
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
				while(true) {					
					System.out.print("Enter password     : ");
					password = sc.nextLine();
					if (Validation.validatePassword(password))
						return new Login(email, password);
					else
						System.out.println("\nEnter a Valid Password");
				}
			} 
			else
				System.out.println("\nEnter a proper Email Address");
		}
	}

	// Sign Up logic
	public static SignUp getSignUpDetails(Scanner sc) {
        
        while (true) {
        	//userName
        	System.out.println("-----------------------------------------------------------");
            System.out.print("Enter Your Name : ");
            
            name = sc.nextLine();
            if (Validation.validateName(name))
                break;
            else
                System.out.println("<---Name should contains only Characters--->");
        }


        while (true) {
        	//Mobile Number
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

        while(true) {
        	//Home Address to Delivery
        	System.out.print("Enter Your Location(Address) : ");
        	
        	address = sc.nextLine();
        	if(!address.toString().isEmpty())
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
            } 
            else
                System.out.println("<---Password should contains atleast \n8 characters, \n1 UpperCase, "
                		+ "\n1 LowerCase, \n1 Numbers, \n1 SpecialCharacters--->");
        }
        return new SignUp(name, mobileNumber, email, address, confirmPassword);
    }
	
	public static Profile fetchDetails(Scanner sc) {
		while(true) {
			System.out.print("Enter Your Name : ");
			name = sc.next().trim() + sc.nextLine();
			if(Validation.validateName(name))
				break;
			else 
				System.out.println("Enter a Valid name");
		}
		while(true) {
			System.out.print("Enter mobile Number : ");
			mobileNumber = sc.nextLine(); 
			if(Validation.validateMobileNumber(mobileNumber)) 
				break;
			else
				System.out.println("Enter a valid Mobile Numebr");
		}
		
		while(true) {
			System.out.print("Enter your Email Id : ");
			email = sc.nextLine();
			if(Validation.validateEmail(email))
				break;
			else
				System.out.println("Enter a valid emailId");
		}
		
		while(true) {
			System.out.print("Enter your Location :");
			address = sc.nextLine();
			if(!address.isEmpty()) 
				break;
			else
				System.out.println("Address cannot be Empty");
		}
		return new Profile(name, mobileNumber, email, address);
	}
	
	public static void performUserTask(Connection connection, String[] args, Scanner sc, char operation) {
		switch(operation) {
			case 'D':
			case 'd':
			{
				try {					
					DBOperations.selectAllWatchesIfAvailable(connection);
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				break;
			}
			
			case 'O':
			case 'o':
			{
				System.out.print("Enter the item Id to Display Seperatly : ");
				id = sc.nextInt();
				try {
					if(DBOperations.checkWatchId(connection, id)) {
						try {
							DBOperations.specificWatchDetail(connection, id, 0);
							while(true) {					
								UserInterface.orderOrCart();
								operation = UserModule.getWhatToDo(sc);
								performUserTask(connection, args, sc, operation);
							}
							
						} catch(Exception e) {
							System.out.println(e.toString());
						}
						break;
					}
					else {
						System.out.println("No item in the list");
					}
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				break;
			}
			
			case 'A':
			case 'a':
			{
				System.out.print("Enter Quantity You required : ");
				quantity = sc.nextInt();
				try {
					String wd = DBOperations.specificWatchDetail(connection, id, 1);
					
					String wd1[] = wd.split("_");
					wd = wd1[0]+"_"+wd1[1]+"_"+(Double.parseDouble(wd1[2])*quantity)+"_"+quantity;
					wd1 = wd.split("_");
					System.out.println(Arrays.toString(wd1));
					if(DBOperations.insertInCart(connection, id, wd)) {
						UserInterface.shoppingCart(wd1);
					}
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				System.out.println("Item added to the cart Succesfully");
				break;
			}
			
			case 'H':
			case 'h':
			{
				try {
					DBOperations.showHistory(connection);
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				break;
			}
			case 'S':
			case 's':
			{
				// try {
				// 	DBOperations.showMyCart(connection, itemCart);
				// } catch(Exception e) {
				// 	System.out.println(e.toString());
				// }
				break;
			}
			
			case 'V':
			case 'v':
			{
				try {
					String profile[] = DBOperations.displayProfile(connection).split("_");
					UserInterface.profile(profile);
					while(true) {
						UserInterface.profileAction();
						operation = UserModule.getWhatToDo(sc);
						performUserTask(connection, args, sc, operation);
					}
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				break;
			}
			
			case 'E':
			case 'e':
			{
				Profile profile = UserModule.fetchDetails(sc);
				try {
					DBOperations.updateProfile(connection, profile.getName(), profile.getMobileNumber(), profile.getEmailId(), profile.getAddress());
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				break;
			}
			
			case 'P':
			case 'p':
			{
				while(true) {
					System.out.print("Enter your currrent password : ");
					String currentPassword = sc.next().trim();
					sc.nextLine();
					try {
						if(DBOperations.checkPassword(connection, currentPassword)) {
							while(true) {
								System.out.print("Enter new Password : ");
								String newPassword = sc.next();
								sc.nextLine();
								if(!newPassword.equals(currentPassword) && Validation.validatePassword(newPassword)) {								
									while(true) {
										System.out.print("Re-enter new Password : ");
										String reenterPassword = sc.nextLine();
										if(newPassword.equals(reenterPassword)) {
											try {
												DBOperations.changePassword(connection,  newPassword);
											} catch(Exception e) {
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
					} catch(Exception e) {
						System.out.println(e.toString());
					}
				}
				break;
			}
			
			case 'B':
			case 'b':
			{
				UserInterface.userInterface(connection, args, sc, 1);
			}
			
			case 'L':
			case 'l':
			{
				try {
					DBOperations.clearProfile(connection);
				} catch(Exception e) {
					System.out.println(e.toString());
				}
				System.out.println("Logging out. GoodBye!");
				System.out.println("-----------------------------------------------------------");
				MainModule.main(args);
				break;
			}
		}
	}
}