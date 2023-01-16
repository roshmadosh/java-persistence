package com.hiroshisprojects.jdbc.data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
	private static Connection connection;

	public static Connection getConnection() {
		if (connection == null) {
			System.out.println("Initializing DB connection creation...");	
			connection = createConnection();
			return connection;
		} 
		System.out.println("Connection already exists.");
		return connection;
	}

	private static Connection createConnection() {

		System.out.println("Creating DB connection...");
		Properties prop = new Properties();
		
		try (InputStream input = DbConnector.class.getClassLoader().getResourceAsStream("database.properties")) {
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String connectionString = prop.getProperty("connection.string");
		String dbUsername = prop.getProperty("db.username");
		String dbPassword = prop.getProperty("db.password");

		try {
			Connection connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
				
			System.out.println("SUCCESS: Database connection established to DB.");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAIL: Database connection failed, see stack trace.");
			return null;
		}

	}

	public static void closeConnection() {
		if (connection == null) {
			System.out.println("FAIL: Database connection not available. Nothing to close.");
		} else {
			try {
				connection.close();
				System.out.println("SUCCESS: Database connection closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
