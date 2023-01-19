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

	public void updateEmployeeWithId(long empId, Employee employee) throws Exception {
		// Expecting an exception to be thrown from method if emp not found. 
		Employee checkExists = findEmployeeById(empId);
		
		Map<String, String> fieldsMap = Arrays.stream(Employee.class.getDeclaredFields())
			.map(field -> {
				field.setAccessible(true);
				String key = field.getName();
				String value;
				try {
					value = field.get(employee).toString();
				} catch (IllegalAccessException e) {
					LOGGER.error(e.getMessage(), e);
					value = null;
				}
				return new String[] { key, value };
			})
			.collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));

		List<String> fieldsList = new ArrayList<>();
		for (Map.Entry<String, String> entry: fieldsMap.entrySet()) {
			if (entry.getKey().equals("salary")) {
				if (Double.parseDouble(entry.getValue()) > 0.0) {
					fieldsList.add(String.format("%s = %s", entry.getKey(), entry.getValue()));
				}
			} else if (!entry.getValue().isBlank()) {
				fieldsList.add(String.format("%s = '%s'", entry.getKey(), entry.getValue()));
			} 
		}

		if (fieldsList.isEmpty()) throw new IllegalArgumentException("At least one field must be updated.");

		String setterString = fieldsList.stream().collect(Collectors.joining(", "));

		String query = "UPDATE employees SET " + setterString + " WHERE emp_id = " + empId;

		LOGGER.info("QUERY: " + query);
		jdbcTemplate.update(query);
		
	}
}
