package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/studentgradetracker";

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    
    private DBConnection() {
    }

   
    public static Connection getConnection() throws SQLException {

        try {
            // Explicit driver loading (safe and clear)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }

        return DriverManager.getConnection(
                DB_URL,
                DB_USERNAME,
                DB_PASSWORD
        );
    }
}
