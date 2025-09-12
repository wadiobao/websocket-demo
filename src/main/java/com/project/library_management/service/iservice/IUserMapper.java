package com.project.library_management.service.iservice;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.library_management.entity.user.User;
import com.project.library_management.model.UserRequest;
import com.project.library_management.model.UserResponse;

public interface IUserMapper {
    User toUserEntity(UserRequest userRequest, PasswordEncoder encoder);
    UserResponse toUserResponse(User user);
    void updateUserFromRequest(UserRequest request, User user);
}
