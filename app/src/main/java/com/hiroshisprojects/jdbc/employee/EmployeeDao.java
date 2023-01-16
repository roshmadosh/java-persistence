package com.hiroshisprojects.jdbc.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hiroshisprojects.jdbc.data.ApplicationDataSource;
import com.hiroshisprojects.jdbc.data.Dao;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao extends Dao<Employee> {
	
	private Connection connection;
	private EmployeeQueryStringProvider provider;

	public EmployeeDao() throws SQLException {
		connection = ApplicationDataSource.getConnection();
		provider = new EmployeeQueryStringProvider();
	}

	@Override
	protected void createTable() {
		System.out.println("DEBUG attempting to create employee table");
		String queryString = provider.createTable();
		try (Statement statement = connection.createStatement()) {
			statement.execute(queryString);
			System.out.println("SUCCESS: employee table creation attempted.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertInto(Employee employee) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(provider.insertInto(employee));
			System.out.println(String.format("SUCCESS: employee %s created.", employee.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employee> selectAll() {
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(provider.selectAll())) {
				List<Employee> employees = new ArrayList<>();
				while (resultSet.next()) {
					Employee employee = new Employee(resultSet.getString("name"),
							resultSet.getString("position"),
							resultSet.getDouble("salary"));
					employees.add(employee);
				}
				return employees;
			} catch (Exception e) {
				throw e;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.<Employee>emptyList();
		}
	}

	
}
