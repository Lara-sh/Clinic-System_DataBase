package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DB_URL ="jdbc:mysql://<hostname>:<port>/<database>?allowPublicKeyRetrieval=true&useSSL=false";
	private static final String USER = "yourUsername";
	private static final String PASS = "yourPassword";
	private static Connection conn;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				System.out.println("Connected to the database successfully!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

