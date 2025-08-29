package com.project.library_management.entity.user;

import java.util.List;

import com.project.library_management.entity.BaseModel;
import com.project.library_management.entity.DocLending;
import com.project.library_management.enums.AccountStatus;
import com.project.library_management.util.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User extends BaseModel{
	
	@NotBlank(message = "{validation.password.notblank}")
	@Size(min = 8, message = "{validation.password.size}")
	String password;
	
	@NotBlank(message = "{validation.fullname.notblank}")
	@Pattern(regexp = Constants.FULLNAME_PATTERN, message = "{validation.fullname.pattern}")
	String name;
	
	@NotBlank(message = "{validation.address.notblank}")
	String address;
	
	@NotBlank(message = "{validation.email.notblank}")
	@Email(message = "{validation.email.invalid}")
	@Column(nullable = false, unique = true)
	String email;
	
	@NotBlank(message = "{validation.phone.notblank}")
	@Pattern(regexp = Constants.PHONE_PATTERN, message = "{validation.phone.pattern}")
	String phone;
	
	AccountStatus status;
	
	@OneToMany(mappedBy = "userId")
	List<DocLending> docLendings;
	
	public boolean resetPassword() {
		if (this.status == AccountStatus.ACTIVE) {
			this.password = Constants.DEFAULT_PASSWORD;
			return true;
		}
		return false;
	}
}
