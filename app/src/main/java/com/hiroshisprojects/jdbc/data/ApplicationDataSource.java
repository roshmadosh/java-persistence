package com.hiroshisprojects.jdbc.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.stereotype.Component;


@Component
public class ApplicationDataSource {

	public Connection getConnection() throws SQLException {
		return createDataSource().getConnection();
	}


	private DataSource createDataSource() {
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
		return datasource;
	}

}
