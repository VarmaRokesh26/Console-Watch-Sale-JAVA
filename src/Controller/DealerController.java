package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.Dealer;
import Model.DealerDAO;

public class DealerController {
    
    public static List<Dealer> displayDealer(Connection conenction, Dealer dealer) throws SQLException {
        return DealerDAO.displayDealer(conenction, dealer);
    }

}
