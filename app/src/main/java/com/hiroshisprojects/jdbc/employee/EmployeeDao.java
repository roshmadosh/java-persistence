package com.hiroshisprojects.jdbc.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.hiroshisprojects.jdbc.data.Dao;
import com.hiroshisprojects.jdbc.data.DbConnector;

public class EmployeeDao extends Dao<Employee> {
	
	private Connection connection;
	private EmployeeQueryStringProvider provider;

	public EmployeeDao() {
		connection = DbConnector.getConnection();
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
	public void selectAll() {
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(provider.selectAll())) {
				while (resultSet.next()) {
					Employee employee = new Employee(resultSet.getString("name"),
							resultSet.getString("position"),
							resultSet.getDouble("salary"));
					System.out.println(employee);
				}
			} catch (Exception e) {
				throw e;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
