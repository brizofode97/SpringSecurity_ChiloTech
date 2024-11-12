package com.chilo_tech.demo.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvisRequestDTO(

        @NotNull @NotBlank
        String message,
        @NotNull @NotBlank
        String status
) {
}
