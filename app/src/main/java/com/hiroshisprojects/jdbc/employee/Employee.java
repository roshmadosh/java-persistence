package com.hiroshisprojects.jdbc.employee;

import com.hiroshisprojects.jdbc.MyModel;

public class Employee extends MyModel {
	
	private String name;
	private String position;
	private double salary;

	public Employee() {}

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
