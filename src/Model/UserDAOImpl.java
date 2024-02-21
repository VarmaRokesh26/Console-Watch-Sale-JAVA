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

}
