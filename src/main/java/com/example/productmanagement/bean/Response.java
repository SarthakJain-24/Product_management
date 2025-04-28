package com.example.productmanagement.bean;


import java.io.Serializable;

import com.example.productmanagement.exception.PMMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	private int errorCode;
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Object data;
	
	
	@JsonInclude(Include.NON_NULL)
	private String responseMessage;
	@JsonInclude(Include.NON_NULL)
	private int responseCode;	

	public Response(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public Response(PMMessage ramMessage) {
		this.errorCode = ramMessage.getStatusCode();
		this.errorMessage = ramMessage.getMessage();
	}

	public Response(PMMessage ramMessage, Object data) {
		this.errorCode = ramMessage.getStatusCode();
		this.errorMessage = ramMessage.getMessage();
		this.data = data;
	}
	
	public Response(int statusCode,String message,Object data) {
		this.responseCode = statusCode;
		this.responseMessage = message;
		this.data = data;
	}

	public Response(int errorCode, String errorMessage,int statusCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.responseCode = statusCode;
	}
	
	@Override
	public String toString() {
		return "{errorMessage:" + errorMessage + ", errorCode:" + errorCode + "}";
	}
}