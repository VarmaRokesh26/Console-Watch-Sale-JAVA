package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import View.AdminInterface;
import View.UserInterface;

public class DBOperations {
	public static String res;
	public static int rowsAffected;
	public static List<String> profile = new ArrayList<>();

	// Check Admin Details for Login
	public static boolean checkAdminDetails(Connection connection, String email, String password) throws SQLException {
		boolean isAdmin = false;
		String checkQueryBySelect = "SELECT * FROM admindetails";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("mailid");
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
			profile.add("admindetails");
		}
		return isAdmin;
	}

	// Check User details for LogIn
	public static boolean checkUserDetails(Connection connection, String email, String password) throws SQLException {
		boolean isUser = false;
		String checkQueryBySelect = "SELECT * FROM userdetails";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkQueryBySelect);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String emailFromDB = resultSet.getString("emailid");
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
			profile.add("userdetails");
		}
		return isUser;
	}

	// Insert Watches
	public static void insertNewWatch(Connection connection, String name, String brand, double price,
			String description, int number_of_stocks) throws SQLException {
		String insertQuery = "INSERT INTO watches (name, brand, price, description, number_of_stocks) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, brand);
			preparedStatement.setDouble(3, price);
			preparedStatement.setString(4, description);
			preparedStatement.setInt(5, number_of_stocks);

			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected >= 1) {
				System.out.println("Data Inserted!!");
				res = "[" + name + ", " + brand + ", " + price + ", " + description + ", " + number_of_stocks + "]";
			}
			System.out.println("Inserted Data : " + res);
		}
	}

	// Update Watches
	public static void updateWatch(Connection connection, String sName, String brand, double price, String desc,
			int nOS, int wId) throws SQLException {
		String updateQuery = "UPDATE watches SET name=?, brand=?, price=?, description=?, number_of_stocks=? WHERE id=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setString(1, sName);
			preparedStatement.setString(2, brand);
			preparedStatement.setDouble(3, price);
			preparedStatement.setString(4, desc);
			preparedStatement.setInt(5, nOS);
			preparedStatement.setInt(6, wId);
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Update successful.");
			} else {
				System.out.println("Updation Unsuccessfull.");
			}
		}
	}

	// Delete Watch
	public static void deleteWatch(Connection connection, int wId) throws SQLException {
		String deleteQuery = "DELETE FROM watches WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, wId);

			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected >= 1)
				System.out.println("--Data Deleted.--");
			else
				System.out.println("Nothnig is there to delete");
		}
	}

	// Delete all records where number_of_stocks is zero
	public static void deleteWatchByNOS(Connection connection) throws SQLException {
		String deleteQuery = "DELETE FROM watches WHERE number_of_stocks = 0";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected >= 1)
				System.out.println("--Datum Deleted.--");
			else
				System.out.println("Not Deleted");
		}
	}

	// Display Watches
	public static void selectAllWatches(Connection connection, int wId) throws SQLException {
		String selectQuery = "SELECT * FROM watches";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String brand = resultSet.getString("brand");
				double price = resultSet.getDouble("price");
				String description = resultSet.getString("description");
				int number_of_stocks = resultSet.getInt("number_of_stocks");
				if (id == wId) {
					res = name + "," + brand + "," + price + "," + description;
					break;
				}
				if (wId == -1) {
					AdminInterface.displayWatches(id, name, brand, price, description, number_of_stocks);
				}
			}
			System.out.println("******************************************************************");
		}
	}

	// Inserting Logic for new Admin
	public static void insertAdminDetails(Connection connection, String userName, String mobileNumber, String mailid,
			String passCode) throws SQLException {
		String insertQuery = "INSERT INTO admindetails (adminname, mobilenumber, mailid, password) VALUES (?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, mobileNumber);
			preparedStatement.setString(3, mailid);
			preparedStatement.setString(4, passCode);

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected >= 1) {
				System.out.println("Admin Added succesfully!!");
			}
		}
	}

	// Inserting Logic for New User
	public static void insertUserDetails(Connection connection, String userName, String mobileNumber, String email,
			String address, String passCode) throws SQLException {

		String insertQuery = "INSERT INTO userdetails (username, mobilenumber, emailid, address, password) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, mobileNumber);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, passCode);

			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected >= 1)
				System.out.println("\n<--Signed Up succesfully!!-->");
		}
	}

	// Display Watches if it is available
	public static String selectAllWatchesIfAvailable(Connection connection) throws SQLException {
		String selectQuery = "SELECT * FROM watches WHERE number_of_stocks > 0";
		String res = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String brand = resultSet.getString("brand");
				double price = resultSet.getDouble("price");
				String description = resultSet.getString("description");
				int number_of_stocks = resultSet.getInt("number_of_stocks");
				if (number_of_stocks != 0) {
					AdminInterface.displayWatches(id, name, brand, price, description, number_of_stocks);
				}
			}
			System.out.println("******************************************************************");
		}
		return res;
	}

	// Check Watch Id If it exists in the DB for user request
	public static boolean checkWatchId(Connection connection, int id) throws SQLException {
		String checkIdQuery = "SELECT 1 FROM watches WHERE id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(checkIdQuery)) {
			preparedStatement.setInt(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		} catch (Exception e) {
			throw new SQLException("Error checking watch ID: " + e.getMessage(), e);
		}
	}

	// Specific Detail about Watch is to Cearly mention the required watch for the
	// user
	public static String specificWatchDetail(Connection connection, int id, int k) throws SQLException {
		String res = "";
		String specificWatchDetail = "SELECT * FROM watches WHERE id = " + id;

		try (PreparedStatement preparedStatement = connection.prepareStatement(specificWatchDetail);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String brand = resultSet.getString("brand");
				double price = resultSet.getDouble("price");
				String description = resultSet.getString("description");
				int number_of_stocks = resultSet.getInt("number_of_stocks");
				if (number_of_stocks != 0 && k == 0) {
					AdminInterface.displayWatches(id, name, brand, price, description, number_of_stocks);
					System.out.println("******************************************************************");
				} else {
					res += id + "_" + name + "_" + price;
				}
			}
		}
		return res;
	}

	// History of the User of Placing Orders
	public static void showHistory(Connection connection) {
		System.out.println("Your Shop History");
	}

	// Display Cart
	public static void showMyCart(Connection connection) throws SQLException {
		
	}

	public static boolean insertInCart(Connection connection, int watchIdForCart, String cartDetails) throws SQLException {
		String cartQuery = "INSERT INTO cart (userid, cartDetails, id) VALUES (?, ?, ?)";
		String userIdString = userIdForCart(connection);

		try (PreparedStatement preparedStatement = connection.prepareStatement(cartQuery)) {
			preparedStatement.setString(1, userIdString);
			preparedStatement.setString(2, cartDetails);
			preparedStatement.setInt(3, watchIdForCart);

			rowsAffected = preparedStatement.executeUpdate();
			if(rowsAffected>0) 
				return true;
		}
		return false;
	}

	public static String userIdForCart(Connection connection) throws SQLException {
		String mailIdForCart = profile.get(0);
		String userIdForCartString = "";
		String query = "SELECT userid FROM userdetails WHERE emailid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, mailIdForCart);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					userIdForCartString = resultSet.getString("userid");
				}
			}
		}
		return userIdForCartString;
	}

	// Place Order By the User
	public static void placeOrder(Connection connection) {
		System.out.println("Place Order for your sake ");

	}

	// Profile Display for Both Admin and User
	public static String displayProfile(Connection connection) throws SQLException {
		String res = "";
		String profEmail = profile.get(0);
		String profPass = profile.get(1);
		String profRole = profile.get(2);
		String query;

		if (profRole.equals("admindetails")) {
			query = "SELECT * FROM admindetails WHERE mailid = ? AND password = ?";
		} else {
			query = "SELECT * FROM userdetails WHERE emailid = ? AND password = ?";
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, profEmail);
			preparedStatement.setString(2, profPass);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					if (profRole.equals("admindetails")) {
						String profName = resultSet.getString("adminname");
						String profmN = resultSet.getString("mobilenumber");
						res += profName + "_" + profmN + "_" + profEmail;
					} else {
						String profName = resultSet.getString("username");
						String profmN = resultSet.getString("mobilenumber");
						String profAdd = resultSet.getString("address");
						res += profName + "_" + profmN + "_" + profEmail + "_" + profAdd;
					}
				}
			}
		}

		return res;
	}

	// Update Profile for both User and Admin
	public static void updateProfile(Connection connection, String name, String mobileNumber, String emailId,
			String address) throws SQLException {
		if (profile.get(2).equals("admindetails")) {
			String profileUpdateQueryAdmin = "UPDATE admindetails SET adminname = ?, mobileNumber = ?, mailId = ? WHERE mailid = ? AND password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryAdmin)) {
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, mobileNumber);
				preparedStatement.setString(3, emailId);
				preparedStatement.setString(4, profile.get(0));
				preparedStatement.setString(5, profile.get(1));

				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Update successful.");
					profile.set(0, emailId);
				} else {
					System.out.println("Updation Unsuccessfull.");
				}
			}
		} else if (profile.get(2).equals("userdetails")) {
			String profileUpdateQueryUser = "UPDATE userdetails SET username = ?, mobilenumber = ?, emailid = ?, address = ? WHERE emailid = ? AND password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(profileUpdateQueryUser)) {
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, mobileNumber);
				preparedStatement.setString(3, emailId);
				preparedStatement.setString(4, address);
				preparedStatement.setString(5, profile.get(0));
				preparedStatement.setString(6, profile.get(1));

				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Update successful.");
					profile.set(0, emailId);
				} else {
					System.out.println("Updation Unsuccessfull.");
				}
			}
		}
	}

	// Check Current password for Password Change
	public static boolean checkPassword(Connection connection, String currentPassword) throws SQLException {
		return profile.get(1).equals(currentPassword);
	}

	// new Password Update Logic for Both Admin and User
	public static void changePassword(Connection connection, String newPassword) throws SQLException {
		if (profile.get(2).equals("admindetails")) {
			String passwordChangeQuery = "UPDATE admindetails SET password = ? WHERE password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
				preparedStatement.setString(1, newPassword);
				preparedStatement.setString(2, profile.get(1));

				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Password changed Successfully");
					profile.set(1, newPassword);
				} else {
					System.out.println("Password is not channged");
				}
			}
		} else if (profile.get(2).equals("userdetails")) {
			String passwordChangeQuery = "UPDATE userdetails SET password = ? WHERE password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(passwordChangeQuery)) {
				preparedStatement.setString(1, newPassword);
				preparedStatement.setString(2, profile.get(1));

				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Password changed Successfully");
					profile.set(1, newPassword);
				} else {
					System.out.println("Password is not channged");
				}
			}
		}
	}

	// Clear the profile when Loging Out
	public static void clearProfile(Connection connection) {
		profile.clear();
	}
}