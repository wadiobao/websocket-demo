package com.project.library_management.service.iservice;

import org.springframework.http.ResponseEntity;

import com.project.library_management.model.UserRequest;

import jakarta.validation.Valid;

public interface IUserService {
	public ResponseEntity<?> createUser(@Valid UserRequest userRequest);
	public ResponseEntity<?> updateUser(@Valid UserRequest userRequest);
	public ResponseEntity<?> deleteUser(@Valid UserRequest userRequest);
	public ResponseEntity<?> resetPassword(@Valid String email);
	public ResponseEntity<?> myInfor(@Valid String email);
	
}
