package com.dickel.movieflix.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
        @Schema(type = "string", description = "Nome user", example = "João Silva")
                          @NotEmpty(message = "Nome é obrigatório!")
        String name,
        @Schema(type = "string", description = "Email do User", example = "JoaoSilva@email.com")
        @NotEmpty(message = "Email do user é obrigatório.")
        String email,
        @Schema(type = "string", description = "Senha do User", example = "123456")
        @NotEmpty(message = "Senha do user é obrigatório.")
        String password) {
}
