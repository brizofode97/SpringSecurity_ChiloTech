package com.chilo_tech.demo.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthentificationDTO(

        @Email @NotNull @NotBlank
        String username,

        @NotNull @NotBlank
        String password
) {
}
