package com.project.library_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.library_management.model.UserRequest;
import com.project.library_management.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller 
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthenticationController {

	private final MemberService memberService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest) {
		log.info("Registration request received for email: {}", userRequest.getEmail());
		return memberService.createUser(userRequest);
	}

	@PostMapping("/auth/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody @NotBlank(message = "{validation.email.notblank}") 
	                                     @Email(message = "{validation.email.invalid}") String email) {
		log.info("Password reset request received for email: {}", email);
		return memberService.resetPassword(email);
	}
	
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
