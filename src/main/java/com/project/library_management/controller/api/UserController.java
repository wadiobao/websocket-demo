package com.project.library_management.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.UserResponse;
import com.project.library_management.service.iservice.IUserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
	
	IUserService iUserService;
	
	@PostMapping("/infor")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'MEMBER')")
    public ResponseEntity<BaseResponse<UserResponse>> myInfor(@RequestParam String email){
        return iUserService.myInfor(email);
    }

    @PostMapping("/pay-fine")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BaseResponse<Object>> payFine(@RequestParam String email, @RequestParam double amount) {
        return iUserService.payFine(email, amount);
    }
}