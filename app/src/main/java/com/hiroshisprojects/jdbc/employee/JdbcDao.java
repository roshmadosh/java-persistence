package com.hiroshisprojects.jdbc.employee;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDao {
	

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
}
