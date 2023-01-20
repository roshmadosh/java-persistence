package com.hiroshisprojects.jdbc.employee;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private JdbcDao jdbcDao;

	public EmployeeServiceImpl(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	@Override
	public void updateEmployee(long empId, Map<String, String> empMap) throws Exception {
		// check req body is non-empty
		if (empMap.entrySet().isEmpty()) {
			throw new IllegalArgumentException("Request body must contain at least one of [name, position, salary]");
		}

		// name and position can't be blank 
		for (Map.Entry<String, String> entry: empMap.entrySet()) {
			if (entry.getKey().equals("salary")) {
				if (Double.valueOf(entry.getValue()) <= 0) {
					throw new IllegalArgumentException("Salary must be greater than zero.");	
				}
			} else if (entry.getValue().isBlank()) {
				throw new IllegalArgumentException("Cannot give empty string as value for fields. Please omit field from request body if not updating.");
			}
		} jdbcDao.updateEmployeeWithId(empId, empMap);;
	}
}
