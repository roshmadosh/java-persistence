package com.hiroshisprojects.jdbc.employee;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	private JdbcDao jdbcDao;
	private EmployeeDao dao;
	public EmployeeController(EmployeeDao dao, JdbcDao jdbcDao) {
		this.dao = dao;
		this.jdbcDao = jdbcDao;
	}

	@GetMapping
	@ResponseBody
	public List<Employee> getEmployees() {
		List<Employee> employees = dao.selectAll();	
		return employees;
	}


	@GetMapping("/template")
	@ResponseBody
	public List<Employee> getEmployeesUsingTemplate() {
		List<Employee> employees = jdbcDao.getEmployees();
		return employees;
	}

	@PostMapping("/template")
	@ResponseBody
	public Employee insertEmployeeUsingTemplate(@RequestBody Employee employee) {
		return jdbcDao.insertEmployee(employee);	
	}	
}
