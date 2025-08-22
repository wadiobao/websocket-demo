package com.project.library_management.model;

import java.time.LocalDate;

import com.project.library_management.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	String name;
	String address;
	String email;
	String phone;
	LocalDate dateOfMembership;
	UserType type;
}
