package com.hiroshisprojects.jpa;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class TestConfig {

	@Profile("test")
	@Bean(initMethod = "start", destroyMethod = "stop")
	@DependsOn({"getH2DataSource"})
	public Server getH2Server() throws Exception {
		return Server.createWebServer("-web");
	}

	@Profile("test")
	@Bean
	public DataSource getH2DataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.generateUniqueName(true)
			.setType(EmbeddedDatabaseType.H2)
			// .addScript("build.sql")
			.build();
		return db;
	}

}
