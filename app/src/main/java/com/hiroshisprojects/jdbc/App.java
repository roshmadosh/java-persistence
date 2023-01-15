package com.hiroshisprojects.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public class App {

    public static void main(String[] args) {
		Properties prop = new Properties();
		try (InputStream input = App.class.getResourceAsStream("/database.properties")) {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String connectionString = prop.getProperty("connection.string");
		String dbUsername = prop.getProperty("db.username");
		String dbPassword = prop.getProperty("db.password");
		EmployeeQueryStringProvider provider = new EmployeeQueryStringProvider();		
		try (Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword)) {
			System.out.println("Connected to database...");
			try (Statement stmt = con.createStatement()) {
				try (ResultSet resultSet = stmt.executeQuery(provider.selectAll())) {
					while (resultSet.next()) {
						Employee employee = new Employee(resultSet.getString("name"), resultSet.getString("position"), resultSet.getDouble("salary"));
						System.out.println(employee);
					} 
				} catch (Exception e) {
					throw e;
				}
			} catch (Exception e) {
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
