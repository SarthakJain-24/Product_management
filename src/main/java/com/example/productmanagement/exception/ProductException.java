package com.example.productmanagement.exception;

import javax.servlet.http.HttpServletResponse;

public class ProductException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String message;
	private HttpServletResponse response;

	public ProductException(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public ProductException(PMMessage pmMessage) {
		this.statusCode = pmMessage.getStatusCode();
		this.message = pmMessage.getMessage();
	}

	public ProductException(int errorCode, String errorMessage, HttpServletResponse response) {
		this.statusCode = errorCode;
		this.message = errorMessage;
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
	
	
	

