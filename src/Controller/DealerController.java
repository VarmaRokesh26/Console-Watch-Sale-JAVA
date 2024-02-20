package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.DealerDAO;
import Model.DealerDAOImpl;

public class DealerController {
    
    public static List<DealerDAO> displayDealer(Connection conenction, DealerDAO dealer) throws SQLException {
        return DealerDAOImpl.displayDealer(conenction, dealer);
    }

}
