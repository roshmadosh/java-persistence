package com.hiroshisprojects.jdbc.employee;

import com.hiroshisprojects.jdbc.MyModel;

class Employee extends MyModel {
	private final static String TABLENAME = "employees";
	
	private String name;
	private String position;
	private double salary;

	public Employee(String name, String position, double salary) {
		this.name = name;
		this.position = position;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", position=" + position + ", salary=" + salary + "]";
	}
	

}
