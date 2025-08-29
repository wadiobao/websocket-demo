package com.project.library_management.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.project.library_management.entity.user.User;
import com.project.library_management.mapper.UserMapper;
import com.project.library_management.model.BaseResponse;
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
	public ResponseEntity<BaseResponse<Object>> createUser(@Valid UserRequest userRequest) {
		log.info("Creating user with email: {}", userRequest.getEmail());
		
		if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
			log.warn("Email already exists: {}", userRequest.getEmail());
			return ResponseEntity.badRequest().body(BaseResponse.builder().message(messageSource.getMessage("error.email.exists", null, LocaleContextHolder.getLocale())).build());
		}
		
		try {
			User user = UserMapper.toUserEntity(userRequest, encoder);
			userRepository.save(user);
			log.info("User created successfully: {}", user.getEmail());
			return ResponseEntity.ok(BaseResponse.builder().message(messageSource.getMessage("message.register.success", null, LocaleContextHolder.getLocale())).build());
			
		} catch (Exception e) {
			log.error("Error creating user: {}", e.getMessage(), e);
			return ResponseEntity.badRequest().body(BaseResponse.builder().message(messageSource.getMessage("error.creating.account", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale())).build());
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
	@Transactional
	public ResponseEntity<BaseResponse<UserResponse>> updateUser(@Valid UserRequest userRequest) {
		log.info("Updating user with email: {}", userRequest.getEmail());
	    try {
	        User user = userRepository.findByEmail(userRequest.getEmail())
	                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale())));

	        UserMapper.updateUserFromRequest(userRequest, user);

	        User savedUser = userRepository.save(user);
	        log.info("User updated successfully: {}", savedUser.getEmail());

			UserResponse userResponse = UserMapper.toUserResponse(savedUser);

	        return ResponseEntity.ok(BaseResponse.<UserResponse>builder()
					.message(messageSource.getMessage("message.update.success", null, LocaleContextHolder.getLocale()))
					.data(userResponse)
					.build());

	    } catch (UsernameNotFoundException e) {
	        log.warn("User not found for update: {}", userRequest.getEmail());
	        return ResponseEntity.badRequest().body(BaseResponse.<UserResponse>builder().message(e.getMessage()).build());
	    } catch (Exception e) {
	        log.error("Error updating user: {}", e.getMessage(), e);
	        return ResponseEntity.badRequest().body(BaseResponse.<UserResponse>builder().message(messageSource.getMessage("error.updating.user", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale())).build());
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<BaseResponse<Object>> deleteUser(@Valid UserRequest userRequest) {
		log.info("Deleting user with email: {}", userRequest.getEmail());
	    try {
	        User user = userRepository.findByEmail(userRequest.getEmail())
	                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale())));

	        userRepository.delete(user);
	        log.info("User deleted successfully: {}", userRequest.getEmail());
	        
	        return ResponseEntity.ok(BaseResponse.builder().message(messageSource.getMessage("message.delete.success", null, LocaleContextHolder.getLocale())).build());

	    } catch (UsernameNotFoundException e) {
	        log.warn("User not found for deletion: {}", userRequest.getEmail());
	        return ResponseEntity.badRequest().body(BaseResponse.builder().message(e.getMessage()).build());
	    } catch (Exception e) {
	        log.error("Error deleting user: {}", e.getMessage(), e);
	        return ResponseEntity.badRequest().body(BaseResponse.builder().message(messageSource.getMessage("error.deleting.user", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale())).build());
	    }
	}

	@Override
	public ResponseEntity<BaseResponse<UserResponse>> myInfor(@Valid String email) {
		User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale())));
		
		UserResponse response = UserMapper.toUserResponse(user);
		return ResponseEntity.ok(BaseResponse.<UserResponse>builder().data(response).build());
	}
	
	
}
