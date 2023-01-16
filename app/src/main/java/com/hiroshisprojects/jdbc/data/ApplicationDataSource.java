package com.hiroshisprojects.jdbc.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;



public class ApplicationDataSource {

	private static DataSource ds;

	private static DataSource createDataSource() {
		System.out.println("Creating Data Source...");
		Properties props = new Properties();

		try(InputStream input = ApplicationDataSource.class.getClassLoader().getResourceAsStream("datasource.properties")) {
			props.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HikariConfig config = new HikariConfig(props);
		config.setJdbcUrl(props.getProperty("dataSource.jdbcUrl"));
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		HikariDataSource datasource = new HikariDataSource(config);
		ds = datasource;
		return datasource;
	}

	private ApplicationDataSource() {}

	public static Connection getConnection() throws SQLException {
		if (ds != null) {
			System.out.println("Connection already exists...");
			return ds.getConnection();
		}
		return createDataSource().getConnection();
	}

}
