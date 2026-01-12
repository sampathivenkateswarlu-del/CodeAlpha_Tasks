package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import config.DatabaseConfig;

public final class DBConnectionUtil {
	private DBConnectionUtil() {
	}

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DatabaseConfig.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new SQLException("JDBC Driver not found", e);
		}
		return DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USERNAME,
				DatabaseConfig.DB_PASSWORD);
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Failed to close DB connection: " + e.getMessage());
			}
		}
	}
}