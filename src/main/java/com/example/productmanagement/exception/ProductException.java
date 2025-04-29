package com.example.productmanagement.exception;


public class ProductException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String message;

	public ProductException(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public ProductException(PMMessage pmMessage) {
		this.statusCode = pmMessage.getStatusCode();
		this.message = pmMessage.getMessage();
	}


	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
	
	
	

