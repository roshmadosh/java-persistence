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
}
