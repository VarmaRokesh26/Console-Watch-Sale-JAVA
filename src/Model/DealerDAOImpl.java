package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.*;

public class DealerDAOImpl {
    
    private static List<String> profile = new ArrayList<>();
	private static String query;

    public static boolean checkDealerDetails(Connection connection, UserDAO login) throws SQLException {
		boolean isDealer = false;
		query = "SELECT * FROM dealer";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("dealerMailId");
				String passwordFromDB = resultSet.getString("dealerPassword");
				if (emailFromDB.equals(login.getEmailId()) && passwordFromDB.equals(login.getPassword())) {
					isDealer = true;
					break;
				}
			}
		}
		if (isDealer) {
			profile.add(login.getEmailId());
			profile.add(login.getPassword());
			profile.add("dealer");
		}
		return isDealer;
	}

	// Insert Logic for New Dealer
    public static boolean insertNewDealerDetails(Connection connection, DealerDAO dealer) throws SQLException {
        String insertQuery = "INSERT INTO dealer (dealerId, dealerName, location, contactNumber, dealerMailId, dealerPassword) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
           
            preparedStatement.setString(1, dealer.getDealerId());
            preparedStatement.setString(2, dealer.getDealerName());
            preparedStatement.setString(3, dealer.getDealerLocation());
            preparedStatement.setString(4, dealer.getContactNumer());
            preparedStatement.setString(5, dealer.getDealerMailid());
            preparedStatement.setString(6, dealer.getPassword());

            return preparedStatement.executeUpdate() > 0;
        }
    }

	public static DealerDAO getProfile(Connection connection, DealerDAO dealer) throws SQLException {
        String profEmail = profile.get(0);
        String profPass = profile.get(1);

        query = "SELECT * FROM dealer WHERE mailId = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profEmail);
        preparedStatement.setString(2, profPass);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            dealer.setDealerId(resultSet.getString(1));
            dealer.setDealerName(resultSet.getString(2));
            dealer.setDealerLocation(resultSet.getString(3));
            dealer.setContactNumer(resultSet.getString(4));
            dealer.setDealerMailid(profEmail);
            dealer.setPassword(profPass);
        }
        return dealer;
    }

    public static List<DealerDAO> displayDealer(Connection connection, DealerDAO dealer) throws SQLException {
        String query = "SELECT * FROM dealer";
        List<DealerDAO> list = new ArrayList<>();
        
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            dealer.setDealerId(resultSet.getString(1));
            dealer.setDealerName(resultSet.getString(2));
            dealer.setDealerLocation(resultSet.getString(3));
            dealer.setContactNumer(resultSet.getString(4));
            dealer.setDealerMailid(resultSet.getString(5));
            dealer.setPassword(resultSet.getString(6));

            list.add(dealer);
        }
        return list;
    }

}
