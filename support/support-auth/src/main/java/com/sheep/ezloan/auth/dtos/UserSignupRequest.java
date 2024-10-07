package com.sheep.ezloan.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain lowercase letters and numbers")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
            message = "Email must be in a valid format")
    private String email;
}
