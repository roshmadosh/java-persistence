package com.hiroshisprojects.jpa;

import javax.sql.DataSource;

import com.hiroshisprojects.jpa.users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@org.springframework.context.annotation.Configuration
@PropertySource(value = "classpath:datasource.properties")
@EnableTransactionManagement
public class HibernateConfig {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private DataSource ds;

	@Bean
	public SpringLiquibase getLiquiBase() {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(ds);
		liquibase.setChangeLog("classpath:db-changelog.xml");
		return liquibase;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(ds);
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
