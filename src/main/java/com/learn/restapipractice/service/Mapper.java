package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.AuthResponse;
import com.learn.restapipractice.entity.User;

public class Mapper {

    /**
     * Converts User Entity + JWT Token into an AuthResponse DTO
     */
    public static AuthResponse toAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .fullName(user.getName())
                .email(user.getEmail())
                // Convert the Role Enum to a String (e.g., "STUDENT", "ADMIN")
                .role(user.getRole().name())
                .build();
    }


    public static User toEntity(String name, String email, String rawPassword, com.learn.restapipractice.entity.Role role) {
        return User.builder()
                .name(name)
                .email(email)
                .password(rawPassword) // Note: Ensure you hash this password in the service layer before saving!
                .role(role)
                .build();
    }
}