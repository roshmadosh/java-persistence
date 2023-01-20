package com.hiroshisprojects.jdbc.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hiroshisprojects.jdbc.exceptions.EmployeeNotFoundException;
import com.hiroshisprojects.jdbc.exceptions.EmployeeUpdateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	// private EmployeeDao dao;
	private JdbcDao jdbcDao;
	private IEmployeeService employeeService;
	public EmployeeController(JdbcDao jdbcDao, IEmployeeService employeeService) {
		this.jdbcDao = jdbcDao;
		// this.dao = dao;
		this.employeeService = employeeService;
	}

	// @GetMapping
	// @ResponseBody
	// public List<Employee> getEmployees() {
	// 	List<Employee> employees = dao.selectAll();	
	// 	return employees;
	// }


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
	public ResponseEntity<Map<String, String>> updateEmployee(@PathVariable long empId, @RequestBody Map<String, String> empMap) {
		Map<String, String> respObject = new HashMap<>();
		try {
			employeeService.updateEmployee(empId, empMap);
			respObject.put("message", "Employee with ID: " + empId + " successfully updated.");
			return new ResponseEntity<>(respObject, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			respObject.put("message", e.getMessage());
			return new ResponseEntity<>(respObject, HttpStatus.BAD_REQUEST);
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
