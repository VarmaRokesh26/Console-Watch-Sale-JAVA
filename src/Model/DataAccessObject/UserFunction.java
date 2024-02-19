package Model.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.User;

public class UserFunction {
    
    private static List<String> profile = new ArrayList<>();

    // Check User details for LogIn
	public static boolean checkUserDetails(Connection connection, User login) throws SQLException {
		boolean isUser = false;
		String checkQueryBySelect = "SELECT * FROM user";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("emailId");
				String passwordFromDB = resultSet.getString("password");
				if (emailFromDB.equals(login.getEmailId()) && passwordFromDB.equals(login.getPassword())) {
					isUser = true;
					break;
				}
			}
		}
		if (isUser) {
			profile.add(login.getEmailId());
			profile.add(login.getPassword());
			profile.add("user");
		}
		return isUser;
	}

}
