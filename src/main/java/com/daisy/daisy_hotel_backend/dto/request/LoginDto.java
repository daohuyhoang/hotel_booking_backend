package com.daisy.daisy_hotel_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "Username or email must be not blank")
    private String usernameOrEmail;

    @NotBlank(message = "Password must be not blank")
    private String password;
}