package model.dbConnection;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	// JDBC driver name and database URL
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/fedexProject";

	// Database credentials
	private final String USER = "root";
	private final String PASS = "root";
	private Connection connection = null;

	public Connection ConnectDB() {
		
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}