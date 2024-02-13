package Model;

import java.sql.Connection;

public class UIDGenerator {
    private static String dealer = "DUID";
    private static String user = "UID";
    private static String watch = "WID";
    private static String admin = "AUID";
    private static String courier = "CUID";

    private static int counter = 1000;

    public static String IdGenerator(Connection connection, String role) {
        String Id = "";


        return Id;
    }

    public static String lastInsertedId(Connection connection, String role) {
        String query = "";
        return null;
    }
}
