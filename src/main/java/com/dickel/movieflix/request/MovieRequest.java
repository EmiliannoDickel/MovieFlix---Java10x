package com.dickel.movieflix.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        @Schema(type = "string", description = "Nome do Filme", example = "Carros 2")
        @NotEmpty(message = "Título do filme é obrigatório.")
        String title,
        @Schema(type = "string", description = "Descrição do Filme", example = "Filme de animação sobre carros que ganham vida.")
        String description,
        @Schema(type = "date", description = "Data de Lançamento do Filme", example = "25/12/2020")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,
        @Schema(type = "number", description = "Avaliação do Filme", example = "4.5")
        double rating,
        @Schema(type = "array", description = "IDs das categorias associadas ao filme", example = "[1, 2]")
        List<Long> categories,
        @Schema(type = "array", description = "IDs dos serviços de streaming onde o filme está disponível", example = "[1, 2, 3]")
        List<Long> streamings
) {
}