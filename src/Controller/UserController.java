package Controller;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.User;
import Model.UserDAO;

public class UserController {
    
    public static boolean insertInuser(Connection connection, User signUp) throws SQLException {
        return UserDAO.insertUserDetails(connection, signUp);
    }

}
