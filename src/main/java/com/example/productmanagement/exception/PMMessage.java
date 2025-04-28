package com.example.productmanagement.exception;

public enum PMMessage {

	SUCCESS(0, "Success"), UNKNOWN_EXCEPTION(1, "Unknown Error Occurred, Please Try Again."),

	/********** Validation Codes 10001-10100 *******************/
     TOKEN_EXPIRED(1001, "Jwt token is expired."),
	INVALID_JWT(1002, "Please Provide Valid JWT."),
	;

	
	private final int statusCode;
	private final String message;

	PMMessage(int value, String message) {
		this.statusCode = value;
		this.message = message;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getMessage() {
		return this.message;
	}
}
