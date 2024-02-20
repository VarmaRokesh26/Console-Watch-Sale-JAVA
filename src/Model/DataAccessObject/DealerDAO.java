package Model.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.User;

public class DealerDAO {
    
    private static List<String> profile = new ArrayList<>();
	private static String query;

    public static boolean checkDealerDetails(Connection connection, User login) throws SQLException {
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

}
