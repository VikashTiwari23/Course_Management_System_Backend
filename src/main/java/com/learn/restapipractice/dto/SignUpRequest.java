package com.learn.restapipractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
    @NotBlank
    private String fullName;
    @NotBlank @Email(message = "Email should be valid")
    private String email;
    @NotBlank @Size(min=6)
    private String password;
    @NotBlank(message="Role is required")
    private String role;
}
