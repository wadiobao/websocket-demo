package com.project.library_management.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.library_management.entity.user.Librarian;
import com.project.library_management.entity.user.User;
import com.project.library_management.enums.AccountStatus;
import com.project.library_management.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
	
	PasswordEncoder encoder;

	@Bean
	ApplicationRunner applicationRunner(UserRepository repository) {
		return args -> {
			log.info("Checking if default admin user exists...");
			
			if(repository.findByEmail("admin").isEmpty()) {
				log.info("Default admin user not found, creating new one...");
				
				try {
					User user = Librarian.builder()
								.password(encoder.encode("123456"))
								.email("admin")
								.name("Administrator")
								.address("System Address")
								.phone("0000000000")
								.status(AccountStatus.ACTIVE)
								.build();
					
					log.info("Built Librarian object: {}", user);
					
					User savedUser = repository.save(user);
					log.info("Default admin user created successfully with ID: {}", savedUser.getId());
					
				} catch (Exception e) {
					log.error("Error creating default admin user", e);
					throw e;
				}
			} else {
				log.info("Default admin user already exists");
			}
		};
	}
}
