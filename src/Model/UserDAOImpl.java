package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.UserDAO;

public class UserDAOImpl {

	private static List<String> profile = new ArrayList<>();
	// private static int rowsAffected;

	// Check User details for LogIn
	public static boolean checkUserDetails(Connection connection, UserDAO login) throws SQLException {
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

	// Inserting Logic for New User
	public static boolean insertUserDetails(Connection connection, UserDAO signUp) throws SQLException {

		String insertQuery = "INSERT INTO user (userId, userName, mobileNumber, emailId, address, password) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

			preparedStatement.setString(1, signUp.getUserId());
			preparedStatement.setString(2, signUp.getUserName());
			preparedStatement.setString(3, signUp.getMobileNumber());
			preparedStatement.setString(4, signUp.getEmailId());
			preparedStatement.setString(5, signUp.getAddress());
			preparedStatement.setString(6, signUp.getPassword());

			return preparedStatement.executeUpdate() > 0;
		}
	}

	public static UserDAO userIdForCartOrOrder(Connection connection) throws SQLException {
		String mailIdForCart = profile.get(0);
		String query = "SELECT userId FROM user WHERE emailId = ?";
		UserDAO user = new UserDAO();

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, mailIdForCart);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					user.setUserId(resultSet.getString(1));
				}
			}
		}
		return user;
	}

	public static UserDAO getProfile(Connection connection, UserDAO user) throws SQLException {
		String profEmail = profile.get(0);
		String profPass = profile.get(1);

		String query = "SELECT * FROM user WHERE emailId = ? AND password = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, profEmail);
		preparedStatement.setString(2, profPass);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			user.setUserId(resultSet.getString("userId"));
			System.out.println(user.getUserId());
			user.setUserName(resultSet.getString("userName"));
			user.setMobileNumber(resultSet.getString("mobileNumber"));
			user.setEmailId(profEmail);
			user.setAddress(resultSet.getString("address"));
			user.setPassword(profPass);
		}
		return user;
	}

	public static boolean updateProfile(Connection connection, UserDAO user) throws SQLException {
		String profileUpdateQueryUser = "UPDATE user SET userName = ?, mobileNumber = ?, emailId = ?, address = ? WHERE emailId = ? AND password = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryUser);
		preparedStatement.setString(1, user.getUserName());
		preparedStatement.setString(2, user.getMobileNumber());
		preparedStatement.setString(3, user.getEmailId());
		preparedStatement.setString(4, user.getAddress());
		preparedStatement.setString(5, profile.get(0));
		preparedStatement.setString(6, profile.get(1));

		return preparedStatement.executeUpdate() > 0;
	}

	public static boolean checkPassword(UserDAO user) {
        return user.getPassword().equals(profile.get(1));
    }

	public static void clearProfile() {
        profile.clear();
    }

	public static boolean changePassword(Connection connection, UserDAO user) throws SQLException {
        String passwordChangeQuery = "UPDATE user SET password = ? WHERE password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, profile.get(1));

            profile.set(1, user.getPassword());
            return preparedStatement.executeUpdate() > 0;
        }
    }	

}
