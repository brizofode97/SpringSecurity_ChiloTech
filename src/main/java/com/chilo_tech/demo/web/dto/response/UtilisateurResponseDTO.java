package com.chilo_tech.demo.web.dto.response;

import com.chilo_tech.demo.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UtilisateurResponseDTO(

        @NotNull
        Long identifiant,
        @NotNull @NotBlank
        String nomComplet,
        @NotNull @NotBlank
        String email,
        @NotNull @NotBlank
        boolean actif,
        @NotNull
        Role role

) {
}
