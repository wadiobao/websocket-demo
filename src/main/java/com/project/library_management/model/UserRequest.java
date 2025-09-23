package com.project.library_management.model;

import java.time.LocalDate;

import com.project.library_management.enums.AccountStatus;
import com.project.library_management.enums.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
	@NotBlank(message = "{validation.password.notblank}")
    @Size(min = 6, message = "{validation.password.size}")
    String password;
    
    @NotBlank(message = "{validation.fullname.notblank}")
    @Size(min = 2, max = 100, message = "{validation.fullname.size}")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]+$", message = "{validation.fullname.pattern}")
    String name;
    
    @NotBlank(message = "{validation.address.notblank}")
    @Size(min = 5, max = 200, message = "{validation.address.size}")
    String address;
    
    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.invalid}")
    String email;
    
    @NotBlank(message = "{validation.phone.notblank}")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "{validation.phone.pattern}")
    String phone;
	
	@Default
	AccountStatus status = AccountStatus.ACTIVE;

	@Default
	LocalDate dateOfMembersip = LocalDate.now();
	
	@Default()
	UserType userType = UserType.REGULAR;
}
