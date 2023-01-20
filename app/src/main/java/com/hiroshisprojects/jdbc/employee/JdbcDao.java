package com.hiroshisprojects.jdbc.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDao {
	
	private final Logger LOGGER = LoggerFactory.getLogger(JdbcDao.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Employee> getEmployees() {
		String query = "SELECT * FROM employees";
		List<Employee> employees = jdbcTemplate.query(
				query,
				(resultSet, ind) -> 
					new Employee(
						resultSet.getString("name"), 
						resultSet.getString("position"), 
						resultSet.getDouble("salary")
					)
				);
		return employees;
	}
	public Employee insertEmployee(Employee employee) {
		String query = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
		jdbcTemplate.update(query, employee.getName(), employee.getPosition(), employee.getSalary());
		return employee;
	}

	public Employee findEmployeeById(long empId) throws DataAccessException {
		String query = "SELECT * FROM employees WHERE emp_id = ?";
		return jdbcTemplate.queryForObject(query, 
			(resultSet, rowNum) -> new Employee(resultSet.getString("name"), resultSet.getString("position"), resultSet.getDouble("salary")),
			empId);
	}

	public void updateEmployeeWithId(long empId, Map<String, String> empMap) throws Exception {
		// Expecting an exception to be thrown from method if emp not found. 
		findEmployeeById(empId);
		
		// Construct query string from empMap
		List<String> fieldsList = new ArrayList<>();
		for (Map.Entry<String, String> entry: empMap.entrySet()) {
			if (entry.getValue().equals("salary")) {
					fieldsList.add(String.format("%s = %s", entry.getKey(), entry.getValue()));
			} else {
				fieldsList.add(String.format("%s = '%s'", entry.getKey(), entry.getValue()));
			} 
		}

		String setterString = fieldsList.stream().collect(Collectors.joining(", "));

		String query = "UPDATE employees SET " + setterString + " WHERE emp_id = " + empId;

		LOGGER.info("Submitting query to table 'employees': " + query);
		jdbcTemplate.update(query);
	}
}
