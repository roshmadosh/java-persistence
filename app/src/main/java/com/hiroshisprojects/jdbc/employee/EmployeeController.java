package com.hiroshisprojects.jdbc.employee;

import java.util.List;

import com.hiroshisprojects.jdbc.exceptions.EmployeeNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	private final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
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

	@GetMapping("/template/{empId}")
	@ResponseBody
	public Employee getEmployeeBydId(@PathVariable long empId) {
		try {
			return jdbcDao.findEmployeeById(empId);
		} catch (DataAccessException e) {
			LOGGER.error("COULD NOT FIND EMPLOYEE "+ empId);
			throw new EmployeeNotFoundException();
		}
	}

	@PostMapping("/template")
	@ResponseBody
	public Employee insertEmployeeUsingTemplate(@RequestBody Employee employee) {
		return jdbcDao.insertEmployee(employee);	
	}	
}
