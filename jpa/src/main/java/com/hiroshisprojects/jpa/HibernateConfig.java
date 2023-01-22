package com.hiroshisprojects.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.hiroshisprojects.jpa.users.User;
import com.mysql.cj.jdbc.MysqlDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class HibernateConfig {
	@Autowired
	private ApplicationContext context;

	@Bean
	public DataSource getDataSource() {
		Properties props = new Properties();
		try (InputStream inputStream = HibernateConfig.class.getClassLoader().getResourceAsStream("datasource.properties")) {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(props.getProperty("url"));
		ds.setUser(props.getProperty("user"));
		ds.setPassword(props.getProperty("password"));
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
