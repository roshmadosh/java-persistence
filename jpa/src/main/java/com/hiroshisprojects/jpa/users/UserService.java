package com.hiroshisprojects.jpa.users;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// @Transactional
public class UserService {

	private UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	} 

	@Transactional(readOnly = true)
	public List<User> list() {
		return userDao.list();
	}
	
	@Transactional
	public void save(User user) {
		userDao.save(user);	
	}


}
