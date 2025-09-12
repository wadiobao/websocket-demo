package com.project.library_management.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.library_management.entity.user.Member;
import com.project.library_management.entity.user.RegularMember;
import com.project.library_management.entity.user.Student;
import com.project.library_management.entity.user.User;
import com.project.library_management.entity.user.VipMember;
import com.project.library_management.enums.UserType;
import com.project.library_management.service.iservice.IUserMapper;
import com.project.library_management.model.UserRequest;
import com.project.library_management.model.UserResponse;

@Component
public class UserMapperImpl implements IUserMapper {

    @Override
    public User toUserEntity(UserRequest userRequest, PasswordEncoder encoder) {
        User user = null;
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
        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        UserType type = null;
        if (user instanceof Student) {
            type = UserType.STUDENT;
        } else if (user instanceof VipMember) {
            type = UserType.VIP;
        } else if (user instanceof RegularMember) {
            type = UserType.REGULAR;
        }
        
        Member member = (Member) user;
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .dateOfMembership(member.getDateOfMembersip())
                .type(type)
                .fine(member.getFine())
                .build();
    }

    @Override
    public void updateUserFromRequest(UserRequest request, User user) {
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (user instanceof Member && request.getDateOfMembersip() != null) {
            ((Member) user).setDateOfMembersip(request.getDateOfMembersip());
        }
    }
}
