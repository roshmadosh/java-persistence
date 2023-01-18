package com.hiroshisprojects.jdbc.employee;

import java.util.List;

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
				(rs, ind) -> 
					new Employee(
						rs.getString("name"), 
						rs.getString("position"), 
						rs.getDouble("salary")
					)
				);
		return employees;
	}
	public Employee insertEmployee(Employee employee) {
		String query = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
		jdbcTemplate.update(query, employee.getName(), employee.getPosition(), employee.getSalary());
		return employee;
	}
}
