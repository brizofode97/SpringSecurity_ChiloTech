package com.chilo_tech.demo.web.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvisResponseDTO(

        @NotNull
        Long identifiant,
        @NotNull @NotBlank
        String message,
        @NotNull @NotBlank
        String status

) {
}
