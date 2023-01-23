package com.hiroshisprojects.jpa.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping()
	public List<User> getAllUsers() {
		return service.list(); 
	}

	@PostMapping()
	public ResponseEntity<Map<String, Object>> saveUser(@RequestBody User user) {
		
		Map<String, Object> resp = new HashMap<>();
		try {
			service.save(user);
			resp.put("user", user);
			resp.put("message", "Successfully added user.");
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			resp.put("user", null);
			resp.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(resp);	
		}
	}

}
