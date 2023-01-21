package com.hiroshisprojects.jpa.users;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private UserDao dao;

	public UserController(UserDao dao) {
		this.dao = dao;
	}

	@GetMapping
	public List<User> getAllUsers() {
		return dao.list(); 
	}


}
