package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    public static List<CartDAO> showMyCart(Connection connection, UserDAO user) throws SQLException {
        List<CartDAO> list = new ArrayList<>();

        String cartViewQuery = "SELECT * FROM cart WHERE userId = ?";

        PreparedStatement cartViewPreparedStatement = connection.prepareStatement(cartViewQuery);
        cartViewPreparedStatement.setString(1, user.getUserId());
        ResultSet cartResultSet = cartViewPreparedStatement.executeQuery();
        while (cartResultSet.next()) {
            CartDAO cartList = new CartDAO();
            cartList.setCartId(cartResultSet.getString("cartId"));
            cartList.setUserId(cartResultSet.getString("userId"));
            cartList.setWatchId(cartResultSet.getString("watchId"));
            cartList.setCartDetails(cartResultSet.getString("cartDetails"));
            list.add(cartList);
        }

        return list;
    }

    public static boolean removeItemFromCart(Connection connection, CartDAO cart) throws SQLException {
        String query = "DELETE FROM cart WHERE watchId = ? AND userId = ?";
        System.out.println(cart.getUserId());
        System.out.println(cart.getWatchId());
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, cart.getWatchId());
        preparedStatement.setString(2, cart.getUserId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean removeAllItemFromCart(Connection connection, CartDAO cart) throws SQLException {
        String query = "DELETE FROM cart WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, cart.getUserId());
        return preparedStatement.executeUpdate() > 0;
    } 

}
