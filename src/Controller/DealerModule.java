package Controller;

import java.sql.Connection;
import java.util.Scanner;

import Model.DBOperations;
import View.CourierServiceInterface;
import View.DealerInterface;

public class DealerModule {
    
    public static char getWhatToDo(Scanner sc) {
        return sc.next().charAt(0);
    }
    public static void dealerTask(Connection con, String[] args, Scanner sc, char operation) {
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
				viewAdminProfile(con, args, sc, operation);
				break;
			}

			case 'B':
			case 'b': {
				DealerInterface.dealerInterface(con, args, sc, operation);
			}

            default:
                break;
        }
    }

    public static void showOrders(Connection con) {

        try {
            DBOperations.viewOrders(con, 0); 
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void viewAdminProfile(Connection con, String[] args, Scanner sc, char operation) {
		try {
			String profile[] = DBOperations.displayProfile(con).split("_");
			CourierServiceInterface.courierDetailDisplay(profile);

			while (true) {
				CourierServiceInterface.actionMenu();
				operation = getWhatToDo(sc);
				dealerTask(con, args, sc, operation);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
