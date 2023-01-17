package com.hiroshisprojects.jdbc.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDao {

	private JdbcTemplate jdbcTemplate;

	public JdbcDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Employee> getEmployees() {
		String query = "SELECT * FROM employees";
		List<Employee> employees = jdbcTemplate.query(query, new EmployeeMapper());
		return employees;
	}

	private static final class EmployeeMapper implements RowMapper<Employee> {

		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			String name = rs.getString("name");
			String position = rs.getString("position");
			double salary = rs.getDouble("salary");
			Employee employee = new Employee(name, position, salary);
			return employee;
		}
	}
}
