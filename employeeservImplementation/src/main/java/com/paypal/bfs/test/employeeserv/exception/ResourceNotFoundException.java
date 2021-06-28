package com.paypal.bfs.test.employeeserv.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1027749965963883400L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
