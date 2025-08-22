package com.project.library_management.exception;

import com.project.library_management.enums.ErrorCode;

public class MyException extends RuntimeException {
	
	private ErrorCode errorCode;

	public MyException(ErrorCode code) {
		super();
		this.errorCode = code;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCod(ErrorCode code) {
		this.errorCode = code;
	}

}
