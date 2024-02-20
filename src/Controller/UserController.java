package Controller;

import java.sql.Connection;
import java.sql.SQLException;

import Model.User;
import Model.DataAccessObject.UserDAO;

public class UserController {
    
    public static boolean insertInuser(Connection connection, User signUp) throws SQLException {
        return UserDAO.insertUserDetails(connection, signUp);
    }

}
