package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.DealerDAO;
import DAO.OrderDAO;
import DAO.UserDAO;
import DAO.WatchDAO;
import Model.AdminDAOImpl;
import Model.DealerDAOImpl;
import Model.OrderDAOImpl;
public class DealerController {
    
    public static List<DealerDAO> displayDealer(Connection conenction) throws SQLException {
        return DealerDAOImpl.displayDealer(conenction);
    }

    public static DealerDAO dealerProfile(Connection connection, DealerDAO dealer) throws SQLException {
        if (dealer == null) {
            dealer = new DealerDAO();
        }
        return DealerDAOImpl.getProfile(connection, dealer);
    }

    public static boolean updateProfile(Connection connection, DealerDAO dealer) throws SQLException {
        if(dealer == null)
            dealer = new DealerDAO();
        return DealerDAOImpl.updateProfile(connection, dealer);
    }

    public static boolean checkPassword(DealerDAO dealer) {
        return DealerDAOImpl.checkPassword(dealer);
    }

    public static boolean updatePassword(Connection coneection, DealerDAO dealer)  throws SQLException {
        return DealerDAOImpl.changePassword(coneection, dealer);
    }

    public static void logOut(String[] args) throws SQLException {
		AdminDAOImpl.clearProfile();
	}

    public static List<OrderDAO> historyOfOrders(Connection connection, UserDAO user, DealerDAO dealer , int entry) throws SQLException {
        return OrderDAOImpl.listOfOrders(connection, user, dealer, entry);
    } 

    public static DealerDAO getDealerId(Connection connection) throws SQLException {
        return DealerDAOImpl.getDealerId(connection);
    }

    public static boolean updateOrderStatus(Connection connection, OrderDAO order) throws SQLException {
        return OrderDAOImpl.updateOrderStatus(connection, order);
    }

    public static List<WatchDAO> displayWatch(Connection connection, DealerDAO dealer) throws SQLException {
        return null;
    } 

}
