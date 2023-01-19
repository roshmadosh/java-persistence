package com.hiroshisprojects.jdbc.exceptions;


public class EmployeeUpdateException extends RuntimeException {

	public EmployeeUpdateException() {
	}

	public EmployeeUpdateException(String message) {
		super(message);
	}

	public EmployeeUpdateException(Throwable cause) {
		super(cause);
	}

	public EmployeeUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
