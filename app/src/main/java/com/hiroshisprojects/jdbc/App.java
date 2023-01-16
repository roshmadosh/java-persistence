package com.hiroshisprojects.jdbc;

import com.hiroshisprojects.jdbc.employee.EmployeeDao;
import com.hiroshisprojects.jdbc.data.DbConnector;

public class App {

    public static void main(String[] args) {
		EmployeeDao employeeDao = new EmployeeDao();
		employeeDao.selectAll();
		DbConnector.closeConnection();
    }
}
