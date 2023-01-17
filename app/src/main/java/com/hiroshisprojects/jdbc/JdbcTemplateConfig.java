package com.hiroshisprojects.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class JdbcTemplateConfig {
	
	@Bean
	public DataSource dataSource() {
		Properties props = new Properties();

		try(InputStream input = JdbcTemplateConfig.class.getClassLoader().getResourceAsStream("springdatasource.properties")) {
			props.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(props.getProperty("spring.datasource.url"));
		ds.setUser(props.getProperty("spring.datasource.username"));
		ds.setPassword(props.getProperty("spring.datasource.password"));

		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

}
