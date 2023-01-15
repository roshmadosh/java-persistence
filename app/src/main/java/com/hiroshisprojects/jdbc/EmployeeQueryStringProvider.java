package com.hiroshisprojects.jdbc;

class EmployeeQueryStringProvider extends QueryStringProvider<Employee> {

	public EmployeeQueryStringProvider() {
		super("employees");
	}

	@Override
	public String createTable() {
		System.out.println(String.format("Creating table %s...", this.tableName));
		return "CREATE TABLE IF NOT EXISTS employees"
					+ "(emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30),"
					+ "position varchar(30), salary double)";
	}

	@Override
	public String insertInto(Employee employee) {
		System.out.println(String.format("Inserting %s into %s...", employee.getName(), this.tableName));
		return String.format("INSERT INTO employees(name, position, salary)"
					+ " VALUES('%s', '%s', %s)", employee.getName(), employee.getPosition(), employee.getSalary());
	}

	@Override
	public String selectAll() {
		return "SELECT * FROM " + this.tableName;
	}

}
