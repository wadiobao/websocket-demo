package com.project.library_management.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode  {
	INVALID_METHOD(1111,"Invalid",HttpStatus.BAD_REQUEST),
	USER_EXISTED(401,"User already exists",HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(422,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
	INVALID_USERNAME(422,"Username must be at least 5 characters",HttpStatus.BAD_REQUEST),
	USER_NOT_EXISTED(404,"User does not exist",HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(403,"Unauthenticated",HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(401,"Access denied",HttpStatus.FORBIDDEN),
	;
	private int code;
	private String message;
	private HttpStatusCode httpStatusCode;
	
	private ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
		this.code = code;
		this.message = message;
		this.httpStatusCode =  httpStatusCode;
	}
}
