package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectivity {
    private static Connectivity instance = null;
    private Connection connection = null;

    private Connectivity() {
        String URL = "jdbc:mysql://localhost:3306/onlinewatchStore";
        String USER = "root";
        String PASSWORD = "Rokeshv@rm@2603";
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
    }

    public static Connectivity getInstance() {
        if (instance == null) {
            synchronized (Connectivity.class) {
                if (instance == null) {
                    instance = new Connectivity();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
