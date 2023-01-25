package com.hiroshisprojects.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.hiroshisprojects.jpa.users.User;
import com.hiroshisprojects.jpa.users.UserDao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DataSourceConfig.class, HibernateConfig.class, WebMvcConfig.class })
@WebAppConfiguration
public class UserIntegrationTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserIntegrationTest.class);	

	@Autowired
	private UserDao userDao;

	@Test
	@Transactional
	// @Rollback(false)
	@Commit
	public void whenUserSaved_thenCountIncrements() {
		String testEmail = "ANOTHER@gmail.com";
		User testUser = new User();
		testUser.setEmail(testEmail);

		List<User> before = userDao.list();
		userDao.save(testUser);
		List<User> after = userDao.list();
	
		assertEquals(before.size() + 1, after.size());	
	}

}
