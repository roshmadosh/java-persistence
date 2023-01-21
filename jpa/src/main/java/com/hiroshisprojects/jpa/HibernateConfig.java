package com.hiroshisprojects.jpa;

import com.hiroshisprojects.jpa.users.User;

import org.springframework.context.annotation.Bean;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	public SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(User.class);
 
		return configuration.buildSessionFactory();
	}


}
