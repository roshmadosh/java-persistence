package com.hiroshisprojects.jdbc.employee;

import java.util.Map;

public interface IEmployeeService {
	
	public void updateEmployee(long empId, Map<String, String> empMap) throws Exception;

}
