package com.project.library_management.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.project.library_management.entity.user.Member;
import com.project.library_management.entity.user.RegularMember;
import com.project.library_management.entity.user.Student;
import com.project.library_management.entity.user.User;
import com.project.library_management.entity.user.VipMember;
import com.project.library_management.enums.UserType;
import com.project.library_management.model.UserRequest;
import com.project.library_management.model.UserResponse;
import com.project.library_management.repository.UserRepository;
import com.project.library_management.service.iservice.IUserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
public class MemberService implements IUserService {
	
	final EmailService emailService;
	final UserRepository userRepository;
	final PasswordEncoder encoder;
	final MessageSource messageSource;
	
	@Override
	@Transactional
	public ResponseEntity<?> createUser(@Valid UserRequest userRequest) {
		log.info("Creating user with email: {}", userRequest.getEmail());
		
		if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
			log.warn("Email already exists: {}", userRequest.getEmail());
			return ResponseEntity.badRequest().body(messageSource.getMessage("error.email.exists", null, LocaleContextHolder.getLocale()));
		}
		
		User user = null;
		try {
			switch (userRequest.getUserType()) {
			case REGULAR:
				user = RegularMember.builder()
						.password(encoder.encode(userRequest.getPassword()))
						.address(userRequest.getAddress())
						.name(userRequest.getName())
						.email(userRequest.getEmail())
						.phone(userRequest.getPhone())
						.status(userRequest.getStatus())
						.dateOfMembersip(userRequest.getDateOfMembersip())
						.totalCheckedout(0)
						.build();
				break;
				
			case STUDENT:
				user = Student.builder()
						.password(encoder.encode(userRequest.getPassword()))
						.address(userRequest.getAddress())
						.name(userRequest.getName())
						.email(userRequest.getEmail())
						.phone(userRequest.getPhone())
						.status(userRequest.getStatus())
						.dateOfMembersip(userRequest.getDateOfMembersip())
						.totalCheckedout(0)
						.build();
				break;
				
			case VIP:
				user = VipMember.builder()
						.password(encoder.encode(userRequest.getPassword()))
						.address(userRequest.getAddress())
						.name(userRequest.getName())
						.email(userRequest.getEmail())
						.phone(userRequest.getPhone())
						.status(userRequest.getStatus())
						.dateOfMembersip(userRequest.getDateOfMembersip())
						.totalCheckedout(0)
						.build();
				break;
			}
			
			userRepository.save(user);
			log.info("User created successfully: {}", user.getEmail());
			return ResponseEntity.ok(messageSource.getMessage("message.register.success", null, LocaleContextHolder.getLocale()));
			
		} catch (Exception e) {
			log.error("Error creating user: {}", e.getMessage(), e);
			return ResponseEntity.badRequest().body(messageSource.getMessage("error.creating.account", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
		}
	}
	 
	@Override
	public ResponseEntity<?> resetPassword(String email){
		log.info("Resetting password for email: {}", email);
		
		try {
			User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale())));
			
			if (user.resetPassword()) {
				log.info(user.getPassword());
				String password = user.getPassword();
				user.setPassword(encoder.encode(password));
				userRepository.save(user);
				emailService.generateAndSendPass(email, password);
				log.info("Password reset successful for: {}", email);
				return ResponseEntity.ok(messageSource.getMessage("message.reset.password.success", null, LocaleContextHolder.getLocale()));
			} else {
				log.warn("Cannot reset password for inactive user: {}", email);
				return ResponseEntity.badRequest().body(messageSource.getMessage("error.account.inactive", null, LocaleContextHolder.getLocale()));
			}
		} catch (UsernameNotFoundException e) {
			log.warn("User not found for password reset: {}", email);
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			log.error("Error during password reset for: {}", email, e);
			return ResponseEntity.badRequest().body(messageSource.getMessage("error.resetting.password", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
		}
	}
	

	@Override
	public ResponseEntity<?> updateUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> myInfor(@Valid String email) {
		User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		UserType type = null;
		if(user instanceof Student) {
			type = UserType.STUDENT;
		}else if(user instanceof VipMember) {
			type = UserType.VIP;
		}else {
			type = UserType.REGULAR;
		}
		Member member = (Member) user;
		UserResponse response = UserResponse.builder()
				.name(user.getName())
				.email(email)
				.address(user.getAddress())
				.phone(user.getPhone())
				.dateOfMembership(member.getDateOfMembersip())
				.type(type)
				.build();
		return ResponseEntity.ok(response);
	}
	
	
}
