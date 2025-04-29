package com.example.productmanagement.exception;

public enum PMMessage {

	SUCCESS(0, "Success"), DATA_NOT_FOUND(1, "data not found with this id."),

	/********** Validation Codes 1001-1003 *******************/
	TOKEN_EXPIRED(1001, "Jwt token is expired."), INVALID_JWT(1002, "Please Provide Valid JWT."),
	DUPLICATE_ID(1003, "Id ALready Exists"),
	ID_DIFFERENT(1004,"Id not Match with Request body Id.");

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
