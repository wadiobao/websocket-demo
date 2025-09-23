package com.project.library_management.service.iservice;

import org.springframework.http.ResponseEntity;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.UserRequest;
import com.project.library_management.model.UserResponse;

import jakarta.validation.Valid;

public interface IUserService {
	public ResponseEntity<BaseResponse<Object>> createUser(@Valid UserRequest userRequest);
	public ResponseEntity<BaseResponse<UserResponse>> updateUser(@Valid UserRequest userRequest);
	public ResponseEntity<BaseResponse<Object>> deleteUser(@Valid UserRequest userRequest);
	public ResponseEntity<?> resetPassword(@Valid String email);
	public ResponseEntity<BaseResponse<UserResponse>> myInfor(@Valid String email);
	public ResponseEntity<BaseResponse<Object>> payFine(@Valid String email, double amount);
	
}
