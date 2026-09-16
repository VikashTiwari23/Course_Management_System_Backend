package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.AuthResponse;
import com.learn.restapipractice.dto.LoginRequest;
import com.learn.restapipractice.dto.SignUpRequest;
import com.learn.restapipractice.entity.Role;
import com.learn.restapipractice.entity.User;
import com.learn.restapipractice.exception.ResourceAlreadyExistsException;
import com.learn.restapipractice.repository.UserRepository;
import com.learn.restapipractice.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signup(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists ! ");
        }
        User user = User.builder()
                .name(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .token(token)
                .fullName(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found with email "+request.getEmail()));
        String jwt = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .token(jwt)
                .fullName(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
