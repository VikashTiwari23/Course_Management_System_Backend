package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.AuthResponse;
import com.learn.restapipractice.dto.LoginRequest;
import com.learn.restapipractice.dto.SignUpRequest;
import com.learn.restapipractice.entity.Role;
import com.learn.restapipractice.entity.User;
import com.learn.restapipractice.exception.ResourceAlreadyExistsException;
import com.learn.restapipractice.repository.UserRepository;
import com.learn.restapipractice.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;


    @InjectMocks
    private AuthService authService;

    // ============ TEST 1: signup success ============
    @Test
    void signup_Success() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .fullName("Vikash")
                .email("Vikash@12.com")
                .password("password123")
                .role("STUDENT")
                .build();
        when(userRepository.existsByEmail("Vikash@12.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("password___123___");
        User savedUser = User.builder()
                .email("Vikash@12.com")
                .role(Role.STUDENT)
                .password("password___123____")
                .name("Vikash")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken("Vikash@12.com","STUDENT")).thenReturn("new_token");
        AuthResponse authResponse = authService.signup(signUpRequest);

        assertEquals("new_token", authResponse.getToken());
        assertEquals("STUDENT", authResponse.getRole());
        assertEquals("Vikash", authResponse.getFullName());
        assertEquals("Vikash@12.com", authResponse.getEmail());
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    void signup_EmailAlreadyExists_ThrowsException() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .fullName("Vikash")
                .email("Vikash@12.com")
                .password("password123")
                .role("STUDENT")
                .build();
        when(userRepository.existsByEmail("Vikash@12.com")).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class,()->authService.signup(signUpRequest));
        verify(userRepository,never()).save(any(User.class));
        verify(passwordEncoder,never()).encode(any(String.class));
    }

    @Test
    void login_Success() {
        LoginRequest request = LoginRequest.builder()
                .email("Vikash@12.com")
                .password("password123")
                .build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        User user = User.builder()
                .id(1L)
                .name("Vikash")
                .email("Vikash@12.com")
                .password("password___123___")
                .role(Role.STUDENT)
                .build();
        when(userRepository.findByEmail("Vikash@12.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("Vikash@12.com","STUDENT")).thenReturn("new_token");
        AuthResponse result = authService.login(request);

        assertEquals("new_token", result.getToken());
        assertEquals("STUDENT", result.getRole());
        assertEquals("Vikash", result.getFullName());
        assertEquals("Vikash@12.com",result.getEmail());
        verify(userRepository,times(1)).findByEmail("Vikash@12.com");
    }
    @Test
    void login_UserNotFound_ThrowsException() {
        LoginRequest request = LoginRequest.builder()
                .email("unknown@test.com")
                .password("password123")
                .build();

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenReturn(null);

        when(userRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> authService.login(request)
        );
    }
}
