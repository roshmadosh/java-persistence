package com.hiroshisprojects.jdbc.employee;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	private EmployeeDao dao;
	public EmployeeController(EmployeeDao dao) {
		this.dao = dao;
	}

	@GetMapping
	@ResponseBody
	public List<Employee> getEmployees() {
		List<Employee> employees = dao.selectAll();	
		System.out.println("EMPLOYEES************ " + Arrays.toString(employees.toArray()));
		return employees;
	}
	
}
