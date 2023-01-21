package com.hiroshisprojects.jpa.users;

import java.util.List;

public interface UserDao {
	void save(User user);
	List<User> list();
}



