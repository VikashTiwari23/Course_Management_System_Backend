package com.learn.restapipractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginRequest {
    @NotBlank(message = "Email should not blank")
    @Email(message="Email should be proper")
    private String email;
    @NotBlank(message="Password should be filled")
    @Size(message = "Size should be more than 6 ")
    private String password;

}
