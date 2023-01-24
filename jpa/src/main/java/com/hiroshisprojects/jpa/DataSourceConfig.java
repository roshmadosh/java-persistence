package com.hiroshisprojects.jpa;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DataSourceConfig {
	@Autowired
	private Environment env;

	// @Profile("test")
	@Primary
	@Bean
	public DataSource getH2DataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.generateUniqueName(true)
			.setType(EmbeddedDatabaseType.H2)
			.build();
		
		return db;
	}

	@Bean
	public DataSource getDataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(env.getProperty("spring.datasource.url"));
		ds.setUser(env.getProperty("spring.datasource.username"));
		ds.setPassword(env.getProperty("spring.datasource.password"));
		return ds;
	}
}
