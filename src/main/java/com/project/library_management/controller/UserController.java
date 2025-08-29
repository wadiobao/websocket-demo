package com.project.library_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.UserResponse;
import com.project.library_management.service.iservice.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	final IUserService iUserService;
	
	@PostMapping("/infor")
	public ResponseEntity<BaseResponse<UserResponse>> myInfor(@RequestParam String email){
		return iUserService.myInfor(email);
	}
}
