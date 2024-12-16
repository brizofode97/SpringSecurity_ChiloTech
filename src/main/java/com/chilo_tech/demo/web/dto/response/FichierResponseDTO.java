package com.chilo_tech.demo.web.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FichierResponseDTO(

        @NotNull @NotBlank
        String nom,

        @NotNull @NotBlank
        String url,

        @NotNull @NotBlank
        String type,

        @NotNull @NotBlank
        String extension,

        @NotNull
        Long taille,

        @NotNull
        UtilisateurResponseDTO utilisateurResponseDTO

) {
}
