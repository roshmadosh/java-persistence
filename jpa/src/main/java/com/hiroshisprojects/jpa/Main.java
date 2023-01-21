package com.hiroshisprojects.jpa;

import java.util.List;

import com.hiroshisprojects.jpa.users.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Main {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		config.addAnnotatedClass(User.class);

		SessionFactory sessionFactory = 
			config.buildSessionFactory();

		Session session = sessionFactory.openSession();

		String hql = "FROM User";
		
		session.beginTransaction();

		Query<User> query = session.createQuery(hql, User.class);
		List<User> results = query.getResultList();

		for (User user: results) {
			System.out.println(user);
		}
		

		session.getTransaction().commit();
	}

}
