package com.hiroshisprojects.jpa.users;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class UserDaoImpl implements UserDao {
	
	private Session session;

	public UserDaoImpl(SessionFactory sessionFactory) {
		this.session = sessionFactory.openSession();
	}

	@Override
	public void save(User user) {
		session.persist(user);	
	}

	@Override
	public List<User> list() {
		TypedQuery<User> query = session.createQuery("from User", User.class);
		return query.getResultList();
	}

}
