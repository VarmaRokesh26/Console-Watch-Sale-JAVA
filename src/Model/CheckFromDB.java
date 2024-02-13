package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckFromDB {

	private static List<String> profile = new ArrayList<>();
	private static String userIdString;

	// Check Admin Details for Login
	public static boolean checkAdminDetails(Connection connection, String email, String password) throws SQLException {
		boolean isAdmin = false;
		String checkQueryBySelect = "SELECT * FROM admin";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("mailId");
				String passwordFromDB = resultSet.getString("password");
				if (emailFromDB.equals(email) && passwordFromDB.equals(password)) {
					isAdmin = true;
					break;
				}
			}
		}
		if (isAdmin) {
			profile.add(email);
			profile.add(password);
			profile.add("admin");
		}
		return isAdmin;
	}

	// Check User details for LogIn
	public static boolean checkUserDetails(Connection connection, String email, String password) throws SQLException {
		boolean isUser = false;
		String checkQueryBySelect = "SELECT * FROM user";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("emailId");
				String passwordFromDB = resultSet.getString("password");
				if (emailFromDB.equals(email) && passwordFromDB.equals(password)) {
					isUser = true;
					break;
				}
			}
		}
		if (isUser) {
			profile.add(email);
			profile.add(password);
			profile.add("user");
		}
		return isUser;
	}

	public static boolean checkDealerDetails(Connection connection, String email, String password) throws SQLException {
		boolean isUser = false;
		String checkQueryBySelect = "SELECT * FROM dealer";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("dealerMailId");
				String passwordFromDB = resultSet.getString("dealerPassword");
				if (emailFromDB.equals(email) && passwordFromDB.equals(password)) {
					isUser = true;
					break;
				}
			}
		}
		if (isUser) {
			profile.add(email);
			profile.add(password);
			profile.add("dealer");
		}
		return isUser;
	}

	public static boolean checkCourierServiceDetails(Connection connection, String email, String password)
			throws SQLException {
		boolean isUser = false;
		String checkQueryBySelect = "SELECT * FROM courierservice";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("courierServiceMailId");
				String passwordFromDB = resultSet.getString("cSPassword");
				if (emailFromDB.equals(email) && passwordFromDB.equals(password)) {
					isUser = true;
					break;
				}
			}
		}
		if (isUser) {
			profile.add(email);
			profile.add(password);
			profile.add("courierservice");
		}
		return isUser;
	}

	// Check Watch Id If it exists in the DB for user request
	public static boolean checkWatchId(Connection connection, String id) throws SQLException {
		String checkIdQuery = "SELECT watchId FROM watches WHERE watchId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkIdQuery)) {
			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		} catch (Exception e) {
			throw new SQLException("Error checking watch ID: " + e.getMessage(), e);
		}
	}

	public static String userIdForCartOrOrder(Connection connection) throws SQLException {
		String mailIdForCart = profile.get(0);
		String query = "SELECT userId FROM user WHERE emailId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, mailIdForCart);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					userIdString = resultSet.getString("userId");
				}
			}
		}
		return userIdString;
	}

	public static List<String> profileList() {
		return profile;
	}

	// Check Current password for Password Change
	public static boolean checkPassword(Connection connection, String currentPassword) throws SQLException {
		return profile.get(1).equals(currentPassword);
	}

}
