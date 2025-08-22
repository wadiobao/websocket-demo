package com.project.library_management.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.library_management.entity.user.User;
import com.project.library_management.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {        
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            log.info("User found: ID={}, Name={}, Type={}, Status={}", 
                    user.getId(), user.getName(), user.getClass().getSimpleName(), user.getStatus());

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.emptyList() 
            );
        } catch (UsernameNotFoundException e) {
            log.warn("User not found with email: {}", email);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error loading user with email: {}", email, e);
            throw new UsernameNotFoundException("Error loading user: " + e.getMessage(), e);
        }
    }
}
