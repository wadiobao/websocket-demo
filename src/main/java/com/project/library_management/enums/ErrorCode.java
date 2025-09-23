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

	// Document related errors
	DOCUMENT_NOT_FOUND(404, "Document not found", HttpStatus.NOT_FOUND),
	RACK_NOT_FOUND(404, "Rack not found", HttpStatus.NOT_FOUND),
	MEMBER_NOT_FOUND(404, "Member not found", HttpStatus.NOT_FOUND),
	DOCUMENT_BORROWED_CANNOT_DELETE(400, "Cannot delete a borrowed document", HttpStatus.BAD_REQUEST),
	DOCUMENT_ALREADY_BORROWED(400, "Document is already borrowed", HttpStatus.BAD_REQUEST),
	DOCUMENT_REFERENCE_ONLY(400, "Document is reference only and cannot be borrowed", HttpStatus.BAD_REQUEST),
	LOAN_LIMIT_REACHED(400, "Member has reached their loan limit", HttpStatus.BAD_REQUEST),
	LENDING_RECORD_NOT_FOUND(404, "Lending record not found", HttpStatus.NOT_FOUND)
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
