package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UIDGenerator {
    private static String dealerId = "DUID";
    private static String userId = "UID";
    private static String watchId = "WID";
    private static String adminId = "AUID";
    private static String courierServiceId = "CUID";
    private static String orderId = "OID";
    private static String cartId = "CID";

    private static String query;
    private static String prefix = "";

    public static String IdGenerator(Connection connection, String role) throws SQLException {
        String Id = "";

        if(role.equals("admin")) {
            prefix = adminId;
        } else if(role.equals("user")) {
            prefix = userId;
        } else if(role.equals("dealer")) {
            prefix = dealerId; 
        } else if(role.equals("watches")) {
            prefix = watchId;
        } else if(role.equals("courierservice")) {
            prefix = courierServiceId;
        } else if(role.equals("orders")) {
            prefix = orderId;
        } else if(role.equals("cart")) {
            prefix = cartId;
        }

        String prevId = lastInsertedId(connection, role);
        int counter = Integer.parseInt(prevId.replaceAll("[^0-9]", "").trim());
        counter++;

        Id = prefix + counter;

        return Id;
    }

    public static String lastInsertedId(Connection connection, String role) throws SQLException {
        String colName = "";
        String tableName = "";

        if(role.equals("admin")) {
            tableName = "admin";
            colName = "adminId";
        } else if(role.equals("user")) {
            tableName = "user";
            colName = "userId";
        } else if(role.equals("dealer")) {
            tableName = "dealer";
            colName = "dealerId";
        } else if(role.equals("watches")) {
            tableName = "watches";
            colName = "watchId";
        } else if(role.equals("courierservice")) {
            tableName = "courierservice";
            colName = "courierServiceId";
        } else if(role.equals("orders")) {
            tableName = "orders";
            colName = "orderId";
        } else if(role.equals("cart")) {
            tableName = "cart";
            colName = "cartId";
        }

        query = "SELECT " + colName + " FROM " + tableName + " ORDER BY " + colName + " DESC LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(colName);
            } else {
                return prefix + "1000";
            }
        }
    }
}
