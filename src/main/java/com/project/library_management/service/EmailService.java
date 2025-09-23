package com.project.library_management.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
	JavaMailSender javaMailSender;
	
	Map<String,String> otpCache = new ConcurrentHashMap<>();
		
	public void generateAndSendPass(String email, String newPassword) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject("New Password - Library Management");
			mailMessage.setText("Your new password is: " + newPassword + "\n\nPlease log in and change your password immediately.");
			javaMailSender.send(mailMessage);
			
			log.info("Password reset email sent successfully to: {}", email);
			scheduleExpiry(email, 3, TimeUnit.MINUTES);
		} catch (Exception e) {
			log.error("Failed to send password reset email to: {}", email, e);
		}
	}
	
	private void scheduleExpiry(String email, long timeout, TimeUnit unit) {
        Executors.newSingleThreadScheduledExecutor()
            .schedule(() -> otpCache.remove(email), timeout, unit);
    }
}


