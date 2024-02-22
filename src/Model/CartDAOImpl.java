package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.*;

public class CartDAOImpl {

    // Users selected item will be added in the cart
    public static boolean insertInCart(Connection connection, CartDAO cart) throws SQLException {
        String cartQuery = "INSERT INTO cart (cartId, userId, watchId, cartDetails) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(cartQuery);
        preparedStatement.setString(1, cart.getCartId());
        preparedStatement.setString(2, cart.getUserId());
        preparedStatement.setString(3, cart.getWatchId());
        preparedStatement.setString(4, cart.getCartDetails());
        return preparedStatement.executeUpdate() > 0;
    }

    // Display Cart
    public static CartDAO showMyCart(Connection connection, UserDAO user) throws SQLException {
        // boolean cartNotEmpty = false;
        CartDAO cart = new CartDAO();
        String cartViewQuery = "SELECT cartDetails FROM cart WHERE userId = ?";

        PreparedStatement cartViewPreparedStatement = connection.prepareStatement(cartViewQuery);
        cartViewPreparedStatement.setString(1, user.getUserId());
        ResultSet cartResultSet = cartViewPreparedStatement.executeQuery();
        while (cartResultSet.next()) {
            System.out.println("+---------------------------------------+");
        }

        return cart;
    }

}
