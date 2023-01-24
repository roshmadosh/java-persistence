package com.hiroshisprojects.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.hiroshisprojects.jpa.users.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = "classpath:datasource.properties")
@EnableTransactionManagement
public class HibernateConfig {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private Environment env;

	
	@Bean(initMethod = "start", destroyMethod = "stop")
	@DependsOn({"getH2DataSource"})
	public Server getH2Server() throws Exception {
		return Server.createWebServer("-web");
	}

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


	@Bean
	public DataSource getDataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(env.getProperty("spring.datasource.url"));
		ds.setUser(env.getProperty("spring.datasource.username"));
		ds.setPassword(env.getProperty("spring.datasource.password"));
		return ds;
	}

	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setConfigLocation(context.getResource("classpath:hibernate.cfg.xml"));
		factoryBean.setAnnotatedClasses(User.class);
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getHibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

}
