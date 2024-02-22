package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import DAO.*;

public class OrderDAOImpl {
    
    private static String query;

    public static List<OrderDAO> listOfOrders(Connection connection, UserDAO user, DealerDAO dealer, int entry) throws SQLException {
        List<OrderDAO> list= new ArrayList<>();
        if(entry == 0) {
            query = "SELECT * FROM orders WHERE userId = ?";
        } else if(entry == 1) {
            query = "SELECT * FROM orders WHERE dealerId = ? AND status != Delivered";
        } else if(entry == 2) {
            query = "SELECT * FROM orders WHERE dealerId = ? AND status = Delivered";
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        if(entry == 0) 
            preparedStatement.setString(1, user.getUserId());
        else if(entry == 1 || entry == 2) 
            preparedStatement.setString(1, dealer.getDealerId());
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            OrderDAO order = new OrderDAO();
            order.setOrderId(resultSet.getString(1));
            order.setUserId(resultSet.getString(2));
            order.setDealerId(resultSet.getString(3));
            order.setWatchId(resultSet.getString(4));
            order.setOrderDate(resultSet.getString(5));
            order.setDeliveryDate(resultSet.getString(6));
            order.setQuantity(resultSet.getInt(7));
            order.setTotalAmount(resultSet.getDouble(8));
            order.setPaymentMode(resultSet.getString(9));
            order.setStatus(resultSet.getString(10));
            list.add(order);
        }
        return list;
    }
    

}
