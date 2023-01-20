package com.hiroshisprojects.jdbc.employee;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.MapEntry;
import com.hiroshisprojects.jdbc.exceptions.EmployeeNotFoundException;
import com.hiroshisprojects.jdbc.exceptions.EmployeeUpdateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	private final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	private EmployeeDao dao;
	private JdbcDao jdbcDao;
	public EmployeeController(EmployeeDao dao, JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
		this.dao = dao;
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
			LOGGER.error(e.getMessage(), e);
			throw new EmployeeNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/template")
	@ResponseBody
	public Employee insertEmployeeUsingTemplate(@RequestBody Employee employee) {
		return jdbcDao.insertEmployee(employee);	
	}	

	@PutMapping("/template/{empId}")
	@ResponseBody
	public void updateEmployee(@PathVariable long empId, @RequestBody Employee employee) {
		try {
			jdbcDao.updateEmployeeWithId(empId, employee);
		} catch (NullPointerException npe) {
			LOGGER.error(npe.getMessage());
			throw new EmployeeUpdateException("Please include all fields for Employee update.");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new EmployeeUpdateException(e.getMessage());
		}	
	}

	@ExceptionHandler(EmployeeUpdateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> handleEmployeeUpdateException(EmployeeUpdateException eue) {
		HashMap<String, Object> result = new HashMap<>();
		result.put("error", true);
		result.put("message", eue.getMessage());
		return result;
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, Object> handleEmployeeNotFoundException(EmployeeNotFoundException enfe) {
		HashMap<String, Object> result = new HashMap<>();
		result.put("error", true);
		result.put("message", enfe.getMessage());
		return result;
	}
}
