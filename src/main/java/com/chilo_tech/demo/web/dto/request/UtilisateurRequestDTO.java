package com.chilo_tech.demo.web.dto.request;

import com.chilo_tech.demo.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UtilisateurRequestDTO(

        @NotNull
        @NotBlank
        String nomComplet,

        @NotNull
        @NotBlank
        String mdp,

        @Email
        @NotNull
        @NotBlank
        @Size(min = 5, max = 50)
        String email,

        @NotNull
        String codeRole

    ) {
}
